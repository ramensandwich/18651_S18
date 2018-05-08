package Node;

import Map.POI;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Random;

/**
 * Created by bradp on 2/27/2018.
 * Client node
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
        //Set max range willing to go to vendor
        while(this.maxRange < 4){
            this.maxRange = rand.nextInt(10);
        }
        //Set student type. 
        int index = ID % 14;
        for(StudentType type : StudentType.values()){
            if(type.getID() == index){
                this.type = type;
                points.addAll(type.getPoints());
                points.add(type.getHome());
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
        if(this.recent){
            if(this.recentCount < 100){
                this.recentCount += 1;
            } else {
                this.recentCount = 0;
                this.recent = false;
            }
        }

        //If not going to vendor and not returning from vendor
        if(!this.toVend && !this.backVend){
            //See if roll determines that we want to go to a vendor
            if(prof.getVend(ticknum)){
                this.venddest = this.findVendor(vendors);
                if(this.venddest != null){
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
            //Heading to vendor
            if(this.venddest.getLocation().equals(this.location)){
                //Arrived at vendor
                this.makePurchase(this.venddest);
                this.toVend = false;
                this.venddest = null;
            } else {
                this.moveToDest(venddest.getLocation());
            }
        } else {
            //Returning from vendor
            if(this.destination.getLoc().equals(this.location)){
                //Arrived back
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
            //Startup state
            this.destination = this.points.get(0);
            this.transit = true;
        } else if(this.transit){
            //Heading to destination
            if(this.destination.getLoc().equals(this.location)){
                this.transit = false;
                this.atdest = true;
            } else {
                moveToDest(destination.getLoc());
            }
        } else if(this.atdest) {
            if(this.timeAtDest < this.cooldown){
                //Countdown until next move
                this.timeAtDest += 1;
            } else {
                //Choose new destination from list
                Random rand = new Random();
                this.transit = true;
                this.atdest = false;
                POI next = this.points.get(rand.nextInt(this.points.size()));
                while(next.getID() == this.destination.getID()){
                    next = this.points.get(rand.nextInt(this.points.size()));
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
        //Move via Manhattan distances. 

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

        //Pick nearest vendor from list of vendors in map
        //Client knows of all vendors because theoretical app
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

        //Make purchase if roll indicates
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
        //Report purchase for logging
        Purchase buy = null;
        if(this.lastbuy != null){
            buy = new Purchase(lastbuy.clientID, lastbuy.vendorID, lastbuy.name, lastbuy.price);
            this.lastbuy = null;
        }
        return buy;
    }
}
