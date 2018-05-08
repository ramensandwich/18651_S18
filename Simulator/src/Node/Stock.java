package Node;

/**
 * Created by bradp on 4/24/2018.
 * Stock enum. Expiration currently does not do anything. 
 */
public enum Stock {
    DORITOS (-1, 1.00),
    DONUTS (50, 1.00),
    BANANA (10, 1.00),
    SANDWICH (10, 1.00),
    COKE (-1, 1.00),
    SNICKERS (-1, 1.00),
    JUICE (20, 1.00),
    CARROTS (10, 1.00);

    private final int expiration;
    private double price;
    Stock(int expiration, double price){
        this.expiration = expiration;
        this.price = price;
    }

    public int getExpiration(){
        return this.expiration;
    }

    public double getPrice(){
        return this.price;
    }

    public void setPrice(double newprice){
        this.price = newprice;
    }
}
