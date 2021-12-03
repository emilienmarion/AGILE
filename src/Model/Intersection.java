package Model;

import java.util.HashMap;

public class Intersection {
    protected String id;
    protected float latitude;
    protected float longitude;
    protected HashMap<String,Float> neighbors; //Id des voisins avec un cout associé
    protected float latitudeSurPanel;
    protected float longitudeSurPanel;

    public Intersection() {
        this.neighbors=new HashMap<String,Float>();
    }

    public Intersection(String id, float latitude, float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.neighbors=new HashMap<String,Float>();
    }

    public Intersection(Intersection i) {
        this.id=i.getId();
        this.latitude=i.getLatitude();
        this.longitude=i.getLongitude();
        this.neighbors=new HashMap<String,Float>();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }
    public void addNeighbor(String id,float length){
        neighbors.put(id,length);
    }

    public HashMap<String, Float> getNeighbors() {
        return neighbors;
    }
    public boolean equals(Intersection i){
        return this.getId().equals(i.getId());
    }
    @Override
    public String toString() {
        return "Intersection{" +
                "\nid='" + id + '\'' +
                "\n, latitude=" + latitude +
                "\n, longitude=" + longitude +
                "\n, neighbors=" + neighbors +
                "\n}";
    }

    public float getLatitudeSurPanel() {
        return latitudeSurPanel;
    }

    public void setLatitudeSurPanel(float latitudeSurPanel) {
        this.latitudeSurPanel = latitudeSurPanel;
    }

    public float getLongitudeSurPanel() {
        return longitudeSurPanel;
    }

    public void setLongitudeSurPanel(float longitudeSurPanel) {
        this.longitudeSurPanel = longitudeSurPanel;
    }
}
