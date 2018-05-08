package Node;

/**
 * Created by bradp on 4/25/2018.
 */
public class Purchase {
    public int clientID;
    public int vendorID;
    public String name;
    public double price;

    //Purchase class for the purpose of logging.
    public Purchase(int clientID, int vendorID, String name, double price){
        this.clientID = clientID;
        this.vendorID = vendorID;
        this.name = name;
        this.price = price;
    }
}
