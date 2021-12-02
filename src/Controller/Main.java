package Controller;


import Model.*;
import Utils.Algorithm;
import Utils.GraphConverter;
import Utils.TSP.CompleteGraph;
import Utils.TSP.TSP;
import Utils.TSP.TSP1;
import Utils.TSP.TSPGraph;
import Utils.XmlUtils;
import View.*;
import View.Map;

import java.util.*;


public class Main {

    public static void main(String[] args) {
        MapData loadedMap = XmlUtils.readMap("xmlFiles/smallMap.xml");
        System.out.println(loadedMap);
        System.out.println("hello World");
        Window frame=new Window(1000,700,loadedMap);
        Request loadRequest=XmlUtils.ReadRequest("xmlFiles/requestsSmall2.xml",loadedMap.getIntersections());
        System.out.println(loadRequest);
        HashMap<String,Point> pointList=loadRequest.getListePoint();
        Graph g=Algorithm.createGraph(pointList,loadedMap);
        System.out.println(g);
        ArrayList<Path> ap=Algorithm.TSP(g);
        System.out.println(ap);
        Map m=frame.getMap();
        m.setWay(ap);
        m.repaint();
        /*
        /*Intersection i=new Intersection();
        Point p=new Point();
        i.setId("coucou");
        p.setId("coucou");
        System.out.println(i.equals(p));*/
        /*HashMap <String,String> hm=new HashMap<String, String>();
        hm.put("ccuccuo","ccc");
        hm.put("bjr","bjr");
        System.out.println(hm.get("ll"));*/
        //CompleteGraph cg=new CompleteGraph(5);
        /*TSP tsp = new TSP1();
        for (int nbVertices = 8; nbVertices <= 16; nbVertices += 2){
            System.out.println("Graphs with "+nbVertices+" vertices:");
            TSPGraph g = new CompleteGraph(nbVertices);
            long startTime = System.currentTimeMillis();
            tsp.searchSolution(20000, g);
            System.out.print("Solution of cost "+tsp.getSolutionCost()+" found in "
                    +(System.currentTimeMillis() - startTime)+"ms : ");
            for (int i=0; i<nbVertices; i++)
                System.out.print(tsp.getSolution(i)+" ");
            System.out.println("0");
        }*/
    }
}
