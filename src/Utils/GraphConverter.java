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
        System.out.println(g.getContent());
        float[][] cost=new float[d][d];
        boolean[][] isUnlocked=new boolean[d][d];
        int i=0;
        for (ArrayList<Vertice> av:g.getContent()){
            int j=0;
            float[] line=new float[d];
            boolean[] lineUnlocked=new boolean[d];
            for (Vertice v:av){
                if (v==null){
                    lineUnlocked[j]=false;
                    line[j]=-1;
                }
                else{
                    line[j]=v.getLength();
                    if (g.getListePoint().get(v.getIdDestination()).getType().equals("delivery")){
                        //quand origin= destination-1 (i-->origin,j-->destination)
                        lineUnlocked[j]= i % 2 == 1 && i == j - 1;
                    }
                    else{
                        lineUnlocked[j]=true;
                    }
                }
                j++;
            }
            cost[i]=line;
            isUnlocked[i]=lineUnlocked;
            i++;
        }
        CompleteGraph cg=new CompleteGraph(d,cost,isUnlocked);
        return cg;
    }
    public static boolean isArc(int i,int j,boolean[][] isUnlocked){
        //return i!=j;
        return isUnlocked[i][j];
    }
}
