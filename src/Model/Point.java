package Model;

public class Point extends Intersection{
    private int duration;
    private String type;
    private String idAssociated;
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
                '}';
    }
}

