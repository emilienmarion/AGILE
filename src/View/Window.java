package View;

import javax.swing.*;
import java.awt.*;

public class Window {
    protected JFrame frame;
    protected JButton loadMapButton;
    protected JButton loadTourButton;
    protected JLabel mapPath;
    protected JPanel mapZonePanel;
    protected JPanel welcomeZonePanel;
    protected JPanel menuBar;
    protected JPanel mainPanel;
    protected JPanel map;

    protected void initWindow()
    {
        System.out.println("Window.init");
        frame = new JFrame("exemple");
        frame.setSize(800, 500);
        frame.setResizable(false);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        loadMapButton = new JButton("Load map");
        loadMapButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        loadTourButton = new JButton("Load Tour");
        loadTourButton.setAlignmentX(Component.CENTER_ALIGNMENT);

        mapPath = new JLabel("src/petiteMap.csv");

        menuBar = new JPanel();
        menuBar.setSize(800, 50);

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(1,2));

        welcomeZonePanel = new JPanel();
        welcomeZonePanel.setSize(400, 400);
        welcomeZonePanel.setBackground(Color.RED);
        welcomeZonePanel.setLayout(new BoxLayout(welcomeZonePanel, BoxLayout.Y_AXIS));

        mapZonePanel = new JPanel();
        mapZonePanel.setSize(400, 400);
        mapZonePanel.setBackground(Color.GRAY);
        mapZonePanel.setLayout(new BoxLayout(mapZonePanel, BoxLayout.Y_AXIS));


        map = new JPanel();
        map.setSize(350, 300);
        map.setBackground(Color.BLACK);
        map.setAlignmentX(Component.CENTER_ALIGNMENT);

    }

    public void Display()
    {
        initWindow();
        System.out.println("Window.display");
        mapZonePanel.add(map);
        mapZonePanel.add(mapPath);

        welcomeZonePanel.add(loadMapButton);
        welcomeZonePanel.add(Box.createRigidArea(new Dimension(0,20)));
        welcomeZonePanel.add(loadTourButton);

        mainPanel.add(mapZonePanel);
        mainPanel.add(welcomeZonePanel);

        frame.getContentPane().add(menuBar, BorderLayout.PAGE_START);
        frame.getContentPane().add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }
}
