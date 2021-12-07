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
    public static void allowDelivery(boolean[][] isUnlocked,int index,int length){
        for (int i=0;i<length;i++){
            if (i != index) {
                isUnlocked[i][index] = true;
            }
            isUnlocked[i][index - 1] = false;
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
