package Node;

import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by bradp on 2/27/2018.
 */
public class VendNode extends Node {
    private Map<Stock, Integer> currStock;
    private Map<Stock, Integer> currSales;
    private Map<Stock, Integer> currPortion;
    private Point location;
    private static final int capacity = 200;
    private static final int pricetime = 120;
    private int stockHeld;
    private int timer;
    private int totalsales;
    private int expectedsales;
    private double price;
    private double gain;

    public VendNode(int ID){
        super(ID, NodeType.VEND);
        this.location = new Point();
        this.currStock = new HashMap<Stock, Integer>();
        this.currSales = new HashMap<Stock, Integer>();
        this.currPortion = new HashMap<Stock, Integer>();
        this.stockHeld = this.capacity;
        this.timer = this.pricetime;
        this.totalsales = 0;
        this.expectedsales = 1;
        this.price = 1.0;
        this.gain = 1.00;
        for(Stock s : Stock.values()){
            this.currStock.put(s, 25);
            this.currSales.put(s, 0);
            this.currPortion.put(s, 25);
        }
    }

    public void setLocation(int x, int y){
        location.x = x;
        location.y = y;
    }

    public Point getLocation(){
        return location;
    }

    public double purchase(Stock item, int count){
        int temp;
        int sales;
        int amount;

        if(getID() == 0){
            //System.out.println("Sale!");
        }

        temp = currStock.get(item);
        temp -= count;
        amount = count;
        if(temp < 0){
            amount = currStock.get(item);
            temp = 0;
        }
        currStock.put(item, temp);
        sales = currSales.get(item);
        sales += amount;
        currSales.put(item, sales);
        this.stockHeld -= amount;
        this.totalsales += amount;
        return this.price;
    }

    public void restockItem(Stock item, int count){
        int temp;

        temp = currStock.get(item);
        temp += count;
        currStock.put(item, temp);
        currSales.put(item,0);
        this.stockHeld += count;
    }

    public void restockFull(){
        double diff;
        double itemsales;
        double itemnum;
        int newnum;
        double portion;
        double itemportion;
        double itemdiff;
        double portiondiff;
        double saleportion;

        diff = this.capacity - this.stockHeld;
        portion = diff/((double)this.capacity);
        //System.out.printf("Restock! Diff = %f, Portion = %f\n", diff, portion);
        for(Stock s : Stock.values()){
            itemsales = (double)this.currSales.get(s);
            itemnum = (double)this.currPortion.get(s);
            saleportion = itemsales/diff;
            itemportion = itemnum/this.capacity;
            portiondiff = (saleportion - itemportion);
            itemdiff = portion * itemnum * portiondiff * 2;
            newnum = (int)(itemdiff + itemnum + 0.5);
            //System.out.printf("Item Sales: %f, Item Portion: %f, Item Difference: %f, New Portion: %d\n", itemsales, itemportion, itemdiff, newnum);
            this.currPortion.put(s, newnum);
            if(this.currStock.get(s) < newnum){
                this.currStock.put(s,newnum);
            }
            currSales.put(s,0);
        }

        this.stockHeld = 200;
    }

    public int readStock(Stock item){
        int count;

        count = this.currStock.get(item);

        return count;
    }

    public int readPortion(Stock item){
        int count;

        count = this.currPortion.get(item);

        return count;
    }

    public ArrayList<Stock> getStock(){
        ArrayList<Stock> options = new ArrayList<>();
        for(Stock s : this.currStock.keySet()){
            if(this.currStock.get(s) > 0){
                options.add(s);
            }
        }
        return options;
    }

    public double getPrice(){
        return this.price;
    }

    public int getSales(){
        return this.totalsales;
    }

    public void tick(){
        if(this.timer > 0){
            this.timer -= 1;
        } else {
            priceAdjust();
            this.timer = this.pricetime;
        }

        if(this.stockHeld < 150){
            restockFull();
        }
    }

    private void priceAdjust(){
        double salesratio = 0.0;
        double exratio = 0.0;
        double diffratio = 0.0;
        double capratio = 0.0;
        double newprice = 0.0;
        int diff = this.totalsales - this.expectedsales;
        double newdiff = 0.0;


        diffratio = (((double)this.totalsales) - ((double)this.expectedsales)) / ((double)this.expectedsales);
        capratio = ((double)this.expectedsales) / ((double)this.capacity);

        newdiff = (((double)diff) + this.gain) / 2.0;

        newprice = this.price + (diffratio * capratio * this.price);
        this.price = newprice;
        if(this.price < 0.5){
            this.price = 0.5;
        }
        if(getID() == 0){
            System.out.printf("Price adjust! %d, %d, %f\n", this.totalsales, this.expectedsales, this.price);
        }
        this.expectedsales += (int)(newdiff);
        if(this.expectedsales < 1){
            this.expectedsales = 1;
        }

        this.totalsales = 0;
        this.gain = diff;

    }
}