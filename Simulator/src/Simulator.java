import Map.POI;
import Map.VendMap;
import Node.ClientNode;
import Node.Purchase;
import Node.Stock;
import Node.VendNode;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.awt.*;
import java.io.*;
import java.util.*;
import java.util.List;

/**
 * Created by bradp on 2/27/2018.
 */
public class Simulator {
    private ArrayList<ClientNode> clients;
    private ArrayList<VendNode> vendors;
    private VendMap vmap;

    //Create output structures and writers
    JSONArray buyB = new JSONArray();
    JSONArray buyL = new JSONArray();
    JSONArray buyD = new JSONArray();

    JSONArray dataB = new JSONArray();
    JSONArray dataL = new JSONArray();
    JSONArray dataD = new JSONArray();

    PrintWriter printB = new PrintWriter(new FileOutputStream("breakfast.json", true));
    PrintWriter printL = new PrintWriter(new FileOutputStream("lunch.json", true));
    PrintWriter printD = new PrintWriter(new FileOutputStream("dinner.json", true));

    PrintWriter outB = new PrintWriter(new FileOutputStream("breakfastbuy.json", true));
    PrintWriter outL = new PrintWriter(new FileOutputStream("lunchbuy.json", true));
    PrintWriter outD = new PrintWriter(new FileOutputStream("dinnerbuy.json", true));

    PrintWriter vendA = new PrintWriter(new FileOutputStream("vendorA.txt", true));
    PrintWriter priceA = new PrintWriter(new FileOutputStream("priceA.txt", true));
    PrintWriter salesA = new PrintWriter(new FileOutputStream("salesA.txt", true));

   public Simulator(int numclients, int numvendors, int length, int height) throws IOException {
       this.clients = new ArrayList<ClientNode>(numclients);
       this.vendors = new ArrayList<VendNode>(numvendors);
       System.out.println("Generating Map");
       this.vmap = new VendMap(length, height);

       List<POI> points = new ArrayList<POI>(Arrays.asList(POI.values()));

       FileOutputStream fosP = new FileOutputStream("points.json");
       PrintWriter objP = new PrintWriter(fosP);
       JSONObject pointobj = new JSONObject();
       JSONArray pointarr = new JSONArray();
       //Add points of interest to map
       for(POI p : points){
           Point loc = p.getLoc();
           JSONObject obj = new JSONObject();
           obj.put("X", Integer.toString(loc.x));
           obj.put("Y", Integer.toString(loc.y));
           pointarr.add(obj);
       }
       //Add points of interest to output file
       pointobj.put("Points", pointarr);
       objP.write(pointobj.toString());
       objP.close();
       fosP.close();

       System.out.println("Generating Clients");
       for(int i = 0; i < numclients; i++){
           this.newClient(i);
       }

       System.out.println("Generating Vendors");
       for(int i = 0; i < numvendors; i++){
           this.newVendor(i);
       }

       System.out.println("Finished Generation!");
   }

   public int newClient(int ID){
       ClientNode client = new ClientNode(ID);
       Random rand = new Random();
       clients.add(client);
       int x = rand.nextInt(84);
       int y = rand.nextInt(56);
       client.setLocation(x,y);
       return client.getID();
   }

    public int newVendor(int ID){
        VendNode vendor = new VendNode(ID);
        Random rand = new Random();
        vendors.add(vendor);
        int x = 0;
        int y = 0;
        if(ID == 0){
            //Ensure Vendor 0 goes to UC Black Chairs. This is the control vendor.
            x = POI.BLACKCHAIRS.getLoc().x;
            y = POI.BLACKCHAIRS.getLoc().y;
        } else {
            x = rand.nextInt(84);
            y = rand.nextInt(56);
        }
        vendor.setLocation(x,y);
        return vendor.getID();
    }

    public void tick(int ticknum) throws IOException {
        String data;
        int x = 0;
        int y = 0;
        int ID = 0;
        ClientNode c;

        for(int i = 0; i < clients.size(); i++){
            c = clients.get(i);
            c.tick(ticknum, this.vendors);
            x = c.getLocation().x;
            y = c.getLocation().y;
            ID = c.getID();
            Purchase buy = c.getLastBuy();

            //Report client positions
            JSONObject obj = new JSONObject();
            obj.put("X", Integer.toString(x));
            obj.put("Y", Integer.toString(y));
            obj.put("ID", Integer.toString(ID));
            obj.put("Tick", Integer.toString(ticknum));
            if(ticknum >= 5400 && ticknum < 6120){
                //Write to breakfast output file
                dataB.add(obj);
            } else if(ticknum >= 8280 && ticknum < 9000){
                //Write to lunch output file
                dataL.add(obj);
            } else if(ticknum >= 13320 && ticknum < 14040){
                //Write to dinner output file
                dataD.add(obj);
            }

            //Report vendor transactions
            if(buy != null){

                JSONObject objbuy = new JSONObject();
                objbuy.put("Client ID", Integer.toString(buy.clientID));
                objbuy.put("Vendor ID", Integer.toString(buy.vendorID));
                objbuy.put("Item", buy.name);
                objbuy.put("Price", Double.toString(buy.price));
                objbuy.put("Tick", Integer.toString(ticknum));
                if(ticknum >= 5400 && ticknum < 6120){
                //Write to breakfast output file
                    buyB.add(objbuy);
                } else if(ticknum >= 8280 && ticknum < 9000){
                //Write to lunch output file
                    buyL.add(objbuy);
                } else if(ticknum >= 13320 && ticknum < 14040){
                //Write to dinner output file
                    buyD.add(objbuy);
                }
            }
        }

        for(int i = 0; i < vendors.size(); i++){
            VendNode v = vendors.get(i);
            v.tick();
            StringBuilder sp = new StringBuilder();
            //Write sales, prices, and stock to output file
            if(i == 0){
                priceA.println(Double.toString(v.getPrice()));
                salesA.println(Integer.toString(v.getSales()));
                for(Stock s : Stock.values()){
                    sp.append(Integer.toString(v.readPortion(s)));
                    sp.append("   ");
                }
                vendA.println(sp.toString());
            }
        }
    }

    public void endSim(){
        //Close all output streams
        JSONObject objB = new JSONObject();
        JSONObject objL = new JSONObject();
        JSONObject objD = new JSONObject();

        JSONObject listB = new JSONObject();
        JSONObject listL = new JSONObject();
        JSONObject listD = new JSONObject();

        objB.put("data", dataB);
        printB.write(objB.toString());
        objL.put("data", dataL);
        printL.write(objL.toString());
        objD.put("data", dataD);
        printD.write(objD.toString());

        listB.put("data", buyB);
        outB.write(listB.toString());
        listL.put("data", buyL);
        outL.write(listL.toString());
        listD.put("data", buyD);
        outD.write(listD.toString());

        printB.close();
        printL.close();
        printD.close();

        outB.close();
        outL.close();
        outD.close();

        vendA.close();
        priceA.close();
        salesA.close();
    }
}
