package Model;

import java.util.ArrayList;

public class Graph {
    private ArrayList<Vertice> content;
    public Graph() {
        content=new ArrayList<Vertice>();
    }

    public Graph(ArrayList<Vertice> content) {
        this.content = content;
    }

    public ArrayList<Vertice> getContent() {
        return content;
    }

    public void setContent(ArrayList<Vertice> content) {
        this.content = content;
    }
    public void addVertice(Vertice v){
        if (v!=null) content.add(v);
    }
    @Override
    public String toString() {
        return "Graph{" +
                "content=" + content +
                '}';
    }
}
