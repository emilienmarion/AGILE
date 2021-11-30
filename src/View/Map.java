package View;

import Model.Intersection;
import Model.MapData;
import Model.Point;
import Model.Request;

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
    private Request req;

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

    public MapData getMapData() {
        return mapData;
    }

    public void setMapData(MapData mapData) {
        this.mapData = mapData;
        notifyObservers();
    }

    public Request getReq() {
        return req;
    }

    public void setReq(Request req) {
        this.req = req;
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
                g.setColor(Color.black);
                g.drawLine(originC[0],originC[1],destinationC[0],destinationC[1]);

                if(req!=null){
                    HashMap<String,Point> listePoint=req.getListePoint();
                    float LatitudeDep =req.getDepot().getLatitude();
                    float LongitudeDep=req.getDepot().getLongitude();
                    int CoorDep[]=getCoords(LongitudeDep,LatitudeDep);
                    g.setColor(Color.green);
                    g.fillRect(CoorDep[0],CoorDep[1],10,10);


                    for(String s:listePoint.keySet()){
                        float Latitude=listePoint.get(s).getLatitude();
                        float Longitude= listePoint.get(s).getLongitude();
                        int Coor[]=getCoords(Longitude,Latitude);
                        if(listePoint.get(s).getType().equals("pickUp")){
                            g.setColor(Color.red);
                            g.fillRect(Coor[0],Coor[1],10,10);
                        }
                        if(listePoint.get(s).getType().equals("delivery")){
                            g.setColor(Color.blue);
                            g.fillOval(Coor[0],Coor[1],10,10);
                        }


                    }

                }


            }
        }





    }




}
