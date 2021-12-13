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


import java.text.ParseException;
import java.util.*;

import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Set;

import javax.swing.*;

public class Main {
    public static void main(String[] args) {
        String mapPath = "xmlFiles/smallMap.xml";
        MapData loadedMap = XmlUtils.readMap(mapPath);
        Frame frame = new Frame(loadedMap,mapPath);

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
        frame.display();

        // To be used in tests
    }


}
