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

    /**
     *  constructor of the Class Graph
     * @param dimension  (Number of Point in the query)
     * @param listePoint point in the query
     * @param depot   starting Point in the tour
     */
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



    /**
     *
     * @return
     */
    public ArrayList<Path> getSolution() {
        return solution;
    }

    /**
     *  set the list of path which is the TSP solution
     * @param solution
     */
    public void setSolution(ArrayList<Path> solution) {
        this.solution = solution;
    }

    /**
     * get  the list of all the vertice in the graph
     * @return
     */
    public ArrayList<ArrayList<Vertice>> getContent() {
        return content;
    }

    /**
     * get the point list of the request
     * @return listePoint
     */
    public HashMap<String, Point> getListePoint() {
        return listePoint;
    }

    /**
     * Set the List of the oint in the request
     * @param listePoint
     */
    public void setListePoint(HashMap<String, Point> listePoint) {
        this.listePoint = listePoint;
    }

    /**
     *
     * @return
     */
    public int getDimension() {
        return dimension;
    }

    /**
     *
     * @param dimension  
     */
    public void setDimension(int dimension) {
        this.dimension = dimension;
    }

    /**
     *
     * @param content
     */
    public void setContent(ArrayList<ArrayList<Vertice>> content) {
        this.content = content;
    }

    /**
     * add a vertice to the graph
     * @param v
     */
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
            System.out.print("indexOrigin=");
            System.out.println(indexOrigin);
            System.out.print("indexDestination=");
            System.out.println(indexDestination);
            content.get(indexOrigin).set(indexDestination, v);
        }
    }

    /**
     * get the TableIndex
     * @return
     */
    public HashMap<String, Integer> getTableIndex() {
        return tableIndex;
    }

    /**
     * Set the TableIndex
     * @param tableIndex
     */
    public void setTableIndex(HashMap<String, Integer> tableIndex) {
        this.tableIndex = tableIndex;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Graph{" +
                "content=" + content +
                '}';
    }
}
