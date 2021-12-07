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

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.*;



public class Main {

    public static void main(String[] args) {



        MapData loadedMap = XmlUtils.readMap("xmlFiles/largeMap.xml");
      Frame frame = new Frame(loadedMap,"xmlFiles/smallMap.xml");
      /*Window frame=new Window(1000,700,loadedMap);
      
        Request loadRequest=XmlUtils.ReadRequest("xmlFiles/requestsLarge7.xml",loadedMap.getIntersections());
        //System.out.println(loadRequest);
        HashMap<String,Point> pointList=loadRequest.getListePoint();
        Point depot=loadRequest.getDepot();
        System.out.print("depotId=");
        System.out.println(depot.getId());
        pointList.put(depot.getId(),depot);
        Graph g=Algorithm.createGraph(pointList,loadedMap,depot);

        MapData loadedMap = XmlUtils.readMap("xmlFiles/smallMap.xml");

       // System.out.println(loadedMap);

       // System.out.println("hello World");
        
       // Window frame=new Window(1000,700,loadedMap);
       // Request loadRequest=XmlUtils.ReadRequest("xmlFiles/requestsSmall2.xml",loadedMap.getIntersections());
        //System.out.println(loadRequest);

/*
       HashMap<String,Point> pointList=loadRequest.getListePoint();
        Graph g=Algorithm.createGraph(pointList,loadedMap);

        System.out.println(g);

        g.setSolution(Algorithm.TSP(g));
        System.out.println(g.getSolution());
        Map m=frame.getMap();
        m.setGraph(g);

        m.repaint();
        System.out.println(g);
       /* ArrayList<Integer> ai=new ArrayList<>();
        ai.add(9);
        int i=0;
        ai.add(i);
        ai.add(6);
        ai.add(5);
        Collections.sort(ai);
        System.out.println(ai);
        System.out.println(i);*/


    

        
*/

        try {
            // Set cross-platform Java L&F (also called "Metal")
            UIManager.setLookAndFeel(
                    UIManager.getSystemLookAndFeelClassName());
        }
        catch (UnsupportedLookAndFeelException e) {
            // handle exception
        }
        catch (ClassNotFoundException e) {
            // handle exception
        }
        catch (InstantiationException e) {
            // handle exception
        }
        catch (IllegalAccessException e) {
            // handle exception
        }


	   // MapData loadedMap = XmlUtils.readMap("xmlFiles/smallMap.xml");
        //System.out.println(loadedMap);

        //Request loadRequest=XmlUtils.ReadRequest("xmlFiles/requestsSmall2.xml",loadedMap.getIntersections());
        //System.out.println(loadRequest);


        frame.display();


    }
}
