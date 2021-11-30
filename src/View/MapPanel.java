package View;

import Controller.Controller;
import Model.MapData;

import javax.swing.*;
import java.awt.*;

public class MapPanel {
    protected JFrame frame;
    protected JLabel mapPath;
    protected JPanel leftPanel;
    private Map map;
    private final int mapSquare;
    private MapData mdT;
    protected MapPanel mapPanel;

    public MapPanel(JPanel leftPanel, int mapSquare, JLabel mapPath, MapData mdT){
        this.leftPanel = leftPanel;
        this.mapSquare = mapSquare;
        this.mapPath = mapPath;
        this.mdT = mdT;
        System.out.println("map data : "+ mdT);
        loadMap(this.mdT);
    }

    public void loadMap(MapData mdT)
    {
        leftPanel.removeAll();

        float diffX=mdT.getMaxX()-mdT.getMinX();
        float diffY=mdT.getMaxY()-mdT.getMinY();
        float scale=Math.min(mapSquare/diffX,mapSquare/diffY);
        System.out.println("Frame.initMapSide");
        mapPath = new JLabel("src/petiteMap.xml");
        mapPath.setForeground(Color.WHITE);

        leftPanel.setBackground(new Color(40,40,40));
        leftPanel.setPreferredSize(new Dimension(500, 500));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        this.map = new Map(50,50,diffX,diffY,scale,mdT);
        System.out.println("Map initialized");

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(map);
        leftPanel.add(mapPath);
        leftPanel.add(Box.createVerticalGlue());

    }

    public Map getMap() {
        return this.map;
    }

    public MapData getMapData() {
        return this.mdT;
    }

}
