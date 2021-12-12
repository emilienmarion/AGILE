package Model;

import java.util.ArrayList;

public class Path {
    private Node path;
    private String id;

    /**
     *
     */
    public Path() {
        path=new Node();
    }

    /**
     *
     * @param id
     */
    public Path(String id) {
        this.id = id;
        path=new Node();
    }

    /**
     *
     * @return
     */
    public Node getPath() {
        return path;
    }

    /**
     *
     * @return
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     *
     * @param path
     */
    public void setPath(Node path) {
        this.path = path;
    }

    /**
     *
     * @param n
     */
    public void addToPath(Node n){
        path=n;
    }

    /**
     *
     * @return
     */
    public Vertice toVertice(){
        String[] ids=id.split("-");
        if (ids[0].equals(ids[1])) return null;
        Vertice v=new Vertice(ids[0],ids[1],path.getCost(),this);
        return v;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Path{" +
                "id=" + id +
                '}';
    }
}
