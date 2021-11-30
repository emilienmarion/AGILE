package Model;

public class Node {
    private float cost;
    private Intersection intersection;
    private Node predecessor;

    public Node(float cost, Intersection intersection, Node predecessor) {
        this.cost = cost;
        this.intersection = intersection;
        this.predecessor = predecessor;
    }

    public Node() {
    }

    public Node getPredecessor() {
        return predecessor;
    }

    public Intersection getIntersection() {
        return intersection;
    }

    public void setIntersection(Intersection intersection) {
        this.intersection = intersection;
    }

    public void setPredecessor(Node predecessor) {
        this.predecessor = predecessor;
    }

    public float getCost() {
        return cost;
    }

    public void setCost(float cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        String msg=new String();
        if (predecessor!=null) msg=Float.toString(predecessor.getCost());
        else msg="null";
        return "Path{" +
                "cost=" + cost +" intersection="+intersection.toString()+
                ", predecessor=" +msg +
                '}';
    }
}
