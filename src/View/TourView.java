package View;

import Controller.Controller;
import Model.Point;
import Model.Request;
import Utils.Algorithm;

import javax.swing.*;
import javax.swing.plaf.basic.BasicButtonUI;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import java.awt.event.MouseAdapter;


import java.awt.event.MouseEvent;


import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import Model.*;



public class TourView  {

    protected JFrame frame;
    protected JPanel rightPanel;
    protected JPanel headerInfo;
    protected HeadInfo headDate;
    protected HeadInfo headExp;
    protected HeadInfo headDeparture;
    protected HeadInfo headETA;
    protected HeadInfo headDuration;
    protected JScrollPane scrollPane;
    protected MapView mapView;
    protected ButtonListener buttonListener;
    protected Request req;
    protected ArrayList<String> orderJpanelList;
    protected HashMap<String, JPanel> jpanelList;
    protected Controller controller;
    protected String filename;
    protected ImageIcon icon;
    protected Tour tour;
    protected JButton addButton;

    protected PointLocater pointLocater;

    protected HashMap<String, String> infoTour;
    protected String tourPath;


    /**
     * constructor of the Class TourView
     * @param rightPanel
     * @param headerInfo
     * @param buttonListener
     * @param mapView
     * @param req
     * @param controller
     * @param TourPath
     * @param tour
     * @throws ParseException
     */
    public TourView(JPanel rightPanel, JPanel headerInfo, ButtonListener buttonListener, MapView mapView, Request req, Controller controller, String TourPath, Tour tour) throws ParseException {

        this.rightPanel = rightPanel;
        this.buttonListener = buttonListener;
        this.headerInfo = headerInfo;
        this.mapView = mapView;
        this.req = req;
        this.jpanelList = new HashMap<>();
        this.orderJpanelList = new ArrayList<>();
        this.controller = controller;
        this.tour = tour;
        this.tourPath = TourPath;

        Map map = mapView.getMap();

        pointLocater = new PointLocater(map, controller);
        map.addMouseListener(pointLocater);


    }

