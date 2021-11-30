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


	    MapData loadedMap = XmlUtils.readMap("xmlFiles/smallMap.xml");
        System.out.println(loadedMap);



       // Window frame=new Window(1000,700,loadedMap);
        Request loadRequest=XmlUtils.ReadRequest("xmlFiles/requestsSmall2.xml",loadedMap.getIntersections());
        System.out.println(loadRequest);


       // Window frameh = new Window(1000,700,loadedMap);

        Frame frame = new Frame(loadedMap);
        frame.display();

        // Window frame=new Window(1000,700,loadedMap);



    }
}
