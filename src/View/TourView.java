package View;

import Controller.Controller;
import Model.Point;
import Model.Request;
import Utils.Algorithm;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;



import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;


import java.awt.event.MouseAdapter;
import java.util.ArrayList;


import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


import java.util.HashMap;
import java.util.Set;
import java.util.HashSet;

import Model.*;
import obs.Observable;
import obs.Observer;



import Utils.Algorithm;
import Utils.GraphConverter;
import Utils.XmlUtils;





public class TourView implements Observer {

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





    public TourView(JPanel rightPanel, JPanel headerInfo, ButtonListener buttonListener, MapView mapView, Request req, Controller controller, String TourPath) throws ParseException {

        this.rightPanel = rightPanel;
        this.buttonListener = buttonListener;
        this.headerInfo = headerInfo;
        this.mapView = mapView;
        this.req = req;
        this.jpanelList = new HashMap<>();
        this.controller = controller;


        initTourView(TourPath);
    }

    public void initTourView(String TourPath) throws ParseException{

         /*
        HashMap<String,Point> pointList=req.getListePoint();
         pointList.put(req.getDepot().getId(),req.getDepot());
        Graph g= Algorithm.createGraph(pointList,map.getMapData(), req.getDepot());

        g.setSolution(Algorithm.TSP(g));
        Map m=mapView.getMap();
        m.setGraph(g);

        m.repaint();
        */




        rightPanel.removeAll();

        rightPanel.setBackground(new Color(40, 40, 40));

        JPanel test = new JPanel();
        JLabel pathLabel = new JLabel(TourPath);
        pathLabel.setForeground(Color.WHITE);
        test.add(pathLabel);
        test.setBackground(new Color(40, 40, 40));

        JPanel componentToScroll = new JPanel();
        componentToScroll.setLayout(new BoxLayout(componentToScroll, BoxLayout.Y_AXIS));
        componentToScroll.setBackground(new Color(80,80,80));

        Point point;
        HashMap<String, Point> listePoint = req.getListePoint();
       // ArrayList<String> listeString= (ArrayList<String>) listePoint.keySet();

        listePoint.put(req.getDepot().getId(),req.getDepot());

        ArrayList<Point> listePointDef = Tour.getTheFinalPointList(listePoint,  this.mapView, this.req);
        int i=0;

        for (Point s : listePointDef) {

            point = s;
            componentToScroll.add(createJPanelPoint(i,point.getId(), point.getType(), point.getDuration(), point.getCostToReach(), point.getSchedule()));
i++;

        }

        scrollPane = new JScrollPane(componentToScroll);
        scrollPane.setBackground(new Color(80,80,80));
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setMaximumSize(new Dimension(400, 400));
        scrollPane.setOpaque(false);
        //scrollPane.getViewport().setBackground(Color.black);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(scrollPane);
        rightPanel.add(test);
        rightPanel.add(Box.createVerticalGlue());
         DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
        setHeaderTour("", req.getDepartureTime(), dateFormat.format(listePointDef.get(listePointDef.size()-1).getSchedule()), "");
    }


    protected JPanel createJPanelPoint(int i,String unId, String unType, int uneDuration, float unCost, Date unSchedule) {

        // TODO : Faire marcher les logos

        ImageIcon iconEdit = new ImageIcon (new ImageIcon("./img/icons8-edit-150.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));
        ImageIcon iconDelete = new ImageIcon (new ImageIcon("./img/icons8-trash-240.png").getImage().getScaledInstance(20, 20, Image.SCALE_DEFAULT));

        JLabel imageEdit = new JLabel(iconEdit);
        JLabel imageDelete = new JLabel(iconDelete);


        JPanel row = new JPanel();
        row.setBackground(new Color(86,86,86));
        row.setName(String.valueOf(1)); //jsp à quoi ça sert
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setPreferredSize(new Dimension(100, 30));

        row.setMaximumSize(new Dimension(380, 60));


        if (this.req != null) {
            HashMap<String, Point> listePoint = req.getListePoint();

            //System.out.println("point : " +unId);

            JLabel id = new JLabel(unId + " ");
            JLabel type = new JLabel(unType + " ");
            JLabel duration = new JLabel(String.valueOf(uneDuration + " "));
            //JLabel costToReach = new JLabel(String.valueOf((unCost/60) + " "));
            DateFormat dateFormat = new SimpleDateFormat("HH:mm:ss");
            String heurePassage;
            if(i==0){
                 heurePassage = "8:00:00";
            }else{
                heurePassage = dateFormat.format(unSchedule);
            }

            JLabel schedule = new JLabel(heurePassage);



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
            JPanel buttonBlock = new JPanel();
            buttonBlock.setBackground(new Color(86,86,86));
            buttonBlock.setLayout(new BoxLayout(buttonBlock, BoxLayout.Y_AXIS));
            JButton editButton = new JButton();
            editButton.setBackground(new Color(70,70,70));
            editButton.add(imageEdit);
            editButton.setActionCommand("editRow" + unId);
            JButton deleteButton = new JButton();
            deleteButton.setBackground(new Color(140,40,40));
            deleteButton.add(imageDelete);
            deleteButton.setActionCommand("deleteRow" + unId);

            buttonBlock.add(editButton);
            buttonBlock.add(deleteButton);
            editButton.addActionListener(buttonListener);
            deleteButton.addActionListener(buttonListener);

            row.add(Box.createHorizontalGlue());
            row.add(duration);
            row.add(schedule);
            row.add(image);
            row.add(id);
            row.add(type);
            row.add(duration);
            row.add(buttonBlock);
            row.add(Box.createHorizontalGlue());
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

                row.setBackground(new Color(116, 69, 206));
                Map m=mapView.getMap();

                m.setTest(true);
                m.setCurentid(unId);
                m.repaint();
               
            }

        });


        jpanelList.put(unId, row);
        return row;
    }




    private void initHeaderTour() {
        //TODO : Déclarer panel de droite avec le scrollbar et les détails des tours

        //TODO : Déclarer panel du haut avec indices d'aide à la tournée

        headerInfo.setPreferredSize(new Dimension(100, 100));
        headerInfo.setBackground(new Color(86, 86, 86));
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

    public void loadRequest(Request req, String tp) throws ParseException {
        System.out.println("ToutPanel.loadRequest");

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

        initTourView(tp);
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

    
    public void editPoint(String id) {


        JPanel point = jpanelList.get(id);
        point.setBackground(Color.MAGENTA);
        int type;
        String location = "location";
        String hour = "33h33";

        point.removeAll();
        JTextField fieldLocation = new JTextField(location);
        JTextField fieldHour = new JTextField(hour);

        JButton confirmEdit = new JButton("Confirm Edition");
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

        point.add(fieldLocation);
        point.add(fieldHour);
        point.add(confirmEdit);
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

    @Override
    public void update(Observable o, Object arg){
        // TODO : code pour display
    }

}
