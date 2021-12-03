package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private ArrayList<ArrayList<Vertice>> content;
    private HashMap<String,Integer> tableIndex;
    private int dimension;
    private int compteur;
    public Graph(int dimension) {
        content=new ArrayList<ArrayList<Vertice>>();
        tableIndex=new HashMap<String,Integer>();
        for (int i=0;i<dimension;i++){
            ArrayList<Vertice> current=new ArrayList<Vertice>();
            for (int k=0;k<dimension;k++){
                current.add(null);
            }
            content.add(current);
        }
        compteur=0;
        this.dimension=dimension;
    }

    public Graph(ArrayList<ArrayList<Vertice>> content) {
        this.content = content;
        tableIndex=new HashMap<String,Integer>();
        compteur=0;
    }

    public ArrayList<ArrayList<Vertice>> getContent() {
        return content;
    }

    public int getDimension() {
        return dimension;
    }

    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    public void setContent(ArrayList<ArrayList<Vertice>> content) {
        this.content = content;
    }
    public void addVertice(Vertice v){
        if (v!=null) {
            String idOrigin = v.getIdOrigin();
            String idDestination = v.getIdDestination();
            if (tableIndex.get(idOrigin) == null) {
                tableIndex.put(idOrigin, compteur);
                compteur++;
            }
            if (tableIndex.get(idDestination) == null) {
                tableIndex.put(idDestination, compteur);
                compteur++;
            }
            int indexOrigin = tableIndex.get(idOrigin);
            int indexDestination = tableIndex.get(idDestination);
            System.out.println(tableIndex);
            System.out.println(content);
            content.get(indexOrigin).set(indexDestination, v);
        }
    }

    public HashMap<String, Integer> getTableIndex() {
        return tableIndex;
    }

    public void setTableIndex(HashMap<String, Integer> tableIndex) {
        this.tableIndex = tableIndex;
    }

    @Override
    public String toString() {
        return "Graph{" +
                "content=" + content +
                '}';
    }
}
