package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Request {
    private Point depot;
    private String departureTime;
    private HashMap<String,Point> listePoint;

    public Request() {
    }

    /**
     * Constructor of the class Request
     * @param depot  intersection which is the started Point
     * @param departureTime  departureTime of the query
     * @param listePoint  list of the point in the request
     */
    public Request(Point depot, String departureTime, HashMap<String,Point> listePoint) {
        this.depot = depot;
        this.departureTime = departureTime;
        this.listePoint = listePoint;
    }

    /**
     * get the depot
     * @return depot
     */
    public Point getDepot() {
        return depot;
    }

    /**
     * set the depot
     * @param depot
     */
    public void setDepot(Point depot) {
        this.depot = depot;
    }

    /**
     * get the DepartureTime
     * @return DepartureTime
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     * set the DepartureTime
     * @param departureTime
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * get the list of the point of the request
     * @return listePoint
     */
    public HashMap<String,Point> getListePoint() {
        return listePoint;
    }

    /**
     *Set the list of the point of the request
     * @param listePoint
     */
    public void setListePoint(HashMap<String,Point> listePoint) {
        this.listePoint = listePoint;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Request{" +
                "depot=" + depot +
                ", departureTime='" + departureTime + '\'' +
                ", listePoint=" + listePoint +
                '}';
    }
}
