package Model;

import Controller.Controller;

import java.util.HashMap;

public class MapData {

    private HashMap<String,Intersection> intersections;
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;
    private Controller controller;
    public MapData() {
    }

    /**
     * Constructor of the class MapData which allowed to stock data of a map ( all intersection) and parameter to display it
     * @param intersections
     * @param minX
     * @param maxX
     * @param minY
     * @param maxY
     */
    public MapData(HashMap<String,Intersection> intersections, float minX, float maxX, float minY, float maxY) {
        this.intersections = intersections;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    /**
     *
     * @return
     */
    public Controller getController() {
        return controller;
    }

    /**
     * Set the Controller which corresponds to the class
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }



    /**
     * get all the intersection on the map
     * @return
     */
    public HashMap<String,Intersection> getIntersections() {
        return intersections;
    }

    /**
     *
     * @return
     */
    public float getMinX() {
        return minX;
    }

    /**
     *
     * @return
     */
    public float getMaxX() {
        return maxX;
    }

    /**
     *
     * @return
     */
    public float getMinY() {
        return minY;
    }

    /**
     *
     * @return
     */
    public float getMaxY() {
        return maxY;
    }

    /**
     *
     * @return description of the object
     */
    @Override
    public String toString() {
        return "MapData{" +
                "\nintersections=" + intersections +
                "\n, minX=" + minX +
                "\n, maxX=" + maxX +
                "\n, minY=" + minY +
                "\n, maxY=" + maxY +
                "\n}";
    }


    /**
     * This method allow us find  an intersection from  coordinate on the panel
     * @param x
     * @param y
     * @return Intersection
     */
    public Intersection findIntersection(int x,int y){
        for(String s: intersections.keySet()){
 float latitude=intersections.get(s).getLatitudeOnPanel();
 float longitude =intersections.get(s).getLongitudeOnPanel();
            if (x<latitude+20 && x>latitude-20 &&  y<longitude+20 && y>longitude-20){
               // controller.drawpoint((int)latitude,(int)longitude);
                // System.out.println("point req"+intersections.get(s).getId());
                return intersections.get(s);
            }
        }
        return null;
    }
}
