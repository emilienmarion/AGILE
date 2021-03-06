package View;


import Controller.Controller;
import Model.*;
import Model.Point;


import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Map extends JPanel {
    private final float scale;
    private final int sizeX;
    private final int sizeY;
    private MapData mapData;
    private Graph graph;
    private String curentid;
    private Tour tour;

    private String idPickup;
    private String idDelivery;
    boolean test;
    boolean drawpointBool;
    boolean drawpointBool2;

    protected Controller controller;


    /**
     * constructor of the Class Map
     * @param offsetX
     * @param offsetY
     * @param diffX
     * @param diffY
     * @param echelon
     * @param md
     */
    public Map(int offsetX, int offsetY, float diffX, float diffY, float echelon, MapData md) {
        super();

        mapData = md;
        sizeX = Math.round(diffX * echelon);
        sizeY = Math.round(diffY * echelon);
        scale = echelon;
        System.out.println(sizeX);
        System.out.println(sizeY);
        this.setBounds(offsetX, offsetY, sizeX, sizeY);
        this.setBackground(new Color(192, 192, 192));
        this.setLayout(new GridBagLayout());

        this.curentid = null;
        test = false;
        drawpointBool=false;
    }

    /**
     * getter of the tour
     * @return tour
     */
    public Tour getTour() {
        return tour;
    }

    /**
     * setter of the tour
     * @param tour
     */
    public void setTour(Tour tour) {
        this.tour = tour;
    }

    /**
     * setter of idPickup
     * @param idPickup
     */
    public void setIdPickup(String idPickup) {
        this.idPickup = idPickup;
    }

    /**
     * setter of idDelivery
     * @param idDelivery
     */
    public void setIdDelivery(String idDelivery) {
        this.idDelivery = idDelivery;
    }

    /**
     * setter of the boolean which manages if the point must be draw
     * @param drawpointBool2
     */
    public void setDrawpointBool2(boolean drawpointBool2) {
        this.drawpointBool2 = drawpointBool2;
    }

    /**
     * getter of the boolean which manages the point magnification
     * @return test
     */
    public boolean isTest() {
        return test;
    }

    /**
     * setter of the boolean which manages the point magnification
     * @param test
     */
    public void setTest(boolean test) {
        this.test = test;
    }

    /**
     * setter of the graph
     * @param graph
     */
    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    /**
     * getter of the current id
     * @return curentid
     */
    public String getCurentid() {
        return curentid;
    }

    /**
     * setter of the current id
     * @param curentid
     */
    public void setCurentid(String curentid) {
        this.curentid = curentid;
    }

    /**
     * getter of a point's coordinates
     * @param longitude
     * @param latitude
     * @return x, y the longitude and latitude
     */
    private int[] getCoords(float longitude, float latitude) {
        int x = Math.round((longitude - mapData.getMinX()) * scale);
        int y = Math.round((latitude - mapData.getMinY()) * scale);
        return new int[]{x, y};
    }


    /**
     * This method makes a point bigger when it is selected on the map
     * @param curentid
     * @param g
     * @param isdep
     */
    private void grossir(String curentid, Graphics g,boolean isdep) {

        int lat =0;
        int longi=0 ;

        for ( Point p: tour.getPointsDef() ){
            if(p.getId().equals(curentid)){
               lat = (int)p.getLatitudeOnPanel();
                longi=(int)p.getLongitudeOnPanel();
            }

        }

        g.setColor(Color.red);
        g.fillRect(lat, longi, 20, 20);
    }

    /**
     * Method which allowed to draw the points and the path between them on the map
     * @param graph
     * @param g
     */
    private void drawGraph(Graph graph,Graphics g){
        ArrayList<Path> way= getTour().getPathPointsDef();
        int length=graph.getListePoint().size();
        int index=0;
        ArrayList<Color> ac=new ArrayList<Color>();

        ac.add(Color.blue);
        ac.add(Color.pink);
        ac.add(Color.green);
        ac.add(Color.yellow);
        ac.add(Color.orange);
        ac.add(Color.cyan);
        ac.add(Color.magenta);
        ac.add(new Color(169, 109, 54));
        ac.add(new Color(6, 238, 173));
        ac.add(new Color(229, 47, 245));
        ac.add(new Color(246, 115, 135));
        ac.add(new Color(234, 239, 163));
        ac.add(new Color(241, 115, 115));
        ac.add(new Color(136, 159, 255));
        ac.add(new Color(76, 177, 255));
        ac.add(new Color(122, 3, 101));
        ac.add(new Color(255, 131, 77));
        g.setColor(ac.get(index));
        for (Path p : way) {
            Node n = p.getPath();
            Point start = getTour().getGraph().getListePoint().get(n.getIntersection().getId());

            int[] coordA = getCoords(start.getLongitude(), start.getLatitude());
            if (start.getType().equals("delivery")) g.fillRect(coordA[0], coordA[1], 10, 10);
            else if(start.getType().equals("pickUp")){
                g.fillOval(coordA[0], coordA[1], 10, 10);
            }else{
                g.setColor( new Color(226, 12, 12));
                g.fillOval(coordA[0], coordA[1], 20, 20);
            }
            Node n2 = n.getPredecessor();
            while (n2 != null) {
                Intersection i1 = n.getIntersection();
                float latitude1 = i1.getLatitude();
                float longitude1 = i1.getLongitude();
                Intersection i2 = n2.getIntersection();
                float latitude2 = i2.getLatitude();
                float longitude2 = i2.getLongitude();
                int[] coord1 = getCoords(longitude1, latitude1);

                int[] coord2 = getCoords(longitude2, latitude2);
                g.drawLine(coord1[0], coord1[1], coord2[0], coord2[1]);
                n = n.getPredecessor();
                n2 = n2.getPredecessor();
            }
            Point end = graph.getListePoint().get(n.getIntersection().getId());
            int[] coordB = getCoords(end.getLongitude(), end.getLatitude());
            end.setLatitudeOnPanel(coordB[0]);
            end.setLongitudeOnPanel(coordB[1]);
            if (end.getType().equals("delivery")) g.fillRect(coordB[0], coordB[1], 10, 10);
            else if(end.getType().equals("pickUp")){
                g.fillOval(coordB[0], coordB[1], 10, 10);
            }else{
                g.setColor( new Color(226, 12, 12));
                g.fillOval(coordB[0], coordB[1], 20, 20);
            }
            g.setColor(ac.get(index));
            if (index != 17) index++;
        }

        g.setColor(Color.black);
    }


    /**
     * getter of mapData
     * @return mapData
     */
    public MapData getMapData() {
        return mapData;
    }

    /**
     * Method which allowed to draw the map
     * @param g
     */
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        HashMap<String, Intersection> intersections = new HashMap<String, Intersection>(mapData.getIntersections());
        for (String currentId : intersections.keySet()) {
            Intersection currentIntersection = intersections.get(currentId);
            Set<String> neighbors = currentIntersection.getNeighbors().keySet();
            for (String neighborId : neighbors) {
                float originLatitude = currentIntersection.getLatitude();
                float originLongitude = currentIntersection.getLongitude();
                float destinationLatitude = intersections.get(neighborId).getLatitude();
                float destinationLongitude = intersections.get(neighborId).getLongitude();
                int[] originC = getCoords(originLongitude, originLatitude);
                int[] destinationC = getCoords(destinationLongitude, destinationLatitude);
                currentIntersection.setLatitudeOnPanel(originC[0]);
                currentIntersection.setLongitudeOnPanel(originC[1]);
                intersections.get(neighborId).setLatitudeOnPanel(destinationC[0]);
                intersections.get(neighborId).setLongitudeOnPanel(destinationC[1]);

                g.setColor(Color.black);
                g.drawLine(originC[0], originC[1], destinationC[0], destinationC[1]);
            }
            if (graph != null) {

                drawGraph(graph, g);
            }
            if (test) {
                grossir(this.curentid, g,true);
            }
            if (drawpointBool) {
                drawpoint(this.idPickup,g);
            }
            if (drawpointBool2) {
                drawpoint2(this.idPickup,this.idDelivery,g);
            }

        }

    }

    /**
     * method which draw a pickup point
     * @param idPickup
     * @param g
     */
    public void drawpoint (String idPickup,Graphics g){

        System.out.println("idPi"+idPickup);
        int lat = (int) mapData.getIntersections().get(idPickup).getLatitudeOnPanel();
        int longi = (int) mapData.getIntersections().get(idPickup).getLongitudeOnPanel();

        g.setColor(Color.blue);
        g.fillRect(lat, longi, 10, 10);
    }

    /**
     * method which draw pickup point and its delivery point
     * @param idPickup
     * @param idDelivery
     * @param g
     */
    public void drawpoint2 (String idPickup,String idDelivery,Graphics g){

        int lat1 = (int) mapData.getIntersections().get(idPickup).getLatitudeOnPanel();
        int longi1 = (int) mapData.getIntersections().get(idPickup).getLongitudeOnPanel();

        int lat2 = (int) mapData.getIntersections().get(idDelivery).getLatitudeOnPanel();
        int longi2 = (int) mapData.getIntersections().get(idDelivery).getLongitudeOnPanel();
        g.setColor(Color.orange);

        g.fillRect(lat1, longi1, 10, 10);
        g.fillRect(lat2, longi2, 10, 10);
    }

    /**
     * setter of the boolean which manages if the point must be draw
     * @param drawpointBool
     */
    public void setDrawpointBool(boolean drawpointBool) {
        this.drawpointBool = drawpointBool;
    }
}
