package View;

import Controller.Controller;
import Model.Point;
import Model.Request;
import Utils.Algorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import java.util.ArrayList;

import java.util.HashMap;
import Model.*;

public class TourView {
    protected JFrame frame;
    protected JPanel rightPanel;
    protected JPanel headerInfo;
    protected HeadInfo headDate;
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


    public TourView(JPanel rightPanel, JPanel headerInfo, ButtonListener buttonListener, MapView mapView, Request req, Controller controller, String TourPath) {
        this.rightPanel = rightPanel;
        this.buttonListener = buttonListener;
        this.headerInfo = headerInfo;
        this.mapView = mapView;
        this.req = req;
        this.jpanelList = new HashMap<>();
        this.controller = controller;

        initTourView(TourPath);
    }

    public void initTourView(String TourPath){
        rightPanel.removeAll();
        rightPanel.setBackground(new Color(40, 40, 40));

        JPanel test = new JPanel();
        JLabel pathLabel = new JLabel(TourPath);
        pathLabel.setForeground(Color.WHITE);
        test.add(pathLabel);
        test.setBackground(new Color(40, 40, 40));

        JPanel componentToScroll = new JPanel();
        componentToScroll.setLayout(new BoxLayout(componentToScroll, BoxLayout.Y_AXIS));
        componentToScroll.setBackground(new Color(61,61,61));

        Point point;
        HashMap<String, Point> listePoint = req.getListePoint();
        Point depot = req.getDepot();
        componentToScroll.add(createJPanelPoint(depot.getId(), depot.getType(), depot.getDuration()));
        for (String s : listePoint.keySet()) {
            point = listePoint.get(s);
            componentToScroll.add(createJPanelPoint(point.getId(), point.getType(), point.getDuration()));
        }

        scrollPane = new JScrollPane(componentToScroll);
        scrollPane.setBackground(new Color(61,61,61));
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setMaximumSize(new Dimension(400, 400));
        scrollPane.setOpaque(false);
        //scrollPane.getViewport().setBackground(Color.black);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(scrollPane);
        rightPanel.add(test);
        rightPanel.add(Box.createVerticalGlue());
    }

