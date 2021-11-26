package Utils;

import Model.Intersection;
import Model.Path;
import Model.Point;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.SortedMap;
import java.util.TreeMap;

public class Algorithm {
    //algorithme dijkstra qui charge toute la map en calculant que le cout pour le moment
    //-->Amelioration, Rajouter des attributs dans path avec une linked list d'intersection pour pouvoir printer
    //Remarque: dans notre cas on part d'un Point donné
    public HashMap<String, Path> dijkstra(HashMap<String, Intersection> intersections,Point point){
        System.out.println("Dijkstra");
        HashMap<String,Path> paths=new HashMap<String,Path>();
        //on init avec le premier path qui a un cout de 0 car on y est deja
        String startId=point.getId();
        paths.put(startId,new Path(0));
        //maintenant on s'occupe des intersections
        Intersection current=new Intersection(intersections.get(startId));
        TreeMap<Integer,Intersection> queue=new TreeMap<Integer,Intersection>(); //map with sorted keys
        
        return paths;
    }
}
