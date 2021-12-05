package View;

import javax.swing.*;
import java.awt.*;

import Controller.*;
import Model.MapData;
import Model.Request;

public class Frame {

    protected JFrame frame;
    protected MenuBar menuBar;
    protected JButton loadMapButton;
    protected JButton loadTourButton;
    protected JLabel mapPath;
    protected JPanel leftPanel;
    protected JPanel rightPanel;
    protected JPanel mainPanel;
    protected JPanel splitPanel;
    protected JPanel headerInfo;
    private final int mapSquare=450;
    protected MapView mapView;
    protected TourView tourView;
    protected Controller controller;
    protected ButtonListener buttonListener;
    protected String TourPath;


    public Frame(MapData md, String mapath) {
        controller = new Controller(this);
        controller.setMd(md);
        buttonListener = new ButtonListener(controller, this);
        initFrame();
        mapView = new MapView(leftPanel, mapSquare, mapPath, md, mapath); // Call the constructor and init this side
        initLoaderSide();
    }


    private void initFrame()
    {
        // Window design
        frame = new JFrame("UberIf");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        menuBar = new MenuBar(buttonListener);
        //add menubar to the frame
        frame.setJMenuBar(menuBar);

        // Panel for subPanel organization
        mainPanel = new JPanel();
        mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));

        headerInfo = new JPanel();
        headerInfo.setPreferredSize(new Dimension(0, 0));

        splitPanel = new JPanel();
        splitPanel.setLayout(new GridLayout(1, 2));

        leftPanel = new JPanel();

        rightPanel = new JPanel();

        splitPanel.add(leftPanel);
        splitPanel.add(rightPanel);
        mainPanel.add(headerInfo);
        mainPanel.add(splitPanel);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
    }


    private void initLoaderSide() {
        loadMapButton = new JButton("Load map");
        loadMapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadMapButton.addActionListener(buttonListener);
        loadMapButton.setBackground(new Color(61, 61, 61));
        //loadMapButton.setBorder(BorderFactory.createEmptyBorder(70, 40, 70, 40));
        loadMapButton.setForeground(Color.WHITE);
        //loadMapButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        loadTourButton = new JButton("Load Tour");
        loadTourButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadTourButton.addActionListener(buttonListener);
        loadTourButton.setBackground(new Color(61, 61, 61));
        loadTourButton.setForeground(Color.WHITE);


        rightPanel.setPreferredSize(new Dimension(400, 400));
        rightPanel.setBackground(new Color(40,40,40));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(loadMapButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0,20)));
        rightPanel.add(loadTourButton);
        rightPanel.add(Box.createVerticalGlue());
    }


    public void display()
    {
        System.out.println("Frame.display");
        frame.pack();
        frame.setVisible(true);
    }

    public void PlacerPoint()
    {
        //map.drawPickUp();

    }

    public MapView getMapView() {
        return mapView;
    }

    public void setMapView(MapView mapView) {
        this.mapView = mapView;
    }

    public void switchToTourView(Request req, String tp)
    {
        System.out.println("Frame.switchToTourView");
        this.TourPath = tp;
        // Reset right panel
        rightPanel.removeAll();
        // Setup with the new design
        tourView = new TourView(rightPanel, headerInfo, buttonListener, this.mapView, req, this.controller, this.TourPath);
        tourView.loadRequest(req, this.TourPath);
    }

    public void loadTour(Request req){tourView.loadRequest(req, this.TourPath);}

    public void loadMap(MapData loadedMap, String mapath) {
        mapView.loadMap(loadedMap, mapath);
    }

    public void editPoint(String id) {
        tourView.editPoint(id);}

    public void highlight(String id) {
        tourView.highlight(id);}

    public void confirmEdit(String i) {tourView.confirmEdit(i);}
}