    protected JPanel createJPanelPoint(String unId, String unType, int uneDuration) {


        ImageIcon iconEdit = new ImageIcon (new ImageIcon("./img/icons8-edit-150.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
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
        GridBagConstraints gbc = new GridBagConstraints();

        row.setPreferredSize(new Dimension(380, 60));
        row.setMaximumSize(new Dimension(380, 60));
        row.setMinimumSize(new Dimension(380, 60));

        System.out.println("request : " + this.req);
        if (this.req != null) {
            HashMap<String, Point> listePoint = req.getListePoint();

            //System.out.println("point : " +unId);

            JLabel id = new JLabel(unId + " ");
            id.setForeground(Color.WHITE);

            JLabel duration = new JLabel(String.valueOf(uneDuration + " "));
            duration.setForeground(Color.WHITE);

            JPanel adressPanel = new JPanel();
            adressPanel.setLayout(new BoxLayout(adressPanel, BoxLayout.X_AXIS));
            adressPanel.setBackground(new Color(86,86,86));
            adressPanel.setPreferredSize(new Dimension(150, 50));
            adressPanel.add(id,BorderLayout.WEST);
            adressPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));



            if (unType == "depot") {
                icon = new ImageIcon (new ImageIcon("./img/icons8-garage-ouvert-24.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
            }
            else if (unType == "pickUp") {
                icon = new ImageIcon (new ImageIcon("./img/icons8-give-96.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
            }
            else {
                icon = new ImageIcon (new ImageIcon("./img/icons8-location-pin-100.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
            }

            JLabel image = new JLabel(icon);

            //Gestion bouton edit et delete

            JPanel buttonBlock = new JPanel();
            buttonBlock.setOpaque(true);
            //buttonBlock.setBackground(new Color(161,61,61));
            buttonBlock.setLayout(new BoxLayout(buttonBlock, BoxLayout.Y_AXIS));

            //Gestion bouton edit
            JButton editButton = new JButton();
            editButton.setBackground(new Color(86,86,86));
            editButton.setOpaque(true);
            editButton.add(imageEdit);
            editButton.setActionCommand("editRow" + unId);
            editButton.addActionListener(buttonListener);

            //Gestion bouton delete
            JButton deleteButton = new JButton();
            //deleteButton.setBackground(Color.RED);
            deleteButton.setBackground(new Color(86,86,86));
            deleteButton.setOpaque(true);
            deleteButton.add(imageDelete);
            deleteButton.setActionCommand("deleteRow" + unId);
            deleteButton.addActionListener(buttonListener);

            buttonBlock.add(editButton);
            buttonBlock.add(deleteButton);

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
            row.add(duration, gbc);

            gbc.gridx = 3;
            gbc.gridy = 0;
            gbc.insets = new Insets(0, 5, 0, 10);
            gbc.anchor = GridBagConstraints.LINE_END;
            row.add(buttonBlock, gbc);

            image.setVisible(true);
            imageDelete.setVisible(true);
            imageEdit.setVisible(true);

        }
        jpanelList.put(unId, row);
        return row;
    }

    private void setHeaderTour(String V1, String V2, String V3, String V4 ) {
        //TODO : Déclarer panel du haut avec indices d'aide à la tournée

        headerInfo.setPreferredSize(new Dimension(100, 100));
        headerInfo.removeAll();

        headerInfo.setBackground(new Color(86,86,86));
        headerInfo.setLayout(new BoxLayout(headerInfo, BoxLayout.X_AXIS));
        headerInfo.setPreferredSize(new Dimension(1000, 100));
        headerInfo.setMaximumSize(new Dimension(1000, 100));
        headDate = new HeadInfo("Date", V1);
        headDeparture = new HeadInfo("Depature", V2);
        headETA = new HeadInfo("ETA", V3);
        headDuration = new HeadInfo("Duration", V4);

        headerInfo.add(Box.createHorizontalGlue());
        headerInfo.add(headDate);
        headerInfo.add(Box.createHorizontalGlue());
        headerInfo.add(headDeparture);
        headerInfo.add(Box.createHorizontalGlue());
        headerInfo.add(headETA);
        headerInfo.add(Box.createHorizontalGlue());
        headerInfo.add(headDuration);
        headerInfo.add(Box.createHorizontalGlue());

    }

    public void loadRequest(Request req, String tp) {
        System.out.println("ToutPanel.loadRequest");

        Map map = mapView.getMap();
       // map.setMapData(mapView.getMapData());
        map.setReq(req);
        //map.repaint();


        map.addMouseListener(new PointLocater(map,controller));

        HashMap<String,Point> pointList=req.getListePoint();
        Graph g= Algorithm.createGraph(pointList,map.getMapData());
        //System.out.println(g);

        ArrayList<Path> ap=Algorithm.TSP(g);
        //System.out.println(ap);
        Map m=mapView.getMap();
        //m.setWay(ap);
        m.repaint();


        // TODO : Donner les infos de tournée
        // Changer les variables ci dessous avec les données récupérées
        String date1 = "21-45-3838";
        String departure1 = "22:01";
        String ETA1 = "22:03";
        String duration1 = "00:02";
        setHeaderTour(date1, departure1, ETA1, duration1);

        initTourView(tp);
    }


    public void highlight(String id){

        for (String j :jpanelList.keySet()){
            jpanelList.get(j).setBackground(new Color(61, 61, 61));
        }

        JPanel point = jpanelList.get(id);
        point.setBackground(new Color(116, 69, 206));
    }

    
    public void editPoint(String id) {

        JPanel point = jpanelList.get(id);


        int type;
        String location = "location";
        String hour = "33h33";

        point.removeAll();

        point.setBackground(new Color(61,61,61));
        point.setName(String.valueOf(1)); //jsp à quoi ça sert
        point.setLayout(new GridBagLayout());
        GridBagConstraints gbc = new GridBagConstraints();

        point.setPreferredSize(new Dimension(380, 60));
        point.setMaximumSize(new Dimension(380, 60));
        point.setMinimumSize(new Dimension(380, 60));

        ImageIcon iconValide = new ImageIcon (new ImageIcon("./img/icons8-en-cours-24.png").getImage().getScaledInstance(40, 40, Image.SCALE_DEFAULT));
        JLabel imageValide = new JLabel(iconValide);
        imageValide.setBackground(new Color(116, 69, 206));
        imageValide.setOpaque(false);


        JTextField fieldLocation = new JTextField(location);
        fieldLocation.setBackground(new Color(116, 69, 206));
        fieldLocation.setForeground(Color.WHITE);
        fieldLocation.setBorder(BorderFactory.createEmptyBorder());
        JTextField fieldHour = new JTextField(hour);
        fieldHour.setBackground(new Color(61, 61, 61));
        fieldHour.setForeground(Color.WHITE);
        fieldHour.setBorder(BorderFactory.createEmptyBorder());

        JButton confirmEdit = new JButton(iconValide);
        // Ici on squiz le button listener car j'ai pas trouvé comment passer des variables en paramètre pour donner
        // au controller les données à persister dans le modèle de donnée.
        confirmEdit.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                controller.confirmPointEdition(id, 0, fieldLocation.getText(), fieldHour.getText());
                String idToChange = String.valueOf(id); //recupere l'id du point à changer
                Point pointToChange = req.getListePoint().get(idToChange); //viens recuperer le point correspondant dans la liste des requetes
                int durationToChange = Integer.valueOf(fieldHour.getText()); //ici j'ai changé que la duration parce que les points n'ont pas encore d'heure de passage mais on changera ça quand on aura l'algo
                pointToChange.setDuration(durationToChange); //idem que ligne precedente
            }
        });



        JLabel image = new JLabel(icon);

        JPanel adressPanel = new JPanel();
        adressPanel.setLayout(new BoxLayout(adressPanel, BoxLayout.X_AXIS));
        adressPanel.setBackground(new Color(86,86,86));
        adressPanel.setPreferredSize(new Dimension(150, 50));
        adressPanel.add(fieldLocation,BorderLayout.WEST);
        //adressPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 0, 0));


        confirmEdit.setPreferredSize(new Dimension(50,55));
        confirmEdit.setBackground(new Color(116, 69, 206));
        confirmEdit.setOpaque(false);



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
        point.add(fieldHour, gbc);

        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.insets = new Insets(0, 5, 0, 10);
        gbc.anchor = GridBagConstraints.LINE_END;
        point.add(confirmEdit, gbc);

        image.setVisible(true);

    }

    public void confirmEdit(String id) {
        System.out.println("TourPanel.confirmEdit");
        // TODO : changer aspect de la row
        JPanel point = jpanelList.get(id);
    }


    public void setHeadDate(String date){this.headDate.setValue(date);}
    public void setHeadDeparture(String hour){this.headDate.setValue(hour);}
    public void setHeadETA(String hour){this.headDate.setValue(hour);}
    public void setHeadDuration(String duration){this.headDate.setValue(duration);}

}
