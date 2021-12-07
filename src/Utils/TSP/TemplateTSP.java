package Utils.TSP;
import Utils.Array;
import Utils.GraphConverter;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public abstract class TemplateTSP implements TSP {
    private Integer[] bestSol;
    protected TSPGraph g;
    private float bestSolCost;
    private int timeLimit;
    private long startTime;
    private int nbConfig;

    public int getNbConfig() {
        return nbConfig;
    }

    public void searchSolution(int timeLimit, TSPGraph g){
        if (timeLimit <= 0) return;
        startTime = System.currentTimeMillis();
        this.timeLimit = timeLimit;
        this.g = g;
        bestSol = new Integer[g.getNbVertices()];
        Collection<Integer> unvisited = new ArrayList<Integer>(g.getNbVertices()-1);
        for (int i=1; i<g.getNbVertices(); i++) unvisited.add(i);
        Collection<Integer> visited = new ArrayList<Integer>(g.getNbVertices());
        visited.add(0); // The first visited vertex is 0
        bestSolCost = Float.MAX_VALUE;
        boolean[][] isUnlocked= Array.getCopy(g.getIsUnlocked(),g.getNbVertices());
        nbConfig=0;
        branchAndBound(0, unvisited, visited,isUnlocked, 0);
    }

    public Integer getSolution(int i){
        if (g != null && i>=0 && i<g.getNbVertices())
            return bestSol[i];
        return -1;
    }

    public float getSolutionCost(){
        if (g != null)
            return bestSolCost;
        return -1;
    }

    /**
     * Method that must be defined in TemplateTSP subclasses
     * @param currentVertex
     * @param unvisited
     * @return a lower bound of the cost of paths in <code>g</code> starting from <code>currentVertex</code>, visiting
     * every vertex in <code>unvisited</code> exactly once, and returning back to vertex <code>0</code>.
     */
    protected abstract float bound(Integer currentVertex, Collection<Integer> unvisited,boolean[][] isUnlocked);

    /**
     * Method that must be defined in TemplateTSP subclasses
     * @param currentVertex
     * @param unvisited
     * @param g
     * @return an iterator for visiting all vertices in <code>unvisited</code> which are successors of <code>currentVertex</code>
     */
    protected abstract Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, TSPGraph g,boolean[][] isUnlocked);

    /**
     * Template method of a branch and bound algorithm for solving the TSP in <code>g</code>.
     * @param currentVertex the last visited vertex
     * @param unvisited the set of vertex that have not yet been visited
     * @param visited the sequence of vertices that have been already visited (including currentVertex)
     * @param currentCost the cost of the path corresponding to <code>visited</code>
     */
    private void branchAndBound(int currentVertex, Collection<Integer> unvisited,
                                Collection<Integer> visited,boolean[][] isUnlocked, float currentCost){
        Array.display(isUnlocked,g.getNbVertices());
        nbConfig++;
        if (System.currentTimeMillis() - startTime > timeLimit) return;
        if (unvisited.size() == 0){
            if (GraphConverter.isArc(currentVertex,0,isUnlocked)){
                if (currentCost+g.getCost(currentVertex,0) < bestSolCost){
                    visited.toArray(bestSol);
                    bestSolCost = currentCost+g.getCost(currentVertex,0);
                    System.out.print("Nouvelle solution en : ");
                    System.out.println(bestSolCost);
                }
            }
        } else if (currentCost+bound(currentVertex,unvisited,isUnlocked) < bestSolCost){
            Iterator<Integer> it = iterator(currentVertex, unvisited, g,isUnlocked);
            int compteur=0;
            while (it.hasNext()){
                Integer nextVertex = it.next();
                System.out.print("currentVertex=");
                System.out.println(currentVertex);
                System.out.print("nextVertex=");
                System.out.println(nextVertex);
                if (isUnlocked[currentVertex][nextVertex]) {
                    visited.add(nextVertex);
                    unvisited.remove(nextVertex);
                    boolean[][] newIsUnlocked= Array.getCopy(isUnlocked,g.getNbVertices());
                    Array.allowDelivery(newIsUnlocked,currentVertex,nextVertex,g.getNbVertices());
                    branchAndBound(nextVertex, unvisited, visited,newIsUnlocked,
                            currentCost + g.getCost(currentVertex, nextVertex));
                    visited.remove(nextVertex);
                    unvisited.add(nextVertex);
                }
                compteur++;
            }
        }
    }

}
