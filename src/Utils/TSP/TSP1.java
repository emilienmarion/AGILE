package Utils.TSP;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
public class TSP1 extends TemplateTSP{
    @Override
    protected float bound(Integer currentVertex, Collection<Integer> unvisited,boolean[][] isUnlocked) {
        ArrayList<Float> costs=new ArrayList<Float>();
        for (int i=0;i<g.getNbVertices();i++){
            float mini=-1;
            for (int k=0;k<g.getNbVertices();k++){
                float current=g.getCost(i,k);
                if (mini==-1 && isUnlocked[i][k]){
                    mini=current;
                }
                else if (isUnlocked[i][k] && mini>current){
                    mini=current;
                }
            }
            if (mini!=-1) costs.add(mini);
        }
        float sum=0;
        for (int i=0;i<unvisited.size();i++){
            sum+= costs.get(i);
        }
        return sum;
    }

    @Override
    protected Iterator<Integer> iterator(Integer currentVertex, Collection<Integer> unvisited, TSPGraph g,boolean[][] isUnlocked) {
        return new SeqIter(unvisited, currentVertex, g,isUnlocked);
    }

}
