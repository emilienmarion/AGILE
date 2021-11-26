package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class Request {
    private Point depot;
    private String departureTime;
    private HashMap<String,Point> listePoint;

    public Request() {
    }

    public Request(Point depot, String departureTime, HashMap<String,Point> listePoint) {
        this.depot = depot;
        this.departureTime = departureTime;
        this.listePoint = listePoint;
    }

    public Point getDepot() {
        return depot;
    }

    public void setDepot(Point depot) {
        this.depot = depot;
    }

    public String getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(String departureTime) {
        this.departureTime = departureTime;
    }

    public HashMap<String,Point> getListePoint() {
        return listePoint;
    }

    public void setListePoint(HashMap<String,Point> listePoint) {
        this.listePoint = listePoint;
    }

    @Override
    public String toString() {
        return "Request{" +
                "depot=" + depot +
                ", departureTime='" + departureTime + '\'' +
                ", listePoint=" + listePoint +
                '}';
    }
}
