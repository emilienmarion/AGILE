package Model;

import java.util.Date;

public class Point extends Intersection{
    private int duration;
    private String type;
    private String idAssociated;
    private float costToReach;
    private Date schedule;
    public Point() {

    }

    /**
     * Constructor of Class Point (extend from Intersection)
     * @param i
     * @param duration
     * @param type
     * @param idAssociated
     */
    public Point(Intersection i, int duration, String type, String idAssociated) {
        super(i);
        this.duration = duration;
        this.type = type;
        this.idAssociated = idAssociated;
    }

    /**
     *Constructor of Class Point (extend from Intersection) without the IdAssociated( PickUp or delivery associated)
     * @param i
     * @param duration
     * @param type
     */
    public Point(Intersection i, int duration, String type) {
        super(i);
        this.duration = duration;
        this.type = type;
    }

    /**
     * Copy Constructor of Class Point
     * @param p
     */
    public Point(Point p){
        this.id=p.id;
        this.type=p.type;
        this.idAssociated=p.idAssociated;
        this.costToReach=p.costToReach;
        this.duration=p.duration;
        this.schedule=p.schedule;
        this.neighbors=p.neighbors;
        this.latitude=p.latitude;
        this.longitude=p.longitude;
        this.latitudeOnPanel =p.longitudeOnPanel;
        this.longitudeOnPanel =p.longitudeOnPanel;
    }

    /**
     * get the duration of the Point
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     *  Set the Type of the point (PickUp/Delivery/depot)
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     * get the Id of the Associated point
     * @return
     */
    public String getIdAssociated() {
        return idAssociated;
    }

    /**
     * Set the duration on this Point
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     * Set the Type of the point (PickUp/Delivery/depot)
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *get the coast to rach this point in the Tour
     * @param costToReach
     */
    public void setCostToReach(float costToReach){ this.costToReach = costToReach ;}

    /**
     * get the coast to rach this point in the Tour
     * @return costToReach
     */
    public float getCostToReach(){ return costToReach; }

    /**
     *
     * @return Schedule
     */
    public Date getSchedule() {
        return schedule;
    }

    /**
     * set the estimated Schedule of the point
     * @param schedule
     */
    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    /**
     * get the Id of the Associated Point
     * @param idAssociated
     */
    public void setIdAssociated(String idAssociated) {
        this.idAssociated = idAssociated;
    }

    /**
     *
     * @return
     */
    @Override
    public String toString() {
        return "Point{" +
                "\nid='" + id + '\'' +
                ",\nlatitude=" + latitude +
                ",\nlongitude=" + longitude +
                ",\nneighbors=" + neighbors +
                ",\nduration=" + duration +
                ",\ntype='" + type + '\'' +
                ",\nidAssociated='" + idAssociated + '\'' +
                ",\ncostToReach=" + costToReach +
                ",\nschedule= " + schedule +
                '}';
    }
}

