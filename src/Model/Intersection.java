package Model;

import java.util.HashMap;

public class Intersection {
    protected String id;
    protected float latitude;
    protected float longitude;
    protected HashMap<String,Float> neighbors; //Id des voisins avec un cout associé
    protected float latitudeOnPanel;
    protected float longitudeOnPanel;

    public Intersection() {
        this.neighbors=new HashMap<String,Float>();
    }

    /**
     * Constructor of the class Intersection
     * @param id adress in the XML FIle
     * @param latitude  Latitude on the XML File
     * @param longitude Logitude on the XML File
     */
    public Intersection(String id, float latitude, float longitude) {
        this.id = id;
        this.latitude = latitude;
        this.longitude = longitude;
        this.neighbors=new HashMap<String,Float>();
    }

    /**
     * Copy Construtor of The object Intersection
     * @param i
     */
    public Intersection(Intersection i) {
        this.id=i.getId();
        this.latitude=i.getLatitude();
        this.longitude=i.getLongitude();
        this.neighbors=new HashMap<String,Float>();
    }

    /**
     * getter for the ID
     * @return id
     */
    public String getId() {
        return id;
    }

    /**
     * Setter for the ID
     * @param id
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * getter for the Latitude
     * @return Latitude
     */
    public float getLatitude() {
        return latitude;
    }

    /**
     * Setter for the  latitude
     * @param latitude
     */
    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    /**
     * get the longitude of the point
     * @return
     */
    public float getLongitude() {
        return longitude;
    }

    /**
     * set the longitude of the point
     * @param longitude
     */
    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    /**
     * add a Neighbor to the intersection
     * @param id
     * @param length
     */
    public void addNeighbor(String id,float length){
        neighbors.put(id,length);
    }

    /**
     * get Neighbors of the Intersection
     * @return
     */
    public HashMap<String, Float> getNeighbors() {
        return neighbors;
    }

    /**
     * check if an Intersection is equal to another one
     * @param i
     * @return
     */
    public boolean equals(Intersection i){
        return this.getId().equals(i.getId());
    }


    /**
     * Get the latitude on the JPANEL
     * @return latitudeOnPanel
     */
    public float getLatitudeOnPanel() {
        return latitudeOnPanel;
    }

    /**
     * Set the latitude on the JPANEL
     * @param latitudeOnPanel
     */
    public void setLatitudeOnPanel(float latitudeOnPanel) {
        this.latitudeOnPanel = latitudeOnPanel;
    }

    /**
     *get the longitude  on the JPANEL
     * @return longitudeOnPanel
     */
    public float getLongitudeOnPanel() {
        return longitudeOnPanel;
    }

    /**
     * Set the longitude  on the JPANEL
     * @param longitudeOnPanel
     */
    public void setLongitudeOnPanel(float longitudeOnPanel) {
        this.longitudeOnPanel = longitudeOnPanel;
    }


    /**
     *
     * @return description (String)
     */
    @Override
    public String toString() {
        return "Intersection{" +
                "\nid='" + id + '\'' +
                "\n, latitude=" + latitude +
                "\n, longitude=" + longitude +
                "\n, neighbors=" + neighbors +
                "\n}";
    }
}
