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


    /**
     *  constructor of the Class Frame
     * @param md
     * @param mapath
     */
    public Frame(MapData md, String mapath) {
        controller = new Controller(this);
        controller.setMd(md);
        buttonListener = new ButtonListener(controller, this);
        int mapSquare = 450;
        initFrame();
        mapView = new MapView(leftPanel, mapSquare, mapPath, md, mapath, controller); // Call the constructor and init this side
        initLoaderSide();
        tour = new Tour(mapView,controller);
        this.controller.setTourObject(this.tour);
        md.setController(this.controller);
        this.mapView.setTourObject(this.tour);
    }


    /**
     *  generator of the init content in the frame
     */
    private void initFrame() {
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

    /**
     *  generate button load map and load request in IHM
     */
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
        rightPanel.setBackground(new Color(40, 40, 40));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(loadMapButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0, 20)));
        rightPanel.add(loadTourButton);
        rightPanel.add(Box.createVerticalGlue());
    }


    /**
     * display the frame
     */
    public void display() {
        System.out.println("Frame.display");
        frame.pack();
        frame.setVisible(true);
    }

    /**
     * call the generator of tourView to display a view with the tour
     * @param req
     * @param tp
     * @throws ParseException
     */
    public void switchToTourView(Request req, String tp) throws ParseException {

        System.out.println("Frame.switchToTourView");
        this.TourPath = tp;
        // Reset right panel
        rightPanel.removeAll();
        // Setup with the new design
        tourView = new TourView(rightPanel, headerInfo, buttonListener, this.mapView, req, this.controller, this.TourPath, this.tour);
        loadTour(tour);
    }

    /**
     * call the method which display the tour on the map and the point in the list of point
     * @param tour
     * @throws ParseException
     */
    public void loadTour(Tour tour) throws ParseException {

        tourView.loadRequest(this.TourPath);
        mapView.loadRequest(tour);

    }

    /**
     * call a method which display a new map
     * @param loadedMap
     * @param mapath
     */
    public void loadMap(MapData loadedMap, String mapath) {

        mapView.loadMap(loadedMap, mapath);

    }

    /**
     * method call during edition of point and return the new hour of point
     * @param id
     * @return schedule
     */
    public String editPoint(String id) {
        String schedule = tourView.editPoint(id);
        System.out.println("Frame.editPoint : " + schedule);
        return schedule;
    }
    /**
     *  call a method which confirm the edit and apply the update
     * @param i
     */
    public void confirmEdit(String i) {
        tourView.confirmEdit(i);
    }

    /**
     * call method which setup the add point view and a click listener
     */
    public void addRequest() {
        tourView.addRequest();
    }

    /**
     * call method which setup the delete point view
     * @param id
     */
    public void deletePoint(String id) {
        tourView.deletePoint(id);
    }

    public void confirmDeleteRow(String id) {
        tourView.confirmDelete(id);
    }

    /**
     * call a method which highligh a row in point list
     * @param id
     */
    public void highlight(String id) {
        tourView.highlight(id);
    }


    /**
     * getter of the tourView
     * @return tourView
     */
    public TourView getTourView() {return this.tourView;
    }

    /**
     * getter of the mapView
     * @return mapView
     */
    public MapView getMapView() {return this.mapView;
    }


    /**
     * call method which draw pickup point
     * @param idPickup
     */
    public void drawpoint(String idPickup) {
        tourView.drawpoint(idPickup);
    }

    /**
     * call methods which get out the add point mode
     */
    public void sortirdeADD(){
        controller.setI(0);
      tourView.sortirdeADD();

    }

    /**
     * call method which draw pickup and delivery point
     * @param idPickup
     * @param idDelivery
     */
    public void drawpoint2(String idPickup, String idDelivery) {
        tourView.drawpoint2(idPickup,idDelivery);
    }


}
