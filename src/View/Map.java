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
    private Graph graph;
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

    public Graph getGraph() {
        return graph;
    }

    public void setGraph(Graph graph) {
        this.graph = graph;
    }

    private int[] getCoords(float longitude, float latitude){
        int x=Math.round((longitude- mapData.getMinX())*scale);
        int y=Math.round((latitude- mapData.getMinY())*scale);
        return new int[]{x,y};
    }
    private void drawGraph(Graph graph,Graphics g){
        ArrayList<Path> way=graph.getSolution();
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
            Node n=p.getPath();
            Point start=graph.getListePoint().get(n.getIntersection().getId());
            int[] coordA=getCoords(start.getLongitude(),start.getLatitude());
            if (start.getType().equals("delivery")) g.fillRect(coordA[0],coordA[1],10,10);
            else g.fillOval(coordA[0],coordA[1],10,10);
            Node n2=n.getPredecessor();
            while (n2!=null){
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
            Point end=graph.getListePoint().get(n.getIntersection().getId());
            int[] coordB=getCoords(end.getLongitude(),end.getLatitude());
            if (end.getType().equals("delivery")) g.fillRect(coordB[0],coordB[1],10,10);
            else g.fillOval(coordB[0],coordB[1],10,10);
            g.setColor(ac.get(index));
            if (index!=6)index++;
        }
        g.setColor(Color.black);
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
            if (graph!=null){
                drawGraph(graph,g);
            }
        }
    }
}
