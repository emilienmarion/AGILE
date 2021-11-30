package Model;

public class Vertice {
    private String idOrigin;
    private String idDestination;
    private float length;
    private Path associatedPath;
    public Vertice() {
    }

    public Vertice(String idOrigin, String idDestination, float length, Path associatedPath) {
        this.idOrigin = idOrigin;
        this.idDestination = idDestination;
        this.length = length;
        this.associatedPath = associatedPath;
    }

    public String getIdOrigin() {
        return idOrigin;
    }

    public void setIdOrigin(String idOrigin) {
        this.idOrigin = idOrigin;
    }

    public String getIdDestination() {
        return idDestination;
    }

    public void setIdDestination(String idDestination) {
        this.idDestination = idDestination;
    }

    public float getLength() {
        return length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    public Path getAssociatedPath() {
        return associatedPath;
    }

    public void setAssociatedPath(Path associatedPath) {
        this.associatedPath = associatedPath;
    }

    @Override
    public String toString() {
        return "Vertice{" +
                "idOrigin='" + idOrigin + '\'' +
                ", idDestination='" + idDestination + '\'' +
                ", length=" + length +
                '}';
    }
}
