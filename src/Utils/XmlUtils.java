package Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import Model.MapData;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtils {

    public static MapData readMap(String fileName) {
        try {
            File file = new File(fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            String[] listAttributeIntersection = {"latitude", "longitude"};
            String[] listAttributeSegment = {"destination", "length", "name", "origin"};
            ArrayList<HashMap<String, String>> segments = getSegment(document, listAttributeSegment);
            HashMap<String, HashMap<String, Float>> intersections = getIntersection(document, listAttributeIntersection);
            HashMap<String, Float> maxi = intersections.remove("maxi");
            HashMap<String, Float> mini = intersections.remove("mini");
            MapData mapFormated = new MapData(intersections, segments, mini.get("longitude"), maxi.get("longitude"), mini.get("latitude"), maxi.get("latitude"));
            return mapFormated;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.out.println("Error while opening file" + e.toString());
            return null;
        }
    }

    public static ArrayList<HashMap<String, String>> getSegment(Document document, String[] attributes) {
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("segment");
        ArrayList<HashMap<String, String>> listeElement = new ArrayList<HashMap<String, String>>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                HashMap<String, String> attributeMap = new HashMap<String, String>();
                for (String i : attributes) {
                    attributeMap.put(i, eElement.getAttribute(i));
                }
                listeElement.add(attributeMap);
            }
        }
        return listeElement;
    }

    public static HashMap<String, HashMap<String, Float>> getIntersection(Document document, String[] attributes) {
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("intersection");
        HashMap<String, HashMap<String, Float>> mapIntersection = new HashMap<String, HashMap<String, Float>>();
        boolean flag = true;
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                HashMap<String, Float> currentIntersection = new HashMap<String, Float>();
                for (String i : attributes) {
                    currentIntersection.put(i, Float.parseFloat(eElement.getAttribute(i)));
                }
                if (flag) {
                    HashMap<String,Float> maxi=new HashMap<String,Float>(currentIntersection);
                    HashMap<String,Float> mini=new HashMap<String,Float>(currentIntersection);
                    mapIntersection.put("maxi", maxi);
                    mapIntersection.put("mini",mini);
                    flag = false;
                }else{
                    HashMap<String,Float> maxi=mapIntersection.get("maxi");
                    HashMap<String,Float> mini=mapIntersection.get("mini");
                    Float currentY=currentIntersection.get("latitude");
                    Float currentX=currentIntersection.get("longitude");
                    if (maxi.get("latitude")<currentY) {
                        maxi.replace("latitude", currentY);
                    }
                    if (mini.get("latitude")>currentY) {
                        mini.replace("latitude", currentY);
                    }
                    if (maxi.get("longitude")<currentX) {
                        maxi.replace("longitude", currentX);
                    }
                    if (mini.get("longitude")>currentX) {
                        mini.replace("longitude", currentX);
                    }
                    mapIntersection.replace("mini",mini);
                    mapIntersection.replace("maxi",maxi);
                }
                mapIntersection.put(eElement.getAttribute("id"), currentIntersection);
            }
        }
        return mapIntersection;
    }
}
