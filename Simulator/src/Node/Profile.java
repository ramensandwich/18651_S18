package Node;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * Created by bradp on 4/24/2018.
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
            this.stdmean = 5040;
        } else if (ID < 667){
            this.stdmean = 8640;
        } else {
            this.stdmean = 12960;
        }
        this.stddev = this.rand.nextInt(1440);

        for(Stock s : Stock.values()){
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
        if(sample > ticknum-15 && sample < ticknum + 15){
            return true;
        } else {
            return false;
        }
    }

    public boolean getItem(Stock s, int roll, boolean recent){
        int counterroll = this.itemweight.get(s);

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
