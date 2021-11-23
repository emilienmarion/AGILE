package Model;

public class Point extends Intersection{
    private int duration;
    private String type;
    public Point() {
    }

    @Override
    public String toString() {
        return "Point{" +
                "duration=" + duration +
                ", type='" + type + '\'' +
                '}';
    }

    public Point(Intersection i, int duration, String type) {
        super(i);
        this.duration = duration;
        this.type = type;
    }
}

