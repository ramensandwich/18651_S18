package Map;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by bradp on 4/12/2018.
 * Map of area where points are seeded around.
 */
public class VendMap {
    private int length;
    private int height;
    private ArrayList<ArrayList<Cell>> vmap;

    public VendMap(int length, int height){
        this.length = length;
        this.height = height;
        vmap = new ArrayList<>(height);
        for(int i = 0; i < height; i++){
            ArrayList<Cell> row = new ArrayList<>(length);
            for(int j = 0; j < length; j++){
                Cell c = new Cell(i, j);
                row.add(j, c);
            }
            vmap.add(i, row);
        }
    }

    public Cell getCell(int x, int y){
        ArrayList<Cell> row = vmap.get(y);
        Cell c = row.get(x);
        return c;
    }

}
