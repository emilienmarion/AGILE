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
     *
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
     *
     * @param controller
     */
    public void setController(Controller controller) {
        this.controller = controller;
    }

    /**
     *
     * @param intersections
     */
    public void setIntersections(HashMap<String,Intersection> intersections) {
        this.intersections = intersections;
    }

    /**
     *
     * @param minX
     */
    public void setMinX(float minX) {
        this.minX = minX;
    }

    /**
     *
     * @param maxX
     */
    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    /**
     *
     * @param minY
     */
    public void setMinY(float minY) {
        this.minY = minY;
    }

    /**
     *
     * @param maxY
     */
    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    /**
     *
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
     * @return
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
     *
     * @param x
     * @param y
     * @return
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
