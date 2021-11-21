package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
//the componenents are dispayed with coord basis of 1000*700 but the
public class Window extends JFrame {
    private JPanel mapZone;
    private Map map;
    private int sizeX;
    private int sizeY;
    private int scale;
    public Window() {
        super("Uber IF");
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        addWindowListener(l);
        setSize(1000, 700);
        Dimension screenSize = this.getSize();
        System.out.println(screenSize.toString());
        map=new Map();
        map.setLocation(100,100);
        this.getContentPane().add(map);
        this.setLayout(null);
        this.setVisible(true);

    }
}
