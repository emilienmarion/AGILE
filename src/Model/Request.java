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
     *
     * @param depot
     * @param departureTime
     * @param listePoint
     */
    public Request(Point depot, String departureTime, HashMap<String,Point> listePoint) {
        this.depot = depot;
        this.departureTime = departureTime;
        this.listePoint = listePoint;
    }

    /**
     *
     * @return
     */
    public Point getDepot() {
        return depot;
    }

    /**
     *
     * @param depot
     */
    public void setDepot(Point depot) {
        this.depot = depot;
    }

    /**
     *
     * @return
     */
    public String getDepartureTime() {
        return departureTime;
    }

    /**
     *
     * @param departureTime
     */
    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    /**
     *
     * @return
     */
    public HashMap<String,Point> getListePoint() {
        return listePoint;
    }

    /**
     *
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
