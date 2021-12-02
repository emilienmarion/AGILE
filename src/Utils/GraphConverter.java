package Utils;

import Model.Graph;
import Model.Vertice;
import Utils.TSP.CompleteGraph;

import java.util.ArrayList;

//class useful to convert our Graph which contains all the informations relative to the real case, to n any graph
public class GraphConverter {
    public static CompleteGraph toTSPGraph(Graph g){
        int d=g.getDimension();
        System.out.println(d);
        float[][] cost=new float[d][d];
        int i=0;
        for (ArrayList<Vertice> av:g.getContent()){
            int j=0;
            float[] line=new float[d];
            for (Vertice v:av){
                if (v==null) line[j]=-1;
                else line[j]=v.getLength();
                j++;
            }
            cost[i]=line;
            i++;
        }
        CompleteGraph cg=new CompleteGraph(d,cost);
        return cg;
    }
}
