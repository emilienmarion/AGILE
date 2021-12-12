package Model;

public class Vertice {
    private String idOrigin;
    private String idDestination;
    private float length;
    private Path associatedPath;
    public Vertice() {
    }

    /**
     *
     * @param idOrigin
     * @param idDestination
     * @param length
     * @param associatedPath
     */
    public Vertice(String idOrigin, String idDestination, float length, Path associatedPath) {
        this.idOrigin = idOrigin;
        this.idDestination = idDestination;
        this.length = length;
        this.associatedPath = associatedPath;
    }

    /**
     *
     * @return
     */
    public String getIdOrigin() {
        return idOrigin;
    }

    /**
     *
     * @param idOrigin
     */
    public void setIdOrigin(String idOrigin) {
        this.idOrigin = idOrigin;
    }

    /**
     *
     * @return
     */
    public String getIdDestination() {
        return idDestination;
    }

    /**
     *
     * @param idDestination
     */
    public void setIdDestination(String idDestination) {
        this.idDestination = idDestination;
    }

    /**
     *
     * @return
     */
    public float getLength() {
        return length;
    }

    /**
     *
     * @param length
     */
    public void setLength(float length) {
        this.length = length;
    }

    /**
     *
     * @return
     */
    public Path getAssociatedPath() {
        return associatedPath;
    }

    /**
     *
     * @param associatedPath
     */
    public void setAssociatedPath(Path associatedPath) {
        this.associatedPath = associatedPath;
    }

    /**
     * 
     * @return
     */
    @Override
    public String toString() {
        return "Vertice{" +
                "idOrigin='" + idOrigin + '\'' +
                ", idDestination='" + idDestination + '\'' +
                ", length=" + length +
                '}';
    }
}
