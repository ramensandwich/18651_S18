package Map;

import Node.ClientNode;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by bradp on 4/12/2018.
 */
public enum POI {
    RASHID(23,13,0,"Rashid Auditorium"),
    G_COMMONS(23,19,1,"Gates 6th Floor Commons"),
    TAZA(21,17,2,"Taza de Orro"),
    G_CLUSTER(19,17,3,"Gates Cluster"),
    MAKERSPACE(3,27,4,"Hammerschlag Makerspace"),
    COVES(2,33,5,"Hammerschlag Coves"),
    GRAD_LOUNGE(3,30,6,"Hammerschlag Grad Lounge"),
    SORELLS(12,26,7,"Sorells Library"),
    W_7500(10,29,8,"Wean 7500"),
    DH_2210(23,31,9,"Doherty 2210"),
    DH_2315(18,30,10,"Doherty 2315"),
    DH_1212(32,21,11,"Doherty 1212"),
    PH100(9,41,12,"Porter 100"),
    GEAGLE_AUD(21,43,13,"Giant Eagle Auditorium"),
    B136(17,42,14,"Baker 136"),
    NSH(14,17,15,"Newell Simon Hall"),
    FRC(15,18,16,"Field Robotics Center"),
    HUNT(30,43,17,"Hunt Library"),
    BLACKCHAIRS(42,20,18,"UC Black Chairs"),
    WEIGAND(46,7,19,"Weigand Gym"),
    MCCONOMY(39,15,20,"McConomy Auditorium"),
    PURNELL(31,17,21,"Purnell Hall"),
    CIC(5,14,22,"CIC"),
    POSNER(44,40,23,"Posner Hall"),
    MAGGIEMO(49,32,24,"Margaret Morrison"),
    SCOTT(9,27,36,"Scott Hall"),
    CFA(39,37,37,"CFA"),

    STEVER(37,0,25,"Stever House"),
    DONNER(60,36,26,"Donner House"),
    MOREWOOD_E(34,0,27,"Morewood E-Tower"),
    HILL(72,42,28,"The Hill"),
    BUSSTOP(49,8,29,"Bus Stop"),
    RESNIK(62,29,30,"Resnik House"),
    WESTWING(53,27,31,"West Wing"),
    GREEKQUAD(44,0,32,"Greek Quad"),
    DOHERTY(68,7,33,"Doherty Apartments"),
    MOREWOOD_G(37,0,34,"Morewood Gardens"),
    MUDGE(37,0,35,"Mudge House");


    private int x;
    private int y;
    private int ID;
    private String name;

    POI(int x, int y, int ID, String name){
        this.x = x;
        this.y = y;
        this.ID = ID;
        this.name = name;
    }

    public int getID(){
        return this.ID;
    }

    public Point getPOILoc() {
        Point p = new Point(this.x, this.y);
        return p;
    }

    public String getName(){
        return this.name;
    }

    public Point getLoc(){
        Point p = new Point();
        p.x = this.x;
        p.y = this.y;
        return p;
    }

    public POI getRandomUGHouse(){
        ArrayList<POI> houses = new ArrayList<>(Arrays.asList(STEVER,DONNER,MOREWOOD_E,MUDGE,HILL));
        Collections.shuffle(houses);
        return houses.get(0);
    }

    public POI getRandomGradHouse(){
        ArrayList<POI> houses = new ArrayList<>(Arrays.asList(BUSSTOP,RESNIK,WESTWING,GREEKQUAD,MOREWOOD_G,DOHERTY));
        Collections.shuffle(houses);
        return houses.get(0);
    }
}
