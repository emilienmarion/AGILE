package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Graph {
    private ArrayList<ArrayList<Vertice>> content;
    private HashMap<String,Integer> tableIndex;
    private HashMap<String,Point> listePoint;
    private int dimension;
    private int compteurPickUp;
    private int compteurDelivery;
    private ArrayList<Path> solution;

    public Graph(int dimension,HashMap<String,Point> listePoint,Point depot) {



        content=new ArrayList<ArrayList<Vertice>>();
        tableIndex=new HashMap<String,Integer>();
        for (int i=0;i<dimension;i++){
            ArrayList<Vertice> current=new ArrayList<Vertice>();
            for (int k=0;k<dimension;k++){
                current.add(null);
            }
            content.add(current);
        }
        compteurPickUp=1;
        compteurDelivery=2;
        this.dimension=dimension;
        this.listePoint=listePoint;
        tableIndex.put(depot.getId(),0);
    }

    public Graph(ArrayList<ArrayList<Vertice>> content) {
        this.content = content;
        tableIndex=new HashMap<String,Integer>();
        compteurPickUp=0;
        compteurDelivery=1;
    }

    public ArrayList<Path> getSolution() {
        return solution;
    }

    public void setSolution(ArrayList<Path> solution) {
        this.solution = solution;
    }

    public ArrayList<ArrayList<Vertice>> getContent() {
        return content;
    }

    public HashMap<String, Point> getListePoint() {
        return listePoint;
    }

    public void setListePoint(HashMap<String, Point> listePoint) {
        this.listePoint = listePoint;
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
                if (listePoint.get(idOrigin).getType().equals("delivery")) {
                    tableIndex.put(idOrigin, compteurDelivery);
                    compteurDelivery += 2;
                } else {
                    tableIndex.put(idOrigin, compteurPickUp);
                    compteurPickUp += 2;
                }
            }
            if (tableIndex.get(idDestination) == null) {
                if (listePoint.get(idDestination).getType().equals("delivery")) {
                    tableIndex.put(idDestination, compteurDelivery);
                    compteurDelivery += 2;
                } else {
                    tableIndex.put(idDestination, compteurPickUp);
                    compteurPickUp += 2;
                }
            }
            int indexOrigin = tableIndex.get(idOrigin);
            int indexDestination = tableIndex.get(idDestination);
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
