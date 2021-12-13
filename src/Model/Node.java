package Model;

public class Node {
    private float cost;
    private Intersection intersection;
    private Node predecessor;

    /**
     * Constructor of the Class Node
     * @param cost          Associated Cosst
     * @param intersection  Associated Intersection
     * @param predecessor Previous Node on the path
     */
    public Node(float cost, Intersection intersection, Node predecessor) {
        this.cost = cost;
        this.intersection = intersection;
        this.predecessor = predecessor;
    }

    /**
     * empty constructor of the Class
     */
    public Node() {
    }

    /**
     * get the previous Node in a path
     * @return Node
     */
    public Node getPredecessor() {
        return predecessor;
    }


    /**
     * get the previous Node in a path
     * @param predecessor
     */
    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    /**
     * get the Associated Intersection
     * @return Associated Intersection
     */
    public Intersection getIntersection() {
        return intersection;
    }

    /**
     * set the Associated Intersection
     * @param intersection
     */
    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }



    /**
     * get the Associated Cost
     * @return cost
     */
    public float getCost() {
        return cost;
    }

    /**
     * set the Associated Cost
     * @param cost
     */
    public void setCost(float cost) {
        this.cost = cost;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        String msg= "";
        if (predecessor!=null) msg=Float.toString(predecessor.getCost());
        else msg="null";
        return "Node{" +
                "cost=" + cost +
                ", id="+intersection.getId()+
                '}';
    }
}
