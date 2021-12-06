package Utils.TSP;

public class CompleteGraph implements TSPGraph{
    private static final int MAX_COST = 40;
    private static final int MIN_COST = 10;
    int nbVertices;
    float[][] cost;
    boolean[][] isUnlocked;
    /**
     * Create a complete directed graph such that each edge has a weight within [MIN_COST,MAX_COST]
     * @param nbVertices
     */
    public CompleteGraph(int nbVertices){
        this.nbVertices = nbVertices;
        int iseed = 1;
        cost = new float[nbVertices][nbVertices];
        for (int i=0; i<nbVertices; i++){
            for (int j=0; j<nbVertices; j++){
                if (i == j) cost[i][j] = -1;
                else {
                    int it = 16807 * (iseed % 127773) - 2836 * (iseed / 127773);
                    if (it > 0)	iseed = it;
                    else iseed = 2147483647 + it;
                    cost[i][j] = MIN_COST + iseed % (MAX_COST-MIN_COST+1);
                }
            }
        }
        for(float[] i:cost){
            for (float k:i){
                System.out.print(k);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
    public CompleteGraph(int nbVertices,float[][] cost,boolean[][] isUnlocked){
        System.out.println("Complete Graph");
        this.nbVertices = nbVertices;
        this.cost=cost;
        this.isUnlocked=isUnlocked;
        for(float[] i:cost){
            for (float k:i){
                System.out.print(k);
                System.out.print(" ");
            }
            System.out.println();
        }
        for(boolean[] i:isUnlocked){
            for (boolean k:i){
                System.out.print(k);
                System.out.print(" ");
            }
            System.out.println();
        }
    }

    @Override
    public int getNbVertices() {
        return nbVertices;
    }

    @Override
    public float getCost(int i, int j) {
        if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
            return -1;
        return cost[i][j];
    }

    @Override
    public boolean isArc(int i, int j) {
        if (i<0 || i>=nbVertices || j<0 || j>=nbVertices)
            return false;
        return i != j;
    }
}
