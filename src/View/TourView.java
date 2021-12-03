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
import Utils.Algorithm;
import Utils.GraphConverter;

public class TourView {
    protected JFrame frame;
    protected JPanel rightPanel;
    protected JPanel headerInfo;
    protected JScrollPane scrollPane;
    protected MapView mapView;
    protected ButtonListener buttonListener;
    protected Request req;
    protected HashMap<String, JPanel> jpanelList;
    protected Controller controller;


    public TourView(JPanel rightPanel, JPanel headerInfo, ButtonListener buttonListener, MapView mapView, Request req, Controller controller) {
        this.rightPanel = rightPanel;
        this.buttonListener = buttonListener;
        this.headerInfo = headerInfo;
        this.mapView = mapView;
        this.req = req;
        this.jpanelList = new HashMap<>();
        this.controller = controller;

        rightPanel.setBackground(new Color(40,40,40));

        JPanel test = new JPanel();
        JLabel pathLabel = new JLabel("./uberIf/requests");
        pathLabel.setForeground(Color.WHITE);
        test.add(pathLabel);
        test.setBackground(new Color(40,40,40));

        JPanel componentToScroll = new JPanel();
        componentToScroll.setLayout(new BoxLayout(componentToScroll, BoxLayout.Y_AXIS));

        Point point ;
        HashMap<String, Point> listePoint = req.getListePoint();
        Point depot = req.getDepot();
        componentToScroll.add(createJPanelPoint(depot.getId(), depot.getType(), depot.getDuration()));
        for(String s : listePoint.keySet()){
            point = listePoint.get(s);
            componentToScroll.add(createJPanelPoint(point.getId(), point.getType(), point.getDuration()));
        }

        scrollPane = new JScrollPane(componentToScroll);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setMaximumSize(new Dimension(400, 400));
        scrollPane.setOpaque(true);
        scrollPane.getViewport().setBackground(Color.black);

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(scrollPane);
        rightPanel.add(test);
        rightPanel.add(Box.createVerticalGlue());

        initHeaderTour();
    }

    protected JPanel createJPanelPoint(String unId, String unType, int uneDuration)
    {
        // TODO : Faire marcher les logos
        ImageIcon pickupIcon = new ImageIcon("../img/iconTestBlack.png");

        JPanel row = new JPanel();
        row.setName(String.valueOf(1)); //jsp à quoi ça sert
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        row.setPreferredSize(new Dimension(100,30));
        row.setMaximumSize(new Dimension(200,60));

        System.out.println("request : " + this.req);
        if(this.req!=null) {
            HashMap<String, Point> listePoint = req.getListePoint();
            System.out.println("point : " +unId);

                JLabel id = new JLabel(unId+ " ");
                JLabel type = new JLabel(unType + " ");
                JLabel duration = new JLabel(String.valueOf(uneDuration + " "));


                JPanel buttonBlock = new JPanel();
                buttonBlock.setLayout(new BoxLayout(buttonBlock, BoxLayout.Y_AXIS));
                JButton editButton = new JButton("E");
                editButton.setActionCommand("editRow" + unId);
                JButton deleteButton = new JButton("D");
                deleteButton.setActionCommand("deleteRow" + unId);

                buttonBlock.add(editButton);
                buttonBlock.add(deleteButton);
                editButton.addActionListener(buttonListener);
                deleteButton.addActionListener(buttonListener);

                row.add(id);
                row.add(type);
                row.add(duration);
                row.add(buttonBlock);

        }

        jpanelList.put(unId, row);
        return row;
    }

    private void initHeaderTour() {
        //TODO : Déclarer panel de droite avec le scrollbar et les détails des tours

        //TODO : Déclarer panel du haut avec indices d'aide à la tournée

        headerInfo.setPreferredSize(new Dimension(100, 100));
        headerInfo.setBackground(new Color(86,86,86));

        //TODO : Déclarer la liste puis la rendre paramétrable en entrée de la méthode
    }

    public void loadRequest(Request req) {
        System.out.println("ToutPanel.loadRequest");
        Map map = mapView.getMap();
       // map.setMapData(mapView.getMapData());
        map.setReq(req);
        //map.repaint();

        HashMap<String,Point> pointList=req.getListePoint();
        Graph g= Algorithm.createGraph(pointList,map.getMapData());
        System.out.println(g);
        ArrayList<Path> ap=Algorithm.TSP(g);
        System.out.println(ap);
        Map m=mapView.getMap();
        m.setWay(ap);
        m.repaint();



    }

    public void editPoint(String id){
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

    public void confirmEdit(String id){
        System.out.println("TourPanel.confirmEdit");
        // TODO : changer aspect de la row
        JPanel point = jpanelList.get(id);
    }

}
;