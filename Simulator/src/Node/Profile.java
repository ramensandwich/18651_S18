package Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by bradp on 4/24/2018.
 * Client behavioral profile. Determines preferred time of day and stock likes/dislikes
 */
public class Profile {
    private Map<Stock, Integer> itemweight;
    private int stddev;
    private int stdmean;
    private Random rand;

    public Profile(int ID){
        this.rand = new Random();
        this.itemweight = new HashMap<Stock, Integer>();

        if(ID < 334){
            //Breakfast eater
            this.stdmean = 5040;
        } else if (ID < 667){
            //Lunch eater
            this.stdmean = 8640;
        } else {
            //Dinner eater
            this.stdmean = 12960;
        }
        this.stddev = this.rand.nextInt(1440);

        for(Stock s : Stock.values()){
            //Randomize stock preferences. Can change this to be nonrandom easily.
            //Survey of fellow students didn't show any usable trends. Perhaps try again after campus wide survey?
            int weight = rand.nextInt(100);
            int roll = rand.nextInt(1);
            if(roll == 1){
                weight = weight/2;
            }
            this.itemweight.put(s, weight);
        }
    }

    public boolean getVend(int ticknum) {
        double sample = this.rand.nextGaussian()*this.stddev+this.stdmean;
        //Allow for error when determining roll. Decrease window to lower sales. Increase to heighten them. 
        if(sample > ticknum-15 && sample < ticknum + 15){
            return true;
        } else {
            return false;
        }
    }

    public boolean getItem(Stock s, int roll, boolean recent){
        int counterroll = this.itemweight.get(s);

        //Determine if will make purchase
        if(recent){
            counterroll = counterroll/4;
        }
        if(counterroll > roll){
            return true;
        } else {
            return false;
        }
    }
}
