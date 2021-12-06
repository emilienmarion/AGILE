package View;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

import Controller.*;
import Model.MapData;
import Model.Request;

public class Frame {

    protected JFrame frame;
    protected JButton loadMapButton;
    protected JButton loadTourButton;
    protected JLabel mapPath;
    protected JPanel leftPanel;
    protected JPanel rightPanel;
    protected JPanel mainPanel;
    protected JPanel splitPanel;
    protected JPanel headerInfo;
    private final int mapSquare=500;
    protected MapView mapView;
    protected TourView tourView;
    protected Controller controller;
    protected ButtonListener buttonListener;


    public Frame(MapData md) {
        controller = new Controller(this);
        controller.setMd(md);
        buttonListener = new ButtonListener(controller, this);
        initFrame();
        mapView = new MapView(leftPanel, mapSquare, mapPath, md); // Call the constructor and init this side
        initLoaderSide();
    }


    private void initFrame()
    {
        // Window design
        frame = new JFrame("UberIf");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create a menu bar
        final JMenuBar menuBar = new JMenuBar();
        //create menus
        JMenu fileMenu = new JMenu("File");
        //create menu items
        JMenuItem loadTourMenuItem = new JMenuItem("Load tour");
        loadTourMenuItem.setActionCommand("Load tour");
        loadTourMenuItem.addActionListener(buttonListener);
        JMenuItem loadMapMenuItem = new JMenuItem("Load map");
        loadMapMenuItem.setActionCommand("Load map");
        loadMapMenuItem.addActionListener(buttonListener);
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");


        //add menu items to menus
        fileMenu.add(loadTourMenuItem);
        fileMenu.add(loadMapMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        //add menu to menubar
        menuBar.add(fileMenu);

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

        loadTourButton = new JButton("Load Tour");
        loadTourButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadTourButton.addActionListener(buttonListener);


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

    public void switchToTourView(Request req) throws ParseException {
        System.out.println("Frame.switchToTourView");
        // Reset right panel
        rightPanel.removeAll();
        // Setup with the new design
        tourView = new TourView(rightPanel, headerInfo, buttonListener, this.mapView, req, this.controller);
        tourView.loadRequest(req);
    }

    public void loadMap(MapData loadedMap) {
        mapView.loadMap(loadedMap);
    }

    public void editPoint(String id) {
        tourView.editPoint(id);}

    public void confirmEdit(String i) {tourView.confirmEdit(i);}
}


