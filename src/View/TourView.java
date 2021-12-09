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
import java.util.Date;


import java.awt.event.MouseAdapter;


import java.awt.event.MouseEvent;


import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import Model.*;
import obs.Observable;
import obs.Observer;


public class TourView implements Observer {

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
    protected HashMap<String, JPanel> jpanelList;
    protected Controller controller;
    protected String filename;
    protected ImageIcon icon;
    protected Tour tour;

    protected PointLocater pointLocater;

    protected HashMap<String, String> infoTour;
    protected String tourPath;






    public TourView(JPanel rightPanel, JPanel headerInfo, ButtonListener buttonListener, MapView mapView, Request req, Controller controller, String TourPath, Tour tour) throws ParseException {

        this.rightPanel = rightPanel;
        this.buttonListener = buttonListener;
        this.headerInfo = headerInfo;
        this.mapView = mapView;
        this.req = req;
        this.jpanelList = new HashMap<>();
        this.controller = controller;
        this.tour = tour;
       this.tourPath = TourPath;
 
        Map map = mapView.getMap();
        pointLocater=new PointLocater(map,controller);
        map.addMouseListener(pointLocater);

        
    }

    
      

    public void displayTourView(String TourPath) throws ParseException{
        //TODO finir le refectoring displayTourView, initialisation du tour dans Tour.java


        rightPanel.removeAll();

        rightPanel.setBackground(new Color(40, 40, 40));

        JPanel pathPanel = new JPanel();
        JLabel pathLabel = new JLabel(TourPath);
        pathLabel.setForeground(Color.WHITE);
        pathPanel.add(pathLabel);
        pathPanel.setBackground(new Color(40, 40, 40));

// Bouton d'ajout de demande
        ImageIcon iconAdd = new ImageIcon (new ImageIcon("./img/AddIcon.png").getImage().getScaledInstance(60, 60, Image.SCALE_DEFAULT));
        JLabel imageAdd = new JLabel(iconAdd);
        imageAdd.setBackground(new Color(86,86,86));
        imageAdd.setOpaque(true);

        JButton addButton = new JButton();
        addButton.setUI(new BasicButtonUI());
        addButton.setBackground(new Color(86,86,86));
        addButton.setOpaque(true);
        addButton.add(imageAdd);
        addButton.setActionCommand("add request" );
        addButton.addActionListener(buttonListener);

        JPanel componentToScroll = new JPanel();
        componentToScroll.setLayout(new BoxLayout(componentToScroll, BoxLayout.Y_AXIS));
        componentToScroll.setBackground(new Color(61,61,61));


        jpanelList.forEach((id, value) -> componentToScroll.add(value));

        scrollPane = new JScrollPane(componentToScroll);
        scrollPane.setBackground(new Color(61,61,61));
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setMaximumSize(new Dimension(400, 400));
        scrollPane.setOpaque(false);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(scrollPane);
        rightPanel.add(pathPanel);
        rightPanel.add(Box.createVerticalGlue());

         DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
       // setHeaderTour(addButton, req.getDepartureTime(), dateFormat.format(listePointDef.get(listePointDef.size()-1).getSchedule()), "");
   
        setHeaderTour(  addButton,this.tour.getDepartureTime(), this.tour.getArrivalTime(), this.tour.getTotalDuration());

    }


