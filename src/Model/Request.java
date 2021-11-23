package Model;

import java.util.ArrayList;

public class Request {
    private Point depot;
    private String departureTime;
    private ArrayList<Point[]> listePoint;

    public Request() {
    }

    public Request(Point depot, String departureTime, ArrayList<Point[]> listePoint) {
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

    public ArrayList<Point[]> getListePoint() {
        return listePoint;
    }

    public void setListePoint(ArrayList<Point[]> listePoint) {
        this.listePoint = listePoint;
    }

    @Override
    public String toString() {
        String toString= "Request{" +
                "depot=" + depot +
                ", departureTime='" + departureTime
                ;
        toString=toString+"  \n Liste des points de la requetes: \n  ";

        for(Point[] tabP: this.listePoint){
            toString=toString+" PickUp"+tabP[0];
            toString=toString+"  Delivery"+tabP[1]+"\n";


        }

        return toString;
    }
}
