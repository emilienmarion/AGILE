package View;

import Model.MapData;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
//the componenents are dispayed with coord basis of 1000*700 but the
public class Window extends JFrame {

    private JPanel mapZone;
    private final Map map;
    private final int mapSquare=500;

    /**
     * constructor of the Class Windows
     * @param X
     * @param Y
     * @param md
     */
    public Window(int X,int Y,MapData md) {
        super("Uber IF");
        WindowListener l = new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        addWindowListener(l);
        setSize(X, Y);
        float diffX=md.getMaxX()-md.getMinX();
        float diffY=md.getMaxY()-md.getMinY();
        float scale=Math.min(mapSquare/diffX,mapSquare/diffY);
        map=new Map(50,50,diffX,diffY,scale,md);
        this.getContentPane().add(map);
        this.setLayout(null);
        this.setVisible(true);
        this.setResizable(false);
    }

    /** getter of the map
     * @return map
     */
    public Map getMap() {
        return map;
    }
}
