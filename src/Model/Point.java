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
     *
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
     *
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
     *
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
     *
     * @return
     */
    public int getDuration() {
        return duration;
    }

    /**
     *
     * @return
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return
     */
    public String getIdAssociated() {
        return idAssociated;
    }

    /**
     *
     * @param duration
     */
    public void setDuration(int duration) {
        this.duration = duration;
    }

    /**
     *
     * @param type
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     *
     * @param costToReach
     */
    public void setCostToReach(float costToReach){ this.costToReach = costToReach ;}

    /**
     *
     * @return
     */
    public float getCostToReach(){ return costToReach; }

    /**
     *
     * @return
     */
    public Date getSchedule() {
        return schedule;
    }

    /**
     *
     * @param schedule
     */
    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    /**
     *
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

