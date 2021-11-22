package View;

import Model.MapData;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.HashMap;

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
        HashMap<String,HashMap<String,Float>> intersections=new HashMap<String,HashMap<String,Float>>(mapData.getIntersection());
        ArrayList<HashMap<String,String>> segments=new ArrayList<HashMap<String,String>>(mapData.getSegment());
        for (HashMap<String,String> s:segments){
            String origin=s.get("origin");
            String destination=s.get("destination");
            int originC[]=getCoords(intersections.get(origin).get("longitude"),
            intersections.get(origin).get("latitude"));
            int destinationC[]=getCoords(intersections.get(destination).get("longitude"),
                    intersections.get(destination).get("latitude"));
            g.drawLine(originC[0],originC[1],destinationC[0],destinationC[1]);
        }
    }
}
