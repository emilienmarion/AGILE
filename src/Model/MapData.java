package Model;

import java.util.ArrayList;
import java.util.HashMap;

public class MapData {
    private HashMap<String,Intersection> intersections;
    private float minX;
    private float maxX;
    private float minY;
    private float maxY;
    public MapData() {
    }

    public MapData(HashMap<String,Intersection> intersections, float minX, float maxX, float minY, float maxY) {
        this.intersections = intersections;
        this.minX = minX;
        this.maxX = maxX;
        this.minY = minY;
        this.maxY = maxY;
    }

    public void setIntersections(HashMap<String,Intersection> intersections) {
        this.intersections = intersections;
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

    public HashMap<String,Intersection> getIntersections() {
        return intersections;
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
        return "MapData{" +
                "\nintersections=" + intersections +
                "\n, minX=" + minX +
                "\n, maxX=" + maxX +
                "\n, minY=" + minY +
                "\n, maxY=" + maxY +
                "\n}";
    }
}