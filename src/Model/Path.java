package Model;

public class Path {
    private int cost;

    public Path(int cost) {
        this.cost = cost;
    }

    public Path() {
    }

    public int getCost() {
        return cost;
    }

    public void setCost(int cost) {
        this.cost = cost;
    }

    @Override
    public String toString() {
        return "Path{" +
                "cost=" + cost +
                '}';
    }
}
