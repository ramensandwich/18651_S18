package Node;

import Map.POI;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;

/**
 * Created by bradp on 5/1/2018.
 * Types of students for the purpose of determining points of interest
 */
public enum StudentType {
    CFA_F(true,0,new POI[] {POI.CFA, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND, POI.B136}),
    DIET_F(true,1,new POI[] {POI.PH100, POI.B136, POI.GEAGLE_AUD, POI.DH_1212, POI.DH_2210, POI.DH_2315,
        POI.W_7500, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND}),
    CIT_F(true,1,new POI[] {POI.PH100, POI.DH_1212, POI.DH_2210, POI.DH_2315, POI.TAZA, POI.RASHID, POI.G_CLUSTER, POI.G_COMMONS,
        POI.W_7500, POI.SORELLS, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND}),
    MCS_F(true,2,new POI[] {POI.PH100, POI.DH_1212, POI.DH_2210, POI.DH_2315, POI.TAZA, POI.RASHID, POI.G_CLUSTER, POI.G_COMMONS,
        POI.W_7500, POI.SORELLS, POI.MAKERSPACE, POI.COVES, POI.GRAD_LOUNGE, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND}),
    SCS_F(true,3,new POI[] {POI.DH_1212, POI.DH_2210, POI.DH_2315, POI.TAZA, POI.RASHID, POI.G_CLUSTER, POI.G_COMMONS, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND}),
    TPR_F(true,4,new POI[] {POI.DH_1212, POI.DH_2210, POI.DH_2315, POI.POSNER, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND}),
    CFA(false,5,new POI[] {POI.CFA, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND, POI.CFA}),
    DIET(false,6,new POI[] {POI.PH100, POI.B136, POI.GEAGLE_AUD, POI.DH_1212, POI.DH_2210, POI.DH_2315,
        POI.W_7500, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND, POI.HUNT}),
    CIT(false,7,new POI[] {POI.PH100, POI.DH_1212, POI.DH_2210, POI.DH_2315, POI.TAZA, POI.RASHID, POI.G_CLUSTER, POI.G_COMMONS,
        POI.W_7500, POI.SORELLS, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND}),
    MCS(false,8,new POI[] {POI.PH100, POI.DH_1212, POI.DH_2210, POI.DH_2315, POI.TAZA, POI.RASHID, POI.G_CLUSTER, POI.G_COMMONS,
        POI.W_7500, POI.SORELLS, POI.MAKERSPACE, POI.COVES, POI.GRAD_LOUNGE, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND, POI.HUNT}),
    TPR(false,9,new POI[] {POI.DH_1212, POI.DH_2210, POI.DH_2315, POI.POSNER, POI.HUNT, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND}),
    ECE_G(false,10,new POI[] {POI.TAZA, POI.RASHID, POI.G_CLUSTER, POI.G_COMMONS, POI.FRC, POI.NSH, POI.SCOTT, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND}),
    MCS_G(false,11,new POI[] {POI.W_7500, POI.SORELLS, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND}),
    SCS_G(false,12,new POI[] {POI.TAZA, POI.RASHID, POI.G_CLUSTER, POI.G_COMMONS,POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND}),
    ROBO_G(false,13, new POI[] {POI.G_CLUSTER, POI.G_COMMONS, POI.TAZA, POI.RASHID, POI.FRC, POI.NSH, POI.SCOTT, POI.BLACKCHAIRS, POI.MCCONOMY, POI.WEIGAND});

    private boolean freshman;
    private int ID;
    private ArrayList<POI> points;

    StudentType(boolean freshman, int ID, POI[] points){
        this.freshman = freshman;
        this.ID = ID;
        this.points = new ArrayList<>(Arrays.asList(points));
    }

    public ArrayList<POI> getPoints(){
        ArrayList<POI> temppoints = new ArrayList<>(this.points);
        ArrayList<POI> newpoints = new ArrayList<>();
        Collections.shuffle(temppoints);
        //Get 5 random points of interest from list
        for(int i = 0; i < 5; i++){
            newpoints.add(temppoints.get(i));
        }
        return temppoints;
    }

    public POI getHome(){
        POI p;
        //Get a home from list depending on grad status
        if(this.freshman){
            ArrayList<POI> houses = new ArrayList<>(Arrays.asList(POI.STEVER,POI.DONNER,POI.MOREWOOD_E,POI.MUDGE,POI.HILL));
            Collections.shuffle(houses);
            p = houses.get(0);
        } else {
            ArrayList<POI> houses = new ArrayList<>(Arrays.asList(POI.BUSSTOP,POI.RESNIK,POI.WESTWING,POI.GREEKQUAD,POI.MOREWOOD_G,POI.DOHERTY));
            Collections.shuffle(houses);
            p = houses.get(0);
        }
        return p;
    }

    public int getID(){
        return this.ID;
    }
}
