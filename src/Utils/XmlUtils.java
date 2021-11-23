package Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import Model.Intersection;
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
            HashMap<String, Intersection> intersections = getIntersection(document);
            getSegment(document, intersections);
            Intersection maxi = intersections.remove("maxi");
            Intersection mini = intersections.remove("mini");
            MapData mapFormated = new MapData(intersections, mini.getLongitude(), maxi.getLongitude(), mini.getLatitude(), maxi.getLatitude());
            return mapFormated;
        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.out.println("Error while opening file" + e.toString());
            return null;
        }
    }

    public static void getSegment(Document document, HashMap<String,Intersection> intersections) {
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("segment");
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String origin=eElement.getAttribute("origin");
                String destination=eElement.getAttribute("destination");
                float length=Float.parseFloat(eElement.getAttribute("length"));
                intersections.get(origin).addNeighbor(destination,length);
            }
        }
    }

    public static HashMap<String, Intersection> getIntersection(Document document) {
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("intersection");
        //HashMap<String, HashMap<String, Float>> mapIntersection = new HashMap<String, HashMap<String, Float>>();
        HashMap<String, Intersection> inters=new HashMap<String,Intersection>();
        boolean flag = true;
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                Intersection currentIntersection = new Intersection();
                /*for (String i : attributes) {
                    currentIntersection.put(i, Float.parseFloat(eElement.getAttribute(i)));
                }*/
                currentIntersection.setId(eElement.getAttribute("id"));
                currentIntersection.setLatitude(Float.parseFloat(eElement.getAttribute("latitude")));
                currentIntersection.setLongitude(Float.parseFloat(eElement.getAttribute("longitude")));
                if (flag) {
                    Intersection maxi=new Intersection(currentIntersection);
                    Intersection mini=new Intersection(currentIntersection);
                    inters.put("maxi", maxi);
                    inters.put("mini",mini);
                    flag = false;
                }else{
                    Intersection maxi=inters.get("maxi");
                    Intersection mini=inters.get("mini");
                    Float currentY=currentIntersection.getLatitude();
                    Float currentX=currentIntersection.getLongitude();
                    if (maxi.getLatitude()<currentY) {
                        maxi.setLatitude(currentY);
                    }
                    if (mini.getLatitude()>currentY) {
                        mini.setLatitude(currentY);
                    }
                    if (maxi.getLongitude()<currentX) {
                        maxi.setLongitude(currentX);
                    }
                    if (mini.getLongitude()>currentX) {
                        mini.setLongitude(currentX);
                    }
                    inters.replace("mini",mini);
                    inters.replace("maxi",maxi);
                }
                inters.put(eElement.getAttribute("id"), currentIntersection);
            }
        }
        return inters;
    }
}
