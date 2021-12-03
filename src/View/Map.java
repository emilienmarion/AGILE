package View;


import Model.*;
import Model.Point;


import javax.swing.*;
import java.awt.*;
import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Set;

public class Map extends JPanel {
    private float scale;
    private int sizeX;
    private int sizeY;
    private MapData mapData;

    private ArrayList<Path> way;

    private Request req;


    public Map(int offsetX,int offsetY,float diffX,float diffY,float echelon,MapData md){
        super();
        addMouseListener(new PointLocater(this));
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

    public ArrayList<Path> getWay() {
        return way;
    }

    public void setWay(ArrayList<Path> w) {
        this.way = w;
    }

    private int[] getCoords(float longitude, float latitude){
        int x=Math.round((longitude- mapData.getMinX())*scale);
        int y=Math.round((latitude- mapData.getMinY())*scale);
        return new int[]{x,y};
    }

    private void drawGraph(ArrayList<Path> way,Graphics g){
        //System.out.println("Map.drawGraph");
        //System.out.println(way);
        int index=0;
        ArrayList<Color> ac=new ArrayList<Color>();
        ac.add(Color.blue);
        ac.add(Color.red);
        ac.add(Color.pink);
        ac.add(Color.green);
        ac.add(Color.yellow);
        ac.add(Color.orange);
        ac.add(Color.cyan);
        g.setColor(ac.get(index));
        for (Path p:way){
            Node n=p.getPath().get(0);
            Intersection start=n.getIntersection();
            int[] coordA=getCoords(start.getLongitude(),start.getLatitude());
            g.fillRect(coordA[0],coordA[1],10,10);
            Node n2=n.getPredecessor();
            while (n2!=null){
                p.addToPath(n);
                Intersection i1=n.getIntersection();
                float latitude1=i1.getLatitude();
                float longitude1=i1.getLongitude();
                Intersection i2=n2.getIntersection();
                float latitude2=i2.getLatitude();
                float longitude2=i2.getLongitude();
                int[] coord1=getCoords(longitude1,latitude1);
                int[] coord2=getCoords(longitude2,latitude2);
                g.drawLine(coord1[0],coord1[1],coord2[0],coord2[1]);
                n=n.getPredecessor();
                n2=n2.getPredecessor();
            }
            Intersection end=n.getIntersection();
            int[] coordB=getCoords(end.getLongitude(),end.getLatitude());
            g.fillRect(coordB[0],coordB[1],10,10);
            g.setColor(ac.get(index));
            if (index!=6)index++;
        }
        //System.out.println(way);
        g.setColor(Color.black);
    }


    public MapData getMapData() {
        return mapData;
    }

    public void setMapData(MapData mapData) {
        this.mapData = mapData;
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
                    HashMap<String, Point> listePoint=req.getListePoint();

                    float LatitudeDep =req.getDepot().getLatitude();
                    float LongitudeDep=req.getDepot().getLongitude();
                    int CoorDep[]=getCoords(LongitudeDep,LatitudeDep);
                    req.getDepot().setLongitudeSurPanel(CoorDep[1]);
                    req.getDepot().setLatitudeSurPanel(CoorDep[0]);

                    g.setColor(Color.green);
                    g.fillRect(CoorDep[0],CoorDep[1],10,10);


                    for(String s:listePoint.keySet()){
                        float Latitude=listePoint.get(s).getLatitude();
                        float Longitude= listePoint.get(s).getLongitude();

                        int Coor[]=getCoords(Longitude,Latitude);
                        listePoint.get(s).setLatitudeSurPanel(Coor[0]);
                        listePoint.get(s).setLongitudeSurPanel(Coor[1]);

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
            if (way!=null){
                drawGraph(way,g);
            }
        }





    }




}
