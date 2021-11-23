package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import Controller.*;

public class Frame {

    protected JFrame frame;
    protected JButton loadMapButton;
    protected JButton loadTourButton;
    protected JLabel mapPath;
    protected JPanel leftPanel;
    protected JPanel rightPanel;
    protected JPanel mainPanel;
    protected JPanel splitPanel;
    protected JPanel map;
    protected JPanel headerInfo;
    protected JScrollPane scrollPane;

    protected Controller controller;
    protected buttonListener buttonListener;
    public Frame() {
        System.out.println("Frame.constructor");

        controller = new Controller(this);
        buttonListener = new buttonListener(controller);

        // Window design
        frame = new JFrame("UberIf");
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        //create a menu bar
        final JMenuBar menuBar = new JMenuBar();
        //create menus
        JMenu fileMenu = new JMenu("File");
        //create menu items
        JMenuItem newMenuItem = new JMenuItem("New");
        newMenuItem.setActionCommand("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        openMenuItem.setActionCommand("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        saveMenuItem.setActionCommand("Save");
        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");
        JMenuItem cutMenuItem = new JMenuItem("Cut");
        cutMenuItem.setActionCommand("Cut");
        JMenuItem copyMenuItem = new JMenuItem("Copy");
        copyMenuItem.setActionCommand("Copy");
        JMenuItem pasteMenuItem = new JMenuItem("Paste");
        pasteMenuItem.setActionCommand("Paste");

        //add menu items to menus
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.addSeparator();
        fileMenu.add(exitMenuItem);

        //add menu to menubar
        menuBar.add(fileMenu);

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

        // Add the structure to the frame
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);

        // Setup the structure's content
        initLoaderSide();
        initMapSide(400,400);
    }

    private void initMapSide(int mapWidth, int mapHeight)
    {
        System.out.println("Frame.initMapSide");
        mapPath = new JLabel("src/petiteMap.csv");
        mapPath.setForeground(Color.WHITE);

        leftPanel.setBackground(new Color(40,40,40));
        leftPanel.setPreferredSize(new Dimension(500, 500));
        leftPanel.setLayout(new BoxLayout(leftPanel, BoxLayout.Y_AXIS));

        map = new JPanel();
        map.setPreferredSize(new Dimension(200, 200));
        //map.setMaximumSize(new Dimension(200, 200));
        //map.setAlignmentX(Component.CENTER_ALIGNMENT);
        map.setBackground(Color.PINK);

        leftPanel.add(Box.createVerticalGlue());
        leftPanel.add(map);
        leftPanel.add(mapPath);
        leftPanel.add(Box.createVerticalGlue());
    }

    public void initHeaderTour() {
        //TODO : Déclarer panel de droite avec le scrollbar et les détails des tours

        //TODO : Déclarer panel du haut avec indices d'aide à la tournée

        headerInfo.setPreferredSize(new Dimension(100, 100));
        headerInfo.setBackground(new Color(86,86,86));

        //TODO : Déclarer la liste puis la rendre paramétrable en entrée de la méthode
    }

    private void initLoaderSide() {
        loadMapButton = new JButton("Load map");
        loadMapButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadMapButton.addActionListener(buttonListener);

        loadTourButton = new JButton("Load Tour");
        loadTourButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        loadTourButton.addActionListener(buttonListener);


        rightPanel.setPreferredSize(new Dimension(400, 400));
        rightPanel.setBackground(new Color(40,40,40));
        rightPanel.setLayout(new BoxLayout(rightPanel, BoxLayout.Y_AXIS));

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(loadMapButton);
        rightPanel.add(Box.createRigidArea(new Dimension(0,20)));
        rightPanel.add(loadTourButton);
        rightPanel.add(Box.createVerticalGlue());
    }

    public void initTourSide() {
        System.out.println("Frame.initTourSide");
        rightPanel.setBackground(new Color(40,40,40));

        loadTourButton = new JButton("Load Tour");
        JButton loadTourButton1 = new JButton("Load Tour");
        JButton loadTourButton2 = new JButton("Load Tour");

        JPanel componentToScroll = new JPanel();
        componentToScroll.setLayout(new BoxLayout(componentToScroll, BoxLayout.Y_AXIS));

        for(int i = 0; i < 100; i++)
        {
            componentToScroll.add(createJPanelPoint(i));
        }

        scrollPane = new JScrollPane(componentToScroll);
        scrollPane.setPreferredSize(new Dimension(400, 400));
        scrollPane.setMaximumSize(new Dimension(400, 400));
        scrollPane.setBackground(new Color(61,61,61));

        rightPanel.add(Box.createVerticalGlue());
        rightPanel.add(scrollPane);
        rightPanel.add(Box.createVerticalGlue());
    }

    protected JPanel createJPanelPoint(int i)
    {
        JPanel row = new JPanel();
        JLabel type = new JLabel(new ImageIcon("./Images/icons8-edit-150.png"));

        JLabel place = new JLabel(i+"66 rue JCVD \n 69100 Villeurbanne");
        // TODO : Identifier chaque bouton avec un id pour le buttonListener
        JButton editButton = new JButton("I");
        editButton.addActionListener(buttonListener);


        row.add(type);
        row.add(place);
        row.add(editButton);

        return row;
    }

    public void display()
    {
        System.out.println("Frame.display");
        frame.pack();
        frame.setVisible(true);
    }

    public void switchToTourView()
    {
        // Reset right panel
        rightPanel.remove(Box.createVerticalGlue());
        rightPanel.remove(loadMapButton);
        rightPanel.remove(Box.createRigidArea(new Dimension(0,20)));
        rightPanel.remove(loadTourButton);
        rightPanel.remove(Box.createVerticalGlue());

        // Setup with the new design
        initHeaderTour();
        initTourSide();

        // Display again
        frame.pack();
    }
}
