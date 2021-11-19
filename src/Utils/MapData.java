package Utils;

import java.util.ArrayList;
import java.util.HashMap;

public class MapData {
    private HashMap<String, HashMap<String,String>> intersection;
    private ArrayList<HashMap<String, String>> segment;

    public MapData() {
    }

    public MapData(HashMap<String, HashMap<String, String>> intersection, ArrayList<HashMap<String, String>> segment) {
        this.intersection = intersection;
        this.segment = segment;
    }

    public void setIntersection(HashMap<String, HashMap<String, String>> intersection) {
        this.intersection = intersection;
    }

    public void setSegment(ArrayList<HashMap<String, String>> segment) {
        this.segment = segment;
    }

    public HashMap<String, HashMap<String, String>> getIntersection() {
        return intersection;
    }

    public ArrayList<HashMap<String, String>> getSegment() {
        return segment;
    }

    @Override
    public String toString() {
        return "MapData{" +
                "intersection=" + intersection +
                ", segment=" + segment +
                '}';
    }
}
