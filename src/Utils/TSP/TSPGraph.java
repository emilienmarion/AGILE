package Utils.TSP;

public interface TSPGraph {
    /**
     * @return the number of vertices in <code>this</code>
     */
    int getNbVertices();

    /**
     * @param i
     * @param j
     * @return the cost of arc (i,j) if (i,j) is an arc; -1 otherwise
     */
    float getCost(int i, int j);

    boolean[][] getIsUnlocked();


}
