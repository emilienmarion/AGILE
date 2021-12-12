package View;

import Controller.Controller;
import Model.*;
import Model.Point;
import Utils.Algorithm;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class MapView {
    protected JFrame frame;
    protected JLabel mapPath;
    protected JPanel leftPanel;
    private Map map;
    private final int mapSquare;
    private MapData mdT;
    protected String mapPathString;
    private Tour tour;
    private final Controller controller;

    public MapView(JPanel leftPanel, int mapSquare, JLabel mapPath, MapData mdT, String mp, Controller controller){
        this.leftPanel = leftPanel;
        this.mapSquare = mapSquare;
        this.mapPath = mapPath;
        this.mdT = mdT;
        this.mapPathString = mp;
        this.controller = controller;
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
        this.mdT=mdT;
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

    public void loadRequest(Tour tour) {
        System.out.println("MapView.loadRequest");
        this.tour=tour;
        if(tour == null){
            System.out.println("TourView.loadRequest : tour is null");
        }
        else {

            map.addMouseListener(new PointLocater(map, controller));


            map.setTour(tour);
            map.setGraph(tour.getGraph());
            map.repaint();
        }
        System.out.println("MapView.loadRequest EXIT");
    }

    public void setTourObject(Tour tour) {
        this.tour = tour;
    }
}
