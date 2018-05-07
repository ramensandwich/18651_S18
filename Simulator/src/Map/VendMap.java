package Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bradp on 4/12/2018.
 */
public class VendMap {
    private int length;
    private int height;
    private ArrayList<ArrayList<Cell>> vmap;
    //private ArrayList<POI> points;

    public VendMap(int length, int height){
        this.length = length;
        this.height = height;
        vmap = new ArrayList<>(height);
        //points = new ArrayList<>();
        for(int i = 0; i < height; i++){
            ArrayList<Cell> row = new ArrayList<>(length);
            for(int j = 0; j < length; j++){
                Cell c = new Cell(i, j);
                row.add(j, c);
            }
            vmap.add(i, row);
        }
        //generatePoints();
    }

    public Cell getCell(int x, int y){
        ArrayList<Cell> row = vmap.get(y);
        Cell c = row.get(x);
        return c;
    }

   /* private void generatePoints(){
        Random rand = new Random();
        for(int i = 0; i < 50; i++){
            int x = rand.nextInt(84);
            int y = rand.nextInt(56);
            POI p = new POI(x, y, i, Integer.toString(i));
            this.points.add(p);
            Cell c = getCell(x,y);
            c.addPOI(p);
        }
    }

    public POI getPoint(int ID){
        POI p = this.points.get(ID);
        return p;
    }

    public List<POI> getPoints(){
        List<POI> points = new ArrayList<>();
        for(POI point : this.points){
            points.add(point);
        }
        return points;
    }

    public int getPointNum(){
        return this.points.size();
    }*/

}