    /**
     * Get information which draw pickupions from class Tour.java to display them on the Tour sides
     * @param TourPath tour path to display above the scrollpane
     * @throws ParseException
     */
    public void displayTourView(String TourPath) throws ParseException {
        System.out.println("TourView.displayTourView");

        rightPanel.removeAll();

        rightPanel.setBackground(new Color(40, 40, 40));

        JPanel pathPanel = new JPanel();
        JLabel pathLabel = new JLabel(TourPath);
        pathLabel.setForeground(Color.WHITE);
        pathPanel.add(pathLabel);
        pathPanel.setBackground(new Color(40, 40, 40));

        // Bouton d'ajout de demande
        ImageIcon iconAdd = new ImageIcon(new ImageIcon("./img/AddIcon.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        JLabel imageAdd = new JLabel(iconAdd);
        imageAdd.setBackground(new Color(86, 86, 86));
        imageAdd.setOpaque(true);

         addButton = new JButton();
        addButton.setUI(new BasicButtonUI());
        addButton.setBackground(new Color(86, 86, 86));
        addButton.setOpaque(true);
        addButton.add(imageAdd);
        addButton.setActionCommand("add request");
        addButton.addActionListener(buttonListener);

        // Afficher la liste
        JPanel componentToScroll = new JPanel();
        componentToScroll.setLayout(new BoxLayout(componentToScroll, BoxLayout.Y_AXIS));
        componentToScroll.setBackground(new Color(61, 61, 61));

        orderJpanelList.forEach((id) -> {
            componentToScroll.add(jpanelList.get(id));
        });

        scrollPane = new JScrollPane(componentToScroll);
        scrollPane.setBackground(new Color(61, 61, 61));
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setMaximumSize(new Dimension(400, 400));
        scrollPane.setOpaque(false);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(scrollPane);
        rightPanel.add(pathPanel);
        rightPanel.add(Box.createVerticalGlue());

   
        setHeaderTour(  addButton,this.tour.getDepartureTime(), this.tour.getArrivalTime(), this.tour.getTotalDuration());

        rightPanel.repaint();

        System.out.println("TourView.displayTour EXIT");
    }

    /**
     * method which update header's information
     */
    public void updateHeader(){
        setHeaderTour(addButton,this.tour.getDepartureTime(), this.tour.getArrivalTime(), this.tour.getTotalDuration());
        System.out.println(this.tour.getDepartureTime() +" "+ this.tour.getArrivalTime() +" "+ this.tour.getTotalDuration());
        System.out.println("TourView.updateHeader");
    }


    /**
     * method which create the row for the point
     * @param point
     */
    protected void createJPanelPoint(Point point) {
        System.out.println("CREATE -- " + point.getId()+" at "+point.getSchedule()+" >> "+ point.getIdAssociated());

        ImageIcon iconEdit = new ImageIcon(new ImageIcon("./img/iconEdit.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JLabel imageEdit = new JLabel(iconEdit);
        imageEdit.setBackground(new Color(86, 86, 86));
        imageEdit.setOpaque(true);

        ImageIcon iconDelete = new ImageIcon(new ImageIcon("./img/icons8-trash-240.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JLabel imageDelete = new JLabel(iconDelete);
        imageDelete.setBackground(new Color(198, 52, 52));
        imageDelete.setOpaque(true);

        JPanel row = new JPanel();
        row.setBackground(new Color(61, 61, 61));
        row.setName(String.valueOf(1)); //jsp ?? quoi ??a sert
        row.setLayout(new GridBagLayout());
        row.setPreferredSize(new Dimension(380, 60));
        row.setMaximumSize(new Dimension(380, 60));
        row.setMinimumSize(new Dimension(380, 60));
        GridBagConstraints gbc = new GridBagConstraints();

        if (this.req != null) {

            DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
            String scheduleString;
            scheduleString = dateFormat2.format(point.getSchedule());

            JLabel duration = new JLabel(scheduleString);
            duration.setForeground(Color.WHITE);


            //Gestion adresse
            JPanel adressPanel = new JPanel();
            adressPanel.setLayout(new BoxLayout(adressPanel, BoxLayout.X_AXIS));
            adressPanel.setBackground(new Color(86, 86, 86));
            adressPanel.setPreferredSize(new Dimension(150, 50));
            JLabel labelToChange = new JLabel(point.getId());
            adressPanel.add(labelToChange, BorderLayout.WEST);
            adressPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));


            //Gestion de l'heure

            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String heurePassage = dateFormat.format(point.getSchedule());
            JLabel schedule = new JLabel(heurePassage);
            schedule.setForeground(Color.WHITE);


            if (point.getType() == "depot") {
                icon = new ImageIcon(new ImageIcon("./img/iconDepot.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
            } else if (point.getType() == "pickUp") {
                icon = new ImageIcon(new ImageIcon("./img/iconPickUp.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
            } else {
                icon = new ImageIcon(new ImageIcon("./img/iconDelivery.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
            }

            JLabel image = new JLabel(icon);

            //Gestion bouton edit et delete

            JPanel buttonBlock = new JPanel();
            buttonBlock.setOpaque(false);
            buttonBlock.setBackground(new Color(61, 61, 61));
            buttonBlock.setLayout(new BoxLayout(buttonBlock, BoxLayout.Y_AXIS));

            //Gestion bouton edit
            JButton editButton = new JButton();
            editButton.setUI(new BasicButtonUI());
            editButton.setBackground(new Color(86, 86, 86));
            editButton.setOpaque(true);
            editButton.add(imageEdit);
            editButton.setActionCommand("editRow" + point.getId());
            editButton.addActionListener(buttonListener);

            //Gestion bouton delete
            JButton deleteButton = new JButton();
            deleteButton.setUI(new BasicButtonUI());
            deleteButton.setBackground(new Color(198, 52, 52));
            deleteButton.setOpaque(true);
            deleteButton.add(imageDelete);
            deleteButton.setActionCommand("deleteRow" + point.getId());
            deleteButton.addActionListener(buttonListener);

            if (point.getType() != "depot") {
                buttonBlock.add(editButton);
                buttonBlock.add(deleteButton);
            }

            gbc.gridx = gbc.gridy = 0;
            gbc.insets = new Insets(0, 10, 0, 10);
            gbc.anchor = GridBagConstraints.LINE_START;
            row.add(image, gbc);

            gbc.gridx = 1;
            gbc.gridy = 0;
            gbc.weightx = 1;
            row.add(adressPanel, gbc);

            gbc.gridx = 2;
            gbc.gridy = 0;
            gbc.gridwidth = GridBagConstraints.RELATIVE;
            gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
            row.add(schedule, gbc);

            gbc.gridx = 3;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 5, 0, 10);
            gbc.anchor = GridBagConstraints.LINE_END;
            row.add(buttonBlock, gbc);

            image.setVisible(true);
            imageDelete.setVisible(true);
            imageEdit.setVisible(true);
        }

        if(point.getType() != "depot") {
            row.addMouseListener(new MouseAdapter() {
                @Override
                public void mousePressed(MouseEvent e) {

                    for (String j : jpanelList.keySet()) {
                        jpanelList.get(j).setBackground(new Color(61, 61, 61));

                    }
                    if (!point.getId().equals(req.getDepot().getId())) {

                        jpanelList.get(tour.getPointsDef().get(tour.getPointsDef().indexOf(point)).getIdAssociated()).setBackground(new Color(116, 69, 206, 136));

                    }
                    row.setBackground(new Color(116, 69, 206));

                    Map m = mapView.getMap();

                    m.setTest(true);
                    m.setCurentid(point.getId());
                    m.repaint();
                }
            });
        }

        orderJpanelList.add(point.getId());
        jpanelList.put(point.getId(), row);
    }

    /**
     * setter of the data in the header
     * @param addButton
     * @param V2
     * @param V3
     * @param V4
     */
    private void setHeaderTour(JButton addButton,String V2, String V3, String V4 ) {

        //TODO : D??clarer panel du haut avec indices d'aide ?? la tourn??e

        headerInfo.setPreferredSize(new Dimension(100, 100));
        headerInfo.removeAll();
        headerInfo.validate();
        headerInfo.setBackground(new Color(86,86,86));
        headerInfo.setLayout(new BoxLayout(headerInfo, BoxLayout.X_AXIS));
        headerInfo.setPreferredSize(new Dimension(1000, 100));
        headerInfo.setMaximumSize(new Dimension(1000, 100));
        headDeparture = new HeadInfo("Depature", V2);
        headETA = new HeadInfo("ETA", V3);
        headDuration = new HeadInfo("Duration", V4);

        headerInfo.add(Box.createHorizontalGlue());


        headerInfo.add(addButton);
        headerInfo.add(Box.createHorizontalGlue());

        headerInfo.add(headDeparture);
        headerInfo.add(Box.createHorizontalGlue());
        headerInfo.add(headETA);
        headerInfo.add(Box.createHorizontalGlue());
        headerInfo.add(headDuration);
        headerInfo.add(Box.createHorizontalGlue());
        headerInfo.revalidate();
        headerInfo.repaint();
    }


    /**
     * method which init the header in add point mode
     */
    private void setExpAddRequest(){
        headerInfo.setPreferredSize(new Dimension(100, 100));
        headerInfo.removeAll();
        headerInfo.validate();


        headerInfo.setBackground(new Color(86,86,86));
        headerInfo.setPreferredSize(new Dimension(1000, 100));
        headerInfo.setMaximumSize(new Dimension(1000, 100));

        headExp = new HeadInfo("Pour ajoutez une nouvelle requ??te ?? cette tourn??e cliquez une premi??re fois pour d??signer le pickUp et une deuxi??me fois pour chosir le delivery");

        headerInfo.add(headExp);
        headerInfo.revalidate();
        headerInfo.repaint();

    }

    /**
     * method which load a tour, create a point list and display the tour
     * @param tp
     * @throws ParseException
     */
    public void loadRequest(String tp) throws ParseException {
        System.out.println("TourView.loadRequest");
        // Cr??er la ligne pour chaque point de passage ?? partir du mod??le de donn??es
        if(tour == null){
            System.out.println("TourView.loadRequest : tour is null");
        }
        else{
            if(orderJpanelList!=null)
                orderJpanelList.clear();
            if(jpanelList!=null)
                jpanelList.clear();

            tour.getPointsDef().forEach((s) -> {
                createJPanelPoint(s);
            });
        }
        displayTourView(tp);
    }


    /**
     * method which allowed to highlight a row
     * @param id
     */
    public void highlight(String id){

        for (String j :jpanelList.keySet()){
            jpanelList.get(j).setBackground(new Color(61, 61, 61));
        }
        Map m=mapView.getMap();
        m.setCurentid(id);
        m.setTest(true);
        m.repaint();
        JPanel point = jpanelList.get(id);
        point.setBackground(new Color(116, 69, 206));
    }


    /**
     * method which change view and create editable label to edit point
     * @param id
     * @return
     */
    public String editPoint(String id) {
        JPanel point = jpanelList.get(id);

        String type = "entrepot";
        String location = "location";
        DateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss");

        String scheduleString = dateFormat3.format(req.getListePoint().get(id).getSchedule());
        for(Point p : tour.getPointsDef()){
            System.out.println("    "+p.getId());
            if(p.getId().equals(id)){
                type = p.getType();
                location = p.getId();
                break;
            }
        }

        point.removeAll();

        point.setBackground(new Color(61,61,61));
        point.setName(String.valueOf(1)); //jsp ?? quoi ??a sert
        point.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        point.setPreferredSize(new Dimension(380, 60));
        point.setMaximumSize(new Dimension(380, 60));
        point.setMinimumSize(new Dimension(380, 60));

        ImageIcon iconValide = new ImageIcon (new ImageIcon("./img/iconSubmit.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        JLabel imageValide = new JLabel(iconValide);
        imageValide.setBackground(new Color(116, 69, 206));
        imageValide.setOpaque(true);

        JTextField fieldLocation = new JTextField(location);
        fieldLocation.setBackground(new Color(86, 86, 86));
        fieldLocation.setForeground(Color.WHITE);
        fieldLocation.setBorder(BorderFactory.createEmptyBorder());
        JTextField fieldHour = new JTextField(scheduleString);
        fieldHour.setBackground(new Color(86, 86, 86));
        fieldHour.setForeground(Color.WHITE);
        fieldHour.setBorder(BorderFactory.createEmptyBorder());


        JButton confirmEdit = new JButton(iconValide);

        confirmEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.confirmPointEdition(id, 0, fieldLocation.getText(), fieldHour.getText());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                String idToChange = String.valueOf(id); //recupere l'id du point ?? changer

                Point pointToChange = req.getListePoint().get(idToChange); //viens recuperer le point correspondant dans la liste des requetes
                SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
                String scheduleToChange = fieldHour.getText();
                Date newScheduleDate = null;
                try {
                    newScheduleDate = sdf.parse(scheduleToChange);
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                req.getListePoint().get(idToChange).setSchedule(newScheduleDate);
            }
        });


        ImageIcon iconPoint;
        if ( type.equals("depot")) {
            iconPoint = new ImageIcon(new ImageIcon("./img/iconDepot.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        } else if (type.equals("pickUp")) {
            iconPoint = new ImageIcon(new ImageIcon("./img/iconPickUp.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        } else {
            iconPoint = new ImageIcon(new ImageIcon("./img/iconDelivery.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        }
        JLabel image = new JLabel(iconPoint);

        JPanel adressPanel = new JPanel();
        adressPanel.setLayout(new BoxLayout(adressPanel, BoxLayout.X_AXIS));
        adressPanel.setBackground(new Color(86,86,86));
        adressPanel.setPreferredSize(new Dimension(150, 50));
        adressPanel.add(Box.createRigidArea(new Dimension(10,0)));
        adressPanel.add(fieldLocation,BorderLayout.WEST);

        confirmEdit.setUI(new BasicButtonUI());
        confirmEdit.setPreferredSize(new Dimension(50,50));
        confirmEdit.setBackground(new Color(116, 69, 206));
        confirmEdit.setOpaque(true);

        JPanel heurePanel = new JPanel();
        heurePanel.setLayout(new BoxLayout(heurePanel, BoxLayout.X_AXIS));
        heurePanel.setBackground(new Color(86,86,86));
        heurePanel.setPreferredSize(new Dimension(60, 50));
        heurePanel.add(Box.createRigidArea(new Dimension(5,0)));
        heurePanel.add(fieldHour, BorderLayout.CENTER);

        gbc.gridx = gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        point.add(image, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        point.add(adressPanel,gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        point.add(heurePanel, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 0, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        point.add(confirmEdit, gbc);

        image.setVisible(true);
        point.revalidate();
        point.repaint();
        scheduleString = dateFormat3.format(req.getListePoint().get(id).getSchedule());
        System.out.println("------------->TourView.scheduleString : " + scheduleString);
        return scheduleString;
    }

    /**
     * method which change view to delete point
     * @param id
     */
    public void deletePoint(String id) {

        JPanel point = jpanelList.get(id);
        String heure = "00:00:00";
        String type = "depot";
        System.out.println("ID to delete : " + id);
        for(Point p : tour.getPointsDef()){
            System.out.println("    "+p.getId());
            if(p.getId().equals(id)){
                DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
                heure = dateFormat.format(p.getSchedule());
                type = p.getType();
                break;
            }
        }
        System.out.println("TourView.deletePoint "+type+" at "+heure);

        point.removeAll();

        point.setBackground(new Color(61,61,61));
        point.setName(String.valueOf(1)); //jsp ?? quoi ??a sert
        point.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        point.setPreferredSize(new Dimension(380, 60));
        point.setMaximumSize(new Dimension(380, 60));
        point.setMinimumSize(new Dimension(380, 60));

        JPanel adressPanel = new JPanel();
        JButton confirmDelete = new JButton("ConfirmDelete");
        confirmDelete.setBackground(new Color(198,52,52));
        confirmDelete.setForeground(Color.WHITE);
        confirmDelete.setUI(new BasicButtonUI());
        confirmDelete.setActionCommand("confirmDelete" + id);
        confirmDelete.addActionListener(buttonListener);

        adressPanel.setLayout(new BoxLayout(adressPanel, BoxLayout.X_AXIS));
        adressPanel.setBackground(new Color(198,52,52));
        adressPanel.setPreferredSize(new Dimension(150, 50));
        adressPanel.add(Box.createHorizontalGlue());
        adressPanel.add(confirmDelete,BorderLayout.CENTER);
        adressPanel.add(Box.createHorizontalGlue());
        adressPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 10));

        ImageIcon iconEdit = new ImageIcon (new ImageIcon("./img/iconEdit.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JLabel imageEdit = new JLabel(iconEdit);
        imageEdit.setBackground(new Color(86,86,86));
        imageEdit.setOpaque(true);

        ImageIcon iconDelete = new ImageIcon (new ImageIcon("./img/icons8-trash-240.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JLabel imageDelete = new JLabel(iconDelete);
        imageDelete.setBackground(new Color(198,52,52));
        imageDelete.setOpaque(true);

        JLabel duration = new JLabel(heure + " ");
        duration.setForeground(Color.WHITE);

        ImageIcon iconPoint;
        if ( type.equals("depot")) {
            iconPoint = new ImageIcon(new ImageIcon("./img/iconDepot.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        } else if (type.equals("pickUp")) {
            iconPoint = new ImageIcon(new ImageIcon("./img/iconPickUp.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        } else {
            iconPoint = new ImageIcon(new ImageIcon("./img/iconDelivery.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        }
        JLabel image = new JLabel(iconPoint);

        //Gestion bouton edit et delete

        JPanel buttonBlock = new JPanel();
        buttonBlock.setOpaque(false);
        buttonBlock.setBackground(new Color(61,61,61));
        buttonBlock.setLayout(new BoxLayout(buttonBlock, BoxLayout.Y_AXIS));

        //Gestion bouton edit
        JButton editButton = new JButton();
        editButton.setUI(new BasicButtonUI());
        editButton.setBackground(new Color(86,86,86));
        editButton.setOpaque(true);
        editButton.add(imageEdit);
        editButton.setActionCommand("editRow" + id);
        editButton.addActionListener(buttonListener);

        //Gestion bouton delete
        JButton deleteButton = new JButton("back");
        deleteButton.setUI(new BasicButtonUI());
        deleteButton.setBackground(new Color(198,52,52));
        deleteButton.setOpaque(true);
        deleteButton.setForeground(Color.WHITE);
        deleteButton.addActionListener(buttonListener);

        buttonBlock.add(editButton);
        buttonBlock.add(deleteButton);

        gbc.gridx = gbc.gridy = 0;
        gbc.insets = new Insets(0, 10, 0, 10);
        gbc.anchor = GridBagConstraints.LINE_START;
        point.add(image, gbc);

        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.weightx = 1;
        point.add(adressPanel,gbc);

        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = GridBagConstraints.RELATIVE;
        gbc.anchor = GridBagConstraints.BELOW_BASELINE_TRAILING;
        point.add(duration, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 0, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        point.add(buttonBlock, gbc);

        image.setVisible(true);
        imageDelete.setVisible(true);
        imageEdit.setVisible(true);

        point.revalidate();
        point.repaint();
    }


    /**
     * call method to add a point
     */
     public void addRequest(){
         setExpAddRequest();
         pointLocater.setAddPoint(true);

     }




    /**
     * call method to draw a point on the map
     * @param idPickup
     */
    public void drawpoint(String idPickup) {

        mapView.getMap().setDrawpointBool(true);
        mapView.getMap().setIdPickup(idPickup);

        mapView.getMap().repaint();
    }

    /**
     * method which allowed the exit of add point mode
     */
    public void sortirdeADD() {

        setHeaderTour(  addButton,this.tour.getDepartureTime(), this.tour.getArrivalTime(), this.tour.getTotalDuration());

        mapView.getMap().setDrawpointBool(false);
        mapView.getMap().setDrawpointBool2(false);


    }

    /**
     * call method to draw a pickup point and his delivery point associate on the map
     * @param idPickup
     * @param idDelivery
     */
    public void drawpoint2(String idPickup,String idDelivery) {
        mapView.getMap().setDrawpointBool(false);
        mapView.getMap().setDrawpointBool2(true);
        mapView.getMap().setIdPickup(idPickup);
        mapView.getMap().setIdDelivery(idDelivery);
        mapView.getMap().repaint();
    }

    /**
     * getter of the Xml path
     * @return tourPath
     */
    public String getTourPath() {return this.tourPath;}

    /**
     * getter of the request
     * @return req
     */
    public Request getRequest() {return this.req;}
}