    protected JPanel createJPanelPoint(int i, int tailleListe,String unId, String unType, int uneDuration, float unCost, Date unSchedule) {

        ImageIcon iconEdit = new ImageIcon (new ImageIcon("./img/iconEdit.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JLabel imageEdit = new JLabel(iconEdit);
        imageEdit.setBackground(new Color(86,86,86));
        imageEdit.setOpaque(true);

        ImageIcon iconDelete = new ImageIcon (new ImageIcon("./img/icons8-trash-240.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        JLabel imageDelete = new JLabel(iconDelete);
        imageDelete.setBackground(new Color(198,52,52));
        imageDelete.setOpaque(true);

        JPanel row = new JPanel();
        row.setBackground(new Color(61,61,61));
        row.setName(String.valueOf(1)); //jsp à quoi ça sert
        row.setLayout(new GridBagLayout());
        row.setPreferredSize(new Dimension(380, 60));
        row.setMaximumSize(new Dimension(380, 60));
        row.setMinimumSize(new Dimension(380, 60));
        GridBagConstraints gbc = new GridBagConstraints();

        if (this.req != null) {

            JLabel id = new JLabel(unId + " ");
            id.setForeground(Color.WHITE);
            DateFormat dateFormat2 = new SimpleDateFormat("HH:mm:ss");
            String scheduleString;
            if(i==0){
                scheduleString= "8:00:00";
            }else{
                scheduleString = dateFormat2.format(unSchedule);
            }


            JLabel duration = new JLabel(scheduleString);
            duration.setForeground(Color.WHITE);


            //Gestion adresse
            JPanel adressPanel = new JPanel();
            adressPanel.setLayout(new BoxLayout(adressPanel, BoxLayout.X_AXIS));
            adressPanel.setBackground(new Color(86,86,86));
            adressPanel.setPreferredSize(new Dimension(150, 50));
            adressPanel.add(id,BorderLayout.WEST);
            adressPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));


            //Gestion de l'heure
            //JLabel costToReach = new JLabel(String.valueOf((unCost/60) + " "));
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String heurePassage = dateFormat.format(unSchedule);
            JLabel schedule = new JLabel(heurePassage);
            schedule.setForeground(Color.WHITE);


            if (unType == "depot") {
                icon = new ImageIcon (new ImageIcon("./img/iconDepot.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
            }
            else if (unType == "pickUp") {
                icon = new ImageIcon (new ImageIcon("./img/iconPickUp.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
            }
            else {
                icon = new ImageIcon (new ImageIcon("./img/iconDelivery.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
            }

            JLabel image = new JLabel(icon);

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
            editButton.setActionCommand("editRow" + unId);
            editButton.addActionListener(buttonListener);

            //Gestion bouton delete
            JButton deleteButton = new JButton();
            deleteButton.setUI(new BasicButtonUI());
            deleteButton.setBackground(new Color(198,52,52));
            deleteButton.setOpaque(true);
            deleteButton.add(imageDelete);
            deleteButton.setActionCommand("deleteRow" + unId);
            deleteButton.addActionListener(buttonListener);

            if(i!=0 && i!= tailleListe) {
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
            row.add(adressPanel,gbc);


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
        row.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {

                for (String j :jpanelList.keySet()){
                    jpanelList.get(j).setBackground(new Color(61, 61, 61));

                }
                if(!unId.equals(req.getDepot().getId()) && i!=0) {


                    jpanelList.get(req.getListePoint().get(unId).getIdAssociated()).setBackground(new Color(116, 69, 206, 136));
                }
                row.setBackground(new Color(116, 69, 206));

                Map m=mapView.getMap();

                m.setTest(true);
                m.setCurentid(unId);
                m.repaint();
               
            }

        });


       if(i==0){
           jpanelList.put("1", row);
       }else {
           jpanelList.put(unId, row);
       }
        return row;
    }




/*
    private void initHeaderTour() {
        //TODO : Déclarer panel de droite avec le scrollbar et les détails des tours

        //TODO : Déclarer panel du haut avec indices d'aide à la tournée

        headerInfo.setPreferredSize(new Dimension(100, 100));
        headerInfo.setBackground(new Color(86, 86, 86));
    }*/

    //private void setHeaderTour(JButton addButton, String V2, String V3, String V4 ) {

    private void setHeaderTour(JButton addButton,String V2, String V3, String V4 ) {

        //TODO : Déclarer panel du haut avec indices d'aide à la tournée

        headerInfo.setPreferredSize(new Dimension(100, 100));
        headerInfo.removeAll();

        headerInfo.setBackground(new Color(86,86,86));
        headerInfo.setLayout(new BoxLayout(headerInfo, BoxLayout.X_AXIS));
        headerInfo.setPreferredSize(new Dimension(1000, 100));
        headerInfo.setMaximumSize(new Dimension(1000, 100));


        headExp = new HeadInfo("Pour ajouter une requête, cliquez une première fois pour choisir le pickup et une 2ème fois pour choisir le Delivery");

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


    }


    private void setExpAddRequest(){
        headerInfo.setPreferredSize(new Dimension(100, 100));
        headerInfo.removeAll();
        headerInfo.validate();

System.out.println("je suis laaa");
        headerInfo.setBackground(new Color(86,86,86));
       // headerInfo.setLayout(new BoxLayout(headerInfo, BoxLayout.X_AXIS));
        headerInfo.setPreferredSize(new Dimension(1000, 100));
        headerInfo.setMaximumSize(new Dimension(1000, 100));

        headExp = new HeadInfo("Pour ajoutez une nouvelle requets à cette tournée cliquez une première fois pour désigner le pickUp et une deuxième fois pour chosir le delivery");


       // headerInfo.add(Box.createHorizontalGlue());
        headerInfo.add(headExp);
        headerInfo.revalidate();
        headerInfo.repaint();

    }

    public void loadRequest(Request req, String tp) throws ParseException {
        System.out.println("TourView.loadRequest");
        tour.loadNewRequest(req);
        // Créer la ligne pour chaque point de passage à partir du modèle de données
        if(tour == null){System.out.println("TourView.loadRequest : tour is null");}
        else{
            AtomicInteger i = new AtomicInteger(1);
            tour.getPointsDef().forEach((s) -> {
                createJPanelPoint(i.get(), tour.getPointsDef().size(), s.getIdAssociated(), s.getType(), s.getDuration(), s.getCostToReach(), s.getSchedule());
                i.getAndIncrement();
            });
        }

        Map map = mapView.getMap();
        map.setReq(req);

        map.addMouseListener(new PointLocater(map,controller));

        HashMap<String,Point> pointList=req.getListePoint();
         pointList.put(req.getDepot().getId(),req.getDepot());
        Graph g= Algorithm.createGraph(pointList,map.getMapData(), req.getDepot());

        g.setSolution(Algorithm.TSP(g));
        Map m=mapView.getMap();

        m.setGraph(g);

       m.repaint();
        displayTourView(tp);

    }



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



    
    public String editPoint(String id) {
        JPanel point = jpanelList.get(id);

        int type;
        String location = "location";
        DateFormat dateFormat3 = new SimpleDateFormat("HH:mm:ss");
        String scheduleString = dateFormat3.format(req.getListePoint().get(id).getSchedule());

        point.removeAll();
        point.validate();

        point.setBackground(new Color(61,61,61));
        point.setName(String.valueOf(1)); //jsp à quoi ça sert
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
        //confirmEdit.setUI(new BasicButtonUI());
        // Ici on squiz le button listener car j'ai pas trouvé comment passer des variables en paramètre pour donner
        // au controller les données à persister dans le modèle de donnée.
        confirmEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                try {
                    controller.confirmPointEdition(id, 0, fieldLocation.getText(), fieldHour.getText());
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }
                String idToChange = String.valueOf(id); //recupere l'id du point à changer
                //Point pointToChange = tour.getTour().get(idToChange);
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

        JLabel image = new JLabel(icon);

        JPanel adressPanel = new JPanel();
        adressPanel.setLayout(new BoxLayout(adressPanel, BoxLayout.X_AXIS));
        adressPanel.setBackground(new Color(86,86,86));
        adressPanel.setPreferredSize(new Dimension(150, 50));
        adressPanel.add(Box.createRigidArea(new Dimension(10,0)));
        adressPanel.add(fieldLocation,BorderLayout.WEST);

        confirmEdit.setUI(new BasicButtonUI());
        confirmEdit.setPreferredSize(new Dimension(50,55));
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
        //point.repaint();
        scheduleString = dateFormat3.format(req.getListePoint().get(id).getSchedule());
        System.out.println("------------->TourView.scheduleString : " + scheduleString);
        return scheduleString;

    }

    public void confirmEdit(String id) {
        System.out.println("TourPanel.confirmEdit");
        // TODO : changer aspect de la row
        JPanel point = jpanelList.get(id);
    }


    public void deletePoint(String id) {

        JPanel point = jpanelList.get(id);

        String heure = "22h22";
        String type;

        point.removeAll();

        point.setBackground(new Color(61,61,61));
        point.setName(String.valueOf(1)); //jsp à quoi ça sert
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
        //confirmDelete.setOpaque(false);
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

        JLabel duration = new JLabel(String.valueOf(heure + " "));
        duration.setForeground(Color.WHITE);

        JLabel image = new JLabel(icon);

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
        JButton deleteButton = new JButton();
        deleteButton.setUI(new BasicButtonUI());
        deleteButton.setBackground(new Color(198,52,52));
        deleteButton.setOpaque(true);
        deleteButton.add(imageDelete);
        deleteButton.setActionCommand("deleteRow" + id);
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
    }

    public void confirmDelete(String id) {
        System.out.println("TourPanel.confirmDelete");
        jpanelList.remove(id);
        try {
            displayTourView(tourPath);
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }


     public void addRequest(){
         setExpAddRequest();
         pointLocater.setAddPoint(true);

     }



 


    @Override
    public void update(Observable o, Object arg){
        // TODO : code pour display
    }

    public void drawpoint(String idPickup) {

        mapView.getMap().setDrawpointBool(true);
        mapView.getMap().setIdPickup(idPickup);

        mapView.getMap().repaint();
    }

    public void sortirdeADD() {



    }

    public void drawpoint2(String idPickup,String idDelivery) {
        mapView.getMap().setDrawpointBool(false);
        mapView.getMap().setDrawpointBool2(true);
        mapView.getMap().setIdPickup(idPickup);
        mapView.getMap().setIdDelivery(idDelivery);
        mapView.getMap().repaint();
    }
}
