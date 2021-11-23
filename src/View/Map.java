package View;

import Model.Intersection;
import Model.MapData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Map extends JPanel {
    private float scale;
    private int sizeX;
    private int sizeY;
    private MapData mapData;
    public Map(int offsetX,int offsetY,float diffX,float diffY,float echelon,MapData md){
        super();
        mapData=md;
        sizeX=(int)Math.round(diffX*echelon);
        sizeY=(int)Math.round(diffY*echelon);
        scale=echelon;
        System.out.println(sizeX);
        System.out.println(sizeY);
        this.setBounds(offsetX,offsetY,sizeX,sizeY);
        this.setBackground(Color.white);
        this.setLayout(new GridBagLayout());
    }
    private int[] getCoords(float longitude,float latitude){
        int x=Math.round((longitude- mapData.getMinX())*scale);
        int y=Math.round((latitude- mapData.getMinY())*scale);
        return new int[]{x,y};
    }
    public void paintComponent(Graphics g){
        super.paintComponent(g);
        HashMap<String, Intersection> intersections=new HashMap<String,Intersection>(mapData.getIntersections());
        for (String currentId:intersections.keySet()){
            Intersection currentIntersection=intersections.get(currentId);
            Set<String> neighbors=currentIntersection.getNeighbors().keySet();
            for (String neighborId:neighbors){
                float originLatitude=currentIntersection.getLatitude();
                float originLongitude= currentIntersection.getLongitude();
                float destinationLatitude=intersections.get(neighborId).getLatitude();
                float destinationLongitude=intersections.get(neighborId).getLongitude();
                int originC[]=getCoords(originLongitude,originLatitude);
                int destinationC[]=getCoords(destinationLongitude,destinationLatitude);
                g.drawLine(originC[0],originC[1],destinationC[0],destinationC[1]);
            }
        }
    }
}
