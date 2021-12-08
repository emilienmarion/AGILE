package View;

import javax.swing.*;
import java.awt.*;
import java.text.ParseException;

import Controller.*;
import Model.MapData;
import Model.Request;
import Model.Tour;

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
    protected MapView mapView;
    protected TourView tourView;
    protected Controller controller;
    protected ButtonListener buttonListener;
    protected String TourPath;
    protected Tour tour;



    public Frame(MapData md, String mapath) {
        controller = new Controller(this);
        controller.setMd(md);
        buttonListener = new ButtonListener(controller, this);
        int mapSquare = 450;
        initFrame();
        mapView = new MapView(leftPanel, mapSquare, mapPath, md, mapath); // Call the constructor and init this side
        initLoaderSide();
        tour = new Tour(mapView);
        this.controller.setTourObject(this.tour);

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

    public void switchToTourView(Request req, String tp)throws ParseException
    {

        System.out.println("Frame.switchToTourView");
        this.TourPath = tp;
        // Reset right panel
        rightPanel.removeAll();
        // Setup with the new design
        tourView = new TourView(rightPanel, headerInfo, buttonListener, this.mapView, req, this.controller, this.TourPath, this.tour);
        tourView.loadRequest(req, this.TourPath);
    }

    public void loadTour(Request req) throws ParseException {tourView.loadRequest(req, this.TourPath);}

    public void loadMap(MapData loadedMap, String mapath) {
        mapView.loadMap(loadedMap, mapath);
    }

    public String editPoint(String id) {
        String schedule = tourView.editPoint(id);
        System.out.println("Frame.editPoint : " + schedule);
        return schedule;
    }

    public void addRequest(){
        tourView.addRequest();
    }

    public void confirmEdit(String i) {tourView.confirmEdit(i);}

    public void deletePoint( String id) {
        tourView.deletePoint(id);
    }

    public void confirmDeleteRow(String id) {
        tourView.confirmDelete(id);
    }

    public void highlight(String id) {
        tourView.highlight(id);
    }
}


