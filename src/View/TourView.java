package View;

import Controller.Controller;
import Model.MapData;
import Model.Point;
import Model.Request;
import Utils.XmlUtils;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.HashMap;

public class TourView {
    protected JFrame frame;
    protected JPanel mainPanel;
    protected JPanel headerInfo;
    protected JScrollPane scrollPane;
    protected MapView mapView;
    protected ButtonListener buttonListener;
    protected Request req;
    protected HashMap<Integer, JPanel> jpanelList;
    protected Controller controller;
    protected JLabel mapPath;
    private Map map;
    private MapData mdT;
    private final int mapSquare=500;
    private Image image;


    public TourView(JPanel mainPanel, JPanel headerInfo, ButtonListener buttonListener, MapView mapView, Request req, Controller controller) {
        this.buttonListener = buttonListener;
        this.headerInfo = headerInfo;
        this.mapView = mapView;
        this.req = req;
        this.jpanelList = new HashMap<>();
        this.controller = controller;
        this.mainPanel = mainPanel;

        //Initialisation du container
        mainPanel.setMinimumSize(new Dimension(1000, 700));
        mainPanel.setBackground(new Color(40,40,40));
        mainPanel.setLayout(new GridBagLayout());

        //Creation des composants

        JButton dateButton = new JButton("Date");
        JButton departureButton = new JButton("Departure");
        JButton ETAButton = new JButton("ETA");
        JButton durationButton = new JButton("Duration");

        JPanel mapPanel = new JPanel();
        mapPanel.setBackground(new Color(140,40,40));

        JPanel tourPanel = new JPanel();
        tourPanel.setBackground(new Color(86,86,86));

        JPanel test = new JPanel();
        JLabel pathLabel = new JLabel("./uberIf/requests");
        pathLabel.setForeground(Color.WHITE);
        test.add(pathLabel);
        test.setBackground(new Color(40,40,40));

        JPanel componentToScroll = new JPanel();
        componentToScroll.setLayout(new BoxLayout(componentToScroll, BoxLayout.Y_AXIS));

        //Ajout des conposants

        //Button date
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 15, 0, 0);
        mainPanel.add(dateButton, gbc);

        //Button Departure
        gbc.gridx = 1;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 15, 0, 0);
        mainPanel.add(departureButton, gbc);

        //Button ETA
        gbc.gridx = 2;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 15, 0, 0);
        mainPanel.add(ETAButton, gbc);

        //Button Duration
        gbc.gridx = 3;
        gbc.gridy = 0;
        gbc.gridwidth = 1;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 15, 0, 0);
        mainPanel.add(durationButton, gbc);

        //Panel map
        gbc.gridx = 0;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 15, 0, 15);
        gbc.fill = GridBagConstraints.BOTH;

        MapData loadedMap = XmlUtils.readMap("xmlFiles/smallMap.xml");
        mdT = loadedMap;
        float diffX=mdT.getMaxX()-mdT.getMinX();
        float diffY=mdT.getMaxY()-mdT.getMinY();
        float scale=Math.min(mapSquare/diffX,mapSquare/diffY);
        map = new Map(50,50,diffX,diffY,scale,mdT);
        map.repaint();
        mapPanel.add(map);
        mainPanel.add(mapPanel, gbc);


        //Panel tour

        gbc.gridx = 2;
        gbc.gridy = 1;
        gbc.gridwidth = 2;
        gbc.gridheight = 1;
        gbc.insets = new Insets(10, 15, 0, 10);

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

        tourPanel.add(scrollPane);
        mainPanel.add(tourPanel, gbc);




        initHeaderTour();
    }

    protected JPanel createJPanelPoint(String unId, String unType, int uneDuration)
    {
        // TODO : Faire marcher les logos
        ImageIcon image = new ImageIcon("../img/iconTestBlack.png");

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
                JLabel icon = new JLabel(image, JLabel.CENTER);


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

                row.add(icon);
                row.add(id);
                row.add(type);
                row.add(duration);
                row.add(buttonBlock);

        }

        jpanelList.put(Integer.valueOf(unId), row);
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
        map.setMapData(mapView.getMapData());
        map.setReq(req);
        map.repaint();
    }

    public void editPoint(int id){
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

    public void confirmEdit(int id){
        System.out.println("TourPanel.confirmEdit");
        // TODO : changer aspect de la row
        JPanel point = jpanelList.get(id);
    }



}
;