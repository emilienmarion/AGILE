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


        MapData loadedMap = XmlUtils.readMap("xmlFiles/smallMap.xml");

       // System.out.println(loadedMap);

       // System.out.println("hello World");
        Frame frame = new Frame(loadedMap,"xmlFiles/smallMap.xml");
       // Window frame=new Window(1000,700,loadedMap);
       // Request loadRequest=XmlUtils.ReadRequest("xmlFiles/requestsSmall2.xml",loadedMap.getIntersections());
        //System.out.println(loadRequest);

/*
       HashMap<String,Point> pointList=loadRequest.getListePoint();
        Graph g=Algorithm.createGraph(pointList,loadedMap);
        System.out.println(g);
        ArrayList<Path> ap=Algorithm.TSP(g);
        System.out.println(ap);
        Map m=frame.getMapView().getMap();
        m.setWay(ap);
        m.repaint();
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
