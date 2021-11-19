package Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

public class XmlUtils {

    public static MapData readMap(String fileName){
        try {
            File file = new File(fileName);
            DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
            DocumentBuilder db = dbf.newDocumentBuilder();
            Document document = db.parse(file);
            String[] listAttributeIntersection = { "latitude", "longitude"};
            String[] listAttributeSegment = {"destination", "length", "name", "origin"};
            ArrayList<HashMap<String, String>> segments=getSegment(document, listAttributeSegment);
            HashMap<String,HashMap<String,String>> intersections=getIntersection(document, listAttributeIntersection);
            MapData mapFormated=new MapData(intersections,segments);
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
    public static HashMap<String,HashMap<String,String>> getIntersection(Document document,String[] attributes){
        document.getDocumentElement().normalize();
        NodeList nList = document.getElementsByTagName("intersection");
        HashMap<String, HashMap<String, String>> mapIntersection=new HashMap<String, HashMap<String, String>>();
        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                HashMap<String,String> currentIntersection=new HashMap<String,String>();
                for (String i:attributes){
                    currentIntersection.put(i,eElement.getAttribute(i));
                }
                mapIntersection.put(eElement.getAttribute("id"),currentIntersection);
            }
        }
        return mapIntersection;
    }
}
