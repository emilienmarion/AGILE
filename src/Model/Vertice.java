package Model;

public class Vertice {
    private String idOrigin;
    private String idDestination;
    private float length;
    private Path associatedPath;
    public Vertice() {
    }

    /**
     * Constructor of the Class Vertice
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
     * get the Id of the origin point of the Vertice
     * @return  idOrigin
     */
    public String getIdOrigin() {
        return idOrigin;
    }

    /**
     *set the Id of the origin point of the Vertice
     * @param idOrigin
     */
    public void setIdOrigin(String idOrigin) {
        this.idOrigin = idOrigin;
    }

    /**
     * get the Id of the destination point of the Vertice
     * @return  idOrigin
     */
    public String getIdDestination() {
        return idDestination;
    }

    /**
     *set the Id of the destination point of the Vertice
     * @param idDestination
     */
    public void setIdDestination(String idDestination) {
        this.idDestination = idDestination;
    }

    /**
     * get the Length of the Vertice
     * @return length
     */
    public float getLength() {
        return length;
    }

    /**
     * set the Length of the Vertice
     * @param length
     */
    public void setLength(float length) {
        this.length = length;
    }

    /**
     * get the path associated to  the Vertice
     * @return
     */
    public Path getAssociatedPath() {
        return associatedPath;
    }

    /**
     * set  the path  of the Vertice
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
