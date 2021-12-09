package Utils;

import Model.*;
import Utils.TSP.TSP;
import Utils.TSP.TSP1;
import Utils.TSP.TSPGraph;

import java.util.*;

public class Algorithm {
    private static int min(ArrayList<Float> queueKeys){
        float minima=-1;
        int index=-1;
        for (int i=0;i<queueKeys.size();i++){
            if (i==0){
                minima=queueKeys.get(i);
                index=0;
            }
            else{
                if (minima>queueKeys.get(i)){
                    index=i;
                }
            }
        }
        return index;
    }

    //algorithme dijkstra qui charge toute la map en calculant que le cout pour le moment
    //-->Amelioration, Rajouter des attributs dans path avec une linked list d'intersection pour pouvoir printer
    //Remarque: dans notre cas on part d'un Point donné
    public static HashMap<String, Node> dijkstra(HashMap<String, Intersection> intersections, Point point){
        HashMap<String, Node> paths=new HashMap<String, Node>();
        //on init avec le premier path qui a un cout de 0 car on y est deja
        String startId=point.getId();
        //maintenant on s'occupe des intersections
        Intersection start=intersections.get(startId);
        paths.put(startId,new Node(0,start,null));

        ArrayList<Float> queueKeys=new ArrayList();
        ArrayList<Intersection> queueIntersection=new ArrayList();
        ArrayList<Intersection> queueOrigin=new ArrayList();
        queueKeys.add(new Float(0));
        queueIntersection.add(start);
        queueOrigin.add(null);
        while (queueKeys.size()!=0){
            int indexMin=min(queueKeys);
            Intersection current=queueIntersection.get(indexMin);
            HashMap<String,Float> voisins=current.getNeighbors();
            for (String id:voisins.keySet()){
                if (paths.get(id)==null) {
                    float newWay=paths.get(current.getId()).getCost();
                    float newCost=newWay+voisins.get(id);
                    String ide=current.getId();
                    Node p=new Node(newCost,intersections.get(id),paths.get(ide));
                    paths.put(id,p);
                    queueKeys.add(newCost);
                    queueIntersection.add(intersections.get(id));
                }
                else{
                    Node p=paths.get(id);
                    float oldCost=p.getCost();
                    float newWay=paths.get(current.getId()).getCost();
                    float newCost=newWay+voisins.get(id);
                    if (oldCost>newCost){
                        p.setCost(newCost);
                        p.setPredecessor(paths.get(current.getId()));
                    }
                }
            }
            queueKeys.remove(indexMin);
            queueIntersection.remove(indexMin);
        }
        return paths;
    }

    public static Path getPath(Node n){
        String idDestination=n.getIntersection().getId();
        String idOrigin=new String();
        Path p=new Path();
        p.addToPath(n);
        while (n!=null){
            idOrigin=n.getIntersection().getId();
            n=n.getPredecessor();
        }
        p.setId(idOrigin+"-"+idDestination);
        return p;
    }
    public static Graph createGraph(HashMap<String,Point> pointList,MapData loadedMap,Point depot){
        Set<String> pointListKeySet=pointList.keySet();
        int length=pointListKeySet.size();
        Graph g=new Graph(length,pointList,depot);
        for (String pointId:pointListKeySet) {
            Point p=pointList.get(pointId);
            HashMap<String, Node> result = dijkstra(loadedMap.getIntersections(), p);

            for (String s : pointListKeySet) {
                g.addVertice(Algorithm.getPath(result.get(s)).toVertice());
            }
        }
        return g;
    }
    public static ArrayList<Path> TSP(Graph g){
        TSPGraph cg= GraphConverter.toTSPGraph(g);
        TSP tsp=new TSP1();
        long startTime = System.currentTimeMillis();
        tsp.searchSolution(20000, cg);
        System.out.println("Solution of cost "+tsp.getSolutionCost()+" found in "
                +(System.currentTimeMillis() - startTime)+"ms : ");
        ArrayList<ArrayList<Vertice>> tv=g.getContent();
        int dim=g.getDimension();
        ArrayList<Path> result=new ArrayList<Path>();
        for (int i=0; i<dim-1; i++) {
            Vertice v = tv.get(tsp.getSolution(i)).get(tsp.getSolution(i + 1));
            System.out.print(v);
            System.out.print("-->");
            System.out.println(v.getAssociatedPath());
            result.add(v.getAssociatedPath());
        }
        Vertice v=tv.get(tsp.getSolution(dim-1)).get(0);
        System.out.print(v);
        System.out.print("-->");
        System.out.println(v.getAssociatedPath());
        result.add(v.getAssociatedPath());
        System.out.println(tsp.getNbConfig());
        return result;
    }
}
