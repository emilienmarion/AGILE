package Utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import Model.Intersection;
import Model.MapData;
import Model.Point;
import Model.Request;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;



public class XmlUtils {

    /**
     * Build the object Request from the XML FIle and the Hashmap which contains all Intersections
     * @param fileName
     * @param intersections
     * @return
     */
    public static Request ReadRequest(String fileName,HashMap<String,Intersection> intersections){
        Request req=null;
        try {
        File file = new File(fileName);
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = dbf.newDocumentBuilder();
        Document document = db.parse(file);

           req=getRequest(document,intersections);

        } catch (IOException | ParserConfigurationException | SAXException e) {
            System.out.println("Error while opening file" + e);
            return null;
        }
        return req;
    }

    /**
     *  Build the object Request from the XML FIle and the Hashmap which contains all Intersections
     * @param document
     * @param intersections
     * @return
     */
    private static Request getRequest(Document document,HashMap<String,Intersection> intersections) {
        document.getDocumentElement().normalize();
        Point depot=null;
        String departureTime="";
        HashMap<String,Point> listePoint=new HashMap<String,Point>();

        NodeList nListDep = document.getElementsByTagName("depot");
        for (int temp = 0; temp < nListDep.getLength(); temp++) {
            Node nNode = nListDep.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String addressDepotS = eElement.getAttribute("address");
                //System.out.println("jhzgfvguydfvhgsdghcgsghcdshg"+ addressDepotS);
                 departureTime = eElement.getAttribute("departureTime");

                Intersection interdepot=intersections.get(addressDepotS);
               // System.out.println("jhzgfvguydfvhgsdghcgsghcdshg"+interdepot);
                 depot=new Point(interdepot,0,"depot");

            }
        }

        NodeList nList = document.getElementsByTagName("request");

        for (int temp = 0; temp < nList.getLength(); temp++) {
            Node nNode = nList.item(temp);
            if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                Element eElement = (Element) nNode;
                String pickUpS=eElement.getAttribute("pickupAddress");

                String deliveryS=eElement.getAttribute("deliveryAddress");
                System.out.println("jhzgfvguydfvhgsdghcgsghcdshg"+  deliveryS);
                int  pickupDuration= Integer.parseInt(eElement.getAttribute("pickupDuration"));
                int  deliveryDuration= Integer.parseInt(eElement.getAttribute("deliveryDuration"));
               Intersection interPickUp=intersections.get(pickUpS);

               Point pickUp=new Point(interPickUp,pickupDuration,"pickUp");

                Intersection interdelivery=intersections.get(deliveryS);


                Point delivery=new Point(interdelivery,deliveryDuration,"delivery");
                delivery.setIdAssociated(pickUp.getId());
                pickUp.setIdAssociated(delivery.getId());
                listePoint.put(pickUp.getId(),pickUp);
                listePoint.put(delivery.getId(),delivery);
                listePoint.put(delivery.getId(),delivery);

            }



        }
        Request request=new Request(depot,departureTime,listePoint);



        return request;
    }

    /**
     * Build the Map data which contains all intersection
     * @param fileName
     * @return MapData
     */
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
            System.out.println("Error while opening file" + e);
            return null;
        }
    }

    /**
     * Methods which allow to build segment (link between intersection)
     * @param document
     * @param intersections
     */
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

    /**
     * get each Intersections on the XML File
     * @param document
     * @return
     */
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

    /**
     * Allow to calculte the Schedule for a point based on the previous Schedule, the cost to reach and the duration
     * @param heurePrec
     * @param costToReach
     * @param duration
     * @return Date
     * @throws ParseException
     */
    public static Date findSchedule(Date heurePrec, float costToReach, int duration) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

        int secondHeurePrec= heurePrec.getHours()*3600+heurePrec.getMinutes()*60+heurePrec.getSeconds();
        int secondTotal= (int)costToReach  + secondHeurePrec + duration;

        Date schedule = new SimpleDateFormat("HH:mm:ss").parse("00:00:"+ secondTotal);
        return schedule;

    }

}
