package Model;

public class Node {
    private float cost;
    private Intersection intersection;
    private Node predecessor;

    /**
     *
     * @param cost
     * @param intersection
     * @param predecessor
     */
    public Node(float cost, Intersection intersection, Node predecessor) {
        this.cost = cost;
        this.intersection = intersection;
        this.predecessor = predecessor;
    }

    /**
     *
     */
    public Node() {
    }

    /**
     *
     * @return
     */
    public Node getPredecessor() {
        return predecessor;
    }

    /**
     *
     * @return
     */
    public Intersection getIntersection() {
        return intersection;
    }

    /**
     *
     * @param intersection
     */
    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    /**
     *
     * @param predecessor
     */
    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    /**
     *
     * @return
     */
    public float getCost() {
        return cost;
    }

    /**
     *
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
