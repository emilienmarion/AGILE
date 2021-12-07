package View;

import Model.MapData;

import javax.swing.*;
import java.awt.*;

public class MapView {
    protected JFrame frame;
    protected JLabel mapPath;
    protected JPanel leftPanel;
    private Map map;
    private final int mapSquare;
    private MapData mdT;
    protected MapView mapView;
    protected String mapPathString;

    public MapView(JPanel leftPanel, int mapSquare, JLabel mapPath, MapData mdT, String mp){
        this.leftPanel = leftPanel;
        this.mapSquare = mapSquare;
        this.mapPath = mapPath;
        this.mdT = mdT;
        this.mapPathString = mp;
        //System.out.println("map data : "+ mdT);
        loadMap(this.mdT, this.mapPathString);
    }

    /**
     *
     * @param mdT map's data
     * @param mps map's path
     */
    public void loadMap(MapData mdT, String mps)
    {
        leftPanel.removeAll();

        float diffX=mdT.getMaxX()-mdT.getMinX();
        float diffY=mdT.getMaxY()-mdT.getMinY();
        float scale=Math.min(mapSquare/diffX,mapSquare/diffY);

        System.out.println("Frame.initMapSide");
        mapPath = new JLabel(mps);
        mapPath.setForeground(Color.WHITE);
        mapPath.setAlignmentX(Component.CENTER_ALIGNMENT);

        leftPanel.setBackground(new Color(40,40,40));
        leftPanel.setPreferredSize(new Dimension(500, 500));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        this.map = new Map(50,50,diffX,diffY,scale,mdT);
        this.map.setPreferredSize(new Dimension(this.mapSquare, this.mapSquare));
        this.map.setMaximumSize(new Dimension(this.mapSquare, this.mapSquare));
        this.map.setAlignmentX(Component.CENTER_ALIGNMENT);

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
