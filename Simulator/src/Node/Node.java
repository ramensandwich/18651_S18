package Node;

/**
 * Created by bradp on 2/27/2018.
 */
public abstract class Node {
    private int ID = -1;
    private NodeType type;


    protected Node(int ID, NodeType type){
        this.ID = ID;
        this.type = type;
    }

    public int getID(){
        return this.ID;
    }

    public NodeType getType(){
        return this.type;
    }
}
