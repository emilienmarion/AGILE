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

    public Point(Intersection i, int duration, String type, String idAssociated) {
        super(i);
        this.duration = duration;
        this.type = type;
        this.idAssociated = idAssociated;
    }

    public Point(Intersection i, int duration, String type) {
        super(i);
        this.duration = duration;
        this.type = type;
    }

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
        this.latitudeSurPanel=p.longitudeSurPanel;
        this.longitudeSurPanel=p.longitudeSurPanel;


    }
    public int getDuration() {
        return duration;
    }

    public String getType() {
        return type;
    }

    public String getIdAssociated() {
        return idAssociated;
    }

    public void setDuration(int duration) {
        this.duration = duration;
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setCostToReach(float costToReach){ this.costToReach = costToReach ;}

    public float getCostToReach(){ return costToReach; }

    public Date getSchedule() {
        return schedule;
    }

    public void setSchedule(Date schedule) {
        this.schedule = schedule;
    }

    public void setIdAssociated(String idAssociated) {
        this.idAssociated = idAssociated;
    }
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
                ",\ncostToReach=" + costToReach + '\'' +
                ",\nschedule= " + schedule +
                '}';
    }
}

