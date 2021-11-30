package View;

import Controller.Controller;
import Model.Request;

import javax.swing.*;
import java.awt.*;

public class TourPanel {
    protected JFrame frame;
    protected JPanel rightPanel;
    protected JPanel headerInfo;
    protected JScrollPane scrollPane;
    protected MapPanel mapPanel;
    protected ButtonListener buttonListener;


    public TourPanel(JPanel rightPanel, JPanel headerInfo, ButtonListener buttonListener, MapPanel mapPanel) {
        this.rightPanel = rightPanel;
        this.buttonListener = buttonListener;
        this.headerInfo = headerInfo;
        this.mapPanel = mapPanel;

        rightPanel.setBackground(new Color(40,40,40));

        JPanel test = new JPanel();
        JLabel pathLabel = new JLabel("./uberIf/requests");
        pathLabel.setForeground(Color.WHITE);
        test.add(pathLabel);
        test.setBackground(new Color(40,40,40));

        JPanel componentToScroll = new JPanel();
        componentToScroll.setLayout(new BoxLayout(componentToScroll, BoxLayout.Y_AXIS));

        // TODO: lier le modèle à la vue via Observer
        for(int i = 0; i < 50; i++)
        {
            componentToScroll.add(createJPanelPoint(i));
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

    protected JPanel createJPanelPoint(int i)
    {
        // TODO : Faire marcher les logos
        ImageIcon pickupIcon = new ImageIcon("../img/iconTestBlack.png");

        JPanel row = new JPanel();
        row.setName(String.valueOf(i));
        row.setLayout(new BoxLayout(row, BoxLayout.X_AXIS));
        JLabel type = new JLabel("pickup");

        JLabel place = new JLabel(i+" rue JCVD \n 69100 Villeurbanne");

        JPanel buttonBlock = new JPanel();
        buttonBlock.setLayout(new BoxLayout(buttonBlock, BoxLayout.Y_AXIS));
        JButton editButton = new JButton("E");
        editButton.setActionCommand("editRow"+i);
        JButton deleteButton = new JButton("D");
        deleteButton.setActionCommand("deleteRow"+i);

        buttonBlock.add(editButton);
        buttonBlock.add(deleteButton);
        editButton.addActionListener(buttonListener);
        deleteButton.addActionListener(buttonListener);


        row.add(type);
        row.add(place);
        row.add(buttonBlock);

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

}
