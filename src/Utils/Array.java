package Utils;

import Model.Graph;
import Utils.TSP.CompleteGraph;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class Array {
    public static boolean[][] getCopy(boolean[][] origin,int length){
        boolean[][] copy=new boolean[length][length];
        for (int i=0;i<length;i++){
            boolean[] current=new boolean[length];
            for (int k=0;k<length;k++){
                current[k]=origin[i][k];
            }
            copy[i]=current;
        }
        return copy;
    }
    public static void allowDelivery(boolean[][] isUnlocked,int index,int indexDestination,int length){
        for (int i=0;i<length;i++){
            if (i != indexDestination+1) {
                if (indexDestination%2==1 && isUnlocked[i][0] && i!=indexDestination ) {
                    isUnlocked[i][indexDestination+1] = true;
                }
            }
            isUnlocked[i][indexDestination] = false; //on met a false tt ce qui va vers le prochain noeud
            isUnlocked[index][i]=false; //on met a false ce qui part du noeud
        }
        if (indexDestination%2==1) {
            isUnlocked[indexDestination][indexDestination + 1] = false;
        }
    }
    public static void display(boolean[][] isUnlocked,int length){
        System.out.println("display");
        for (int i=0;i<length;i++){
            for (int k=0;k<length;k++){
                System.out.print(isUnlocked[i][k]);
                System.out.print(" ");
            }
            System.out.println();
        }
    }
}
