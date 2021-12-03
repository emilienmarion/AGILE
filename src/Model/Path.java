package Model;

import java.util.ArrayList;

public class Path {
    private ArrayList<Node> path;
    private String id;
    public Path() {
        path=new ArrayList<Node>();
    }

    public Path(String id) {
        this.id = id;
        path=new ArrayList<Node>();
    }

    public ArrayList<Node> getPath() {
        return path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPath(ArrayList<Node> path) {
        this.path = path;
    }
    public void addToPath(Node n){
        path.add(n);
    }
    public Vertice toVertice(){
        String[] ids=id.split("-");
        if (ids[0].equals(ids[1])) return null;
        Vertice v=new Vertice(ids[0],ids[1],path.get(0).getCost(),this);
        return v;
    }
    @Override
    public String toString() {
        return "Path{" +
                "id=" + id +
                '}';
    }
}
