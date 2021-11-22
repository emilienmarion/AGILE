package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class MapData {
    private HashMap<String, HashMap<String, Float>> intersection;
    private ArrayList<HashMap<String, String>> segment;
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;
    public MapData() {
    }

    public MapData(HashMap<String, HashMap<String, Float>> intersection, ArrayList<HashMap<String, String>> segment, float minX, float maxX, float minY, float maxY) {
        this.intersection = intersection;
        this.segment = segment;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public void setIntersection(HashMap<String, HashMap<String, Float>> intersection) {
        this.intersection = intersection;
    }

    public void setSegment(ArrayList<HashMap<String, String>> segment) {
        this.segment = segment;
    }

    public void setMinX(float minX) {
        this.minX = minX;
    }

    public void setMaxX(float maxX) {
        this.maxX = maxX;
    }

    public void setMinY(float minY) {
        this.minY = minY;
    }

    public void setMaxY(float maxY) {
        this.maxY = maxY;
    }

    public HashMap<String, HashMap<String, Float>> getIntersection() {
        return intersection;
    }

    public ArrayList<HashMap<String, String>> getSegment() {
        return segment;
    }

    public float getMinX() {
        return minX;
    }

    public float getMaxX() {
        return maxX;
    }

    public float getMinY() {
        return minY;
    }

    public float getMaxY() {
        return maxY;
    }

    @Override
    public String toString() {
        return "MapData{\n" +
                "intersection=" + intersection +
                ",\nsegment=" + segment +
                ",\nminX=" + minX +
                ",\nmaxX=" + maxX +
                ",\nminY=" + minY +
                ",\nmaxY=" + maxY +
                '}';
    }
}
