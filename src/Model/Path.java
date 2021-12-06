package Model;

import java.util.ArrayList;

public class Path {
    private Node path;
    private String id;
    public Path() {
        path=new Node();
    }

    public Path(String id) {
        this.id = id;
        path=new Node();
    }

    public Node getPath() {
        return path;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setPath(Node path) {
        this.path = path;
    }
    public void addToPath(Node n){
        path=n;
    }
    public Vertice toVertice(){
        String[] ids=id.split("-");
        if (ids[0].equals(ids[1])) return null;
        Vertice v=new Vertice(ids[0],ids[1],path.getCost(),this);
        return v;
    }
    @Override
    public String toString() {
        return "Path{" +
                "id=" + id +
                '}';
    }
}
