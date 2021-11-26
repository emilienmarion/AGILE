package Controller;


import Model.Intersection;
import Model.MapData;
import Model.Point;
import Model.Request;
import Utils.XmlUtils;
import View.*;


import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.*;



public class Main {

    public static void main(String[] args) {

        /*MapData loadedMap = XmlUtils.readMap("xmlFiles/smallMap.xml");


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


	// write your code here
        MapData loadedMap = XmlUtils.readMap("../xmlFiles/smallMap.xml");

        System.out.println(loadedMap);

        System.out.println("hello World");

        Window frame=new Window(1000,700,loadedMap);
        Request loadRequest=XmlUtils.ReadRequest("xmlFiles/requestsSmall2.xml",loadedMap.getIntersections());
        System.out.println(loadRequest);*/
        /*Intersection i=new Intersection();
        Point p=new Point();
        i.setId("coucou");
        p.setId("coucou");
        System.out.println(i.equals(p));*/

        //Window frame = new Window(1000,700,loadedMap);

        Frame frame = new Frame();
        frame.display();

        // Window frame=new Window(1000,700,loadedMap);
        /*HashMap<String,HashMap<String,Float>> test=new HashMap<String, HashMap<String, Float>>();
        HashMap <String,Float> current=new HashMap<String,Float>();
        current.put("latitude",new Float (50.2));
        current.put("longitude",new Float(40.3));
        HashMap <String,Float> current2=new HashMap<String,Float>();
        current2.put("latitude",new Float (110.2));
        current2.put("longitude",new Float(450.3));
        test.put("maxi",current2);
        test.put("mini",current);
        System.out.println(test);
        test.get("maxi").replace("latitude",new Float(5.5));
        System.out.println(test);*/


    }
}
