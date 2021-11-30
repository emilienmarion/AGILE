package View;

import Controller.Controller;
import Model.Point;
import Model.Request;

import javax.swing.*;
import java.awt.*;
import java.util.HashMap;

public class TourPanel {
    protected JFrame frame;
    protected JPanel rightPanel;
    protected JPanel headerInfo;
    protected JScrollPane scrollPane;
    protected MapPanel mapPanel;
    protected ButtonListener buttonListener;
    protected Request req;
    protected HashMap<Integer, JPanel> jpanelList;


    public TourPanel(JPanel rightPanel, JPanel headerInfo, ButtonListener buttonListener, MapPanel mapPanel, Request req) {
        this.rightPanel = rightPanel;
        this.buttonListener = buttonListener;
        this.headerInfo = headerInfo;
        this.mapPanel = mapPanel;
        this.req = req;
        this.jpanelList = new HashMap<>();

        rightPanel.setBackground(new Color(40,40,40));

        JPanel test = new JPanel();
        JLabel pathLabel = new JLabel("./uberIf/requests");
        pathLabel.setForeground(Color.WHITE);
        test.add(pathLabel);
        test.setBackground(new Color(40,40,40));

        JPanel componentToScroll = new JPanel();
        componentToScroll.setLayout(new BoxLayout(componentToScroll, BoxLayout.Y_AXIS));

        // TODO: lier le modèle à la vue via Observer
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
        Map map = mapPanel.getMap();
        map.setMapData(mapPanel.getMapData());
        map.setReq(req);
        map.repaint();
    }

    public void editPoint(int id){
        JPanel point = jpanelList.get(id);
        point.setBackground(Color.MAGENTA);
        JButton confirmEdit = new JButton("Confirm Edition");
        confirmEdit.addActionListener(buttonListener);
        point.add(confirmEdit);
    }

    public void confirmEdit(int i, String s, String s1){
        System.out.println("TourPanel.confirmEdit");
        // TODO : lier avec le modèle de données

    }

}
