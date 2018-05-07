package Node;

import Map.POI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by bradp on 2/27/2018.
 */
public class ClientNode extends Node {
    private Profile prof;
    private StudentType type;
    private Point location;
    private ArrayList<POI> points;
    private POI destination;
    private VendNode venddest;
    private Purchase lastbuy;
    private boolean transit;
    private boolean atdest;
    private boolean toVend;
    private boolean backVend;
    private boolean recent;
    private int maxRange;
    private int timeAtDest;
    private int recentCount;
    private final static int cooldown = 360;

    public ClientNode(int ID){
        super(ID, NodeType.CLIENT);
        Random rand = new Random();
        this.prof = new Profile(ID);
        this.location = new Point();
        this.venddest = null;
        this.lastbuy = null;
        this.points = new ArrayList<>();
        this.destination = null;
        this.transit = true;
        this.atdest = false;
        this.toVend = false;
        this.backVend = false;
        this.recent = false;
        this.maxRange = rand.nextInt(10);
        while(this.maxRange < 4){
            this.maxRange = rand.nextInt(10);
        }
        int index = ID % 14;
        for(StudentType type : StudentType.values()){
            if(type.getID() == index){
                this.type = type;
                points.addAll(type.getPoints());
                points.add(type.getHome());
                /*if(ID == 0){
                    for(POI p : this.points){
                        System.out.printf("POI: %s\n", p.getName());
                    }
                }*/
            }
        }
    }

    public void setLocation(int x, int y){
        location.x = x;
        location.y = y;
    }

    public void setPoint(POI p){
        this.points.add(p);
    }

    public Point getLocation(){
        return location;
    }

    public void tick(int ticknum, ArrayList<VendNode> vendors){
        Random rand = new Random();
       /* if(this.getID() == 0){
            System.out.printf("X = %d, Y = %d\n", this.location.x, this.location.y);
        }*/
        if(this.recent){
            if(this.recentCount < 100){
                this.recentCount += 1;
            } else {
                this.recentCount = 0;
                this.recent = false;
            }
        }

        if(!this.toVend && !this.backVend){
            if(prof.getVend(ticknum)){
                this.venddest = this.findVendor(vendors);
                if(this.venddest != null){
                    //System.out.println("Heading to Vendor");
                    this.toVend = true;
                }
            } else {
                this.handleDest();
            }
        } else {
            this.handleVend();
        }
    }

    private void handleVend(){
        if(this.toVend){
            if(this.venddest.getLocation().equals(this.location)){
                this.makePurchase(this.venddest);
                this.toVend = false;
                this.venddest = null;
            } else {
                this.moveToDest(venddest.getLocation());
            }
        } else {
            if(this.destination.getLoc().equals(this.location)){
                this.backVend = false;
                this.transit = false;
                this.atdest = true;
            } else {
                this.moveToDest(destination.getLoc());
            }
        }
    }

    private void handleDest(){
        if(this.destination == null){
            this.destination = this.points.get(0);
            this.transit = true;
        } else if(this.transit){
            if(this.destination.getLoc().equals(this.location)){
                this.transit = false;
                this.atdest = true;
            } else {
                moveToDest(destination.getLoc());
            }
        } else if(this.atdest) {
            if(this.timeAtDest < this.cooldown){
                this.timeAtDest += 1;
            } else {
                Random rand = new Random();
                this.transit = true;
                this.atdest = false;
                POI next = this.points.get(rand.nextInt(this.points.size()));
                while(next.getID() == this.destination.getID()){
                    next = this.points.get(rand.nextInt(this.points.size()));
                    //System.out.printf("New Dest: %s\n", next.getName());
                }
                this.destination = next;
                this.timeAtDest = 0;
            }
        }
    }

    private void moveToDest(Point p){
        int xa = location.x;
        int ya = location.y;
        int xb = p.x;
        int yb = p.y;


        if(Math.abs(xa-xb) >= Math.abs(ya-yb)){
            if(xa > xb){
                location.x -= 1;
            } else {
                location.x += 1;
            }
        } else {
            if(ya > yb){
                location.y -= 1;
            } else {
                location.y += 1;
            }
        }
    }

    private VendNode findVendor(ArrayList<VendNode> vendors){
        VendNode v = null;
        VendNode vtemp;
        Point vpoint;
        int x = location.x;
        int y = location.y;
        int min = -1;
        int manhattan;

        for(int i = 0; i < vendors.size(); i++){
            vtemp = vendors.get(i);
            vpoint = vtemp.getLocation();
            manhattan = Math.abs(x - vpoint.x) + Math.abs(y - vpoint.y);
            if(min == -1 || manhattan <= min){
                min = manhattan;
                v = vtemp;
            }
        }

        if(min <= maxRange){
            return v;
        } else {
            return null;
        }
    }

    private void makePurchase(VendNode vendor){
        ArrayList<Stock> options = new ArrayList<>(vendor.getStock());
        Collections.shuffle(options);
        Random rand = new Random();
        int roll;
        double price;

        for(Stock s : options){
            roll = rand.nextInt(100);
            if(this.prof.getItem(s, roll, this.recent)){
                price = vendor.purchase(s, 1);
                this.recent = true;
                this.recentCount = 0;
                this.lastbuy = new Purchase(this.getID(), vendor.getID(), s.name(), price);
                break;
            }
        }
    }

    public Purchase getLastBuy(){
        Purchase buy = null;
        if(this.lastbuy != null){
            buy = new Purchase(lastbuy.clientID, lastbuy.vendorID, lastbuy.name, lastbuy.price);
            this.lastbuy = null;
        }
        return buy;
    }
}
