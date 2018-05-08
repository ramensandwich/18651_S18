package Map;

import java.util.ArrayList;

/**
 * Created by bradp on 4/12/2018.
 * Map cell. Container for Point.
 */
public class Cell {
    private int x;
    private int y;
    private boolean isPOI;
    private ArrayList<POI> points;

    public Cell(int x, int y){
        this.x = x;
        this.y = y;
        this.points = new ArrayList<>();
    }

    public void addPOI(POI point){
        this.points.add(point);
        this.isPOI = true;
    }

    public ArrayList<POI> getPOIs(){
        ArrayList<POI> newpoints = new ArrayList<>();
        for(int i = 0; i < this.points.size(); i++){
            newpoints.add(this.points.get(i));
        }
        return newpoints;
    }

    public boolean hasPOI(){
        return this.isPOI;
    }
}
