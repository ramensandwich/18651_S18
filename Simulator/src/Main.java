import Map.VendMap;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * Created by bradp on 4/12/2018.
 */
public class Main {

    public static void main(String[] args) throws IOException {
        Simulator sim  = new Simulator(1000, 20,84,56);

        PrintWriter printB = new PrintWriter(new File("breakfast.json"));
        PrintWriter printL = new PrintWriter(new File("lunch.json"));
        PrintWriter printD = new PrintWriter(new File("dinner.json"));

        PrintWriter outB = new PrintWriter(new File("breakfastbuy.json"));
        PrintWriter outL = new PrintWriter(new File("lunchbuy.json"));
        PrintWriter outD = new PrintWriter(new File("dinnerbuy.json"));

        PrintWriter vendA = new PrintWriter(new File("vendorA.txt"));
        PrintWriter priceA = new PrintWriter(new File("priceA.txt"));
        PrintWriter salesA = new PrintWriter(new File("salesA.txt"));

        printB.close();
        printL.close();

        printD.close();

        outB.close();
        outL.close();
        outD.close();

        vendA.close();
        priceA.close();
        salesA.close();

        //17280
        for(int i = 0; i < 17280; i++){
            sim.tick(i);
        }
        sim.endSim();

    }
}
