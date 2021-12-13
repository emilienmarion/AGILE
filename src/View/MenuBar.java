package View;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;

public class MenuBar extends JMenuBar {
    private Color bgColor=Color.WHITE;
    private final ButtonListener buttonListener;

    /**
     * constructor of the Class MenuBar
     * @param bl
     */
    public MenuBar(ButtonListener bl){
        this.buttonListener = bl;
        this.setBorder(BorderFactory.createEmptyBorder());
        this.setPreferredSize(new Dimension(1000, 30));
        this.setMaximumSize(new Dimension(1000, 30));

        //create menus
        JMenu fileMenu = new JMenu("Options");
        //create menu items
        JMenuItem loadTourMenuItem = new JMenuItem("Load tour");
        loadTourMenuItem.setActionCommand("Load Tour");
        loadTourMenuItem.addActionListener(buttonListener);

        JMenuItem exitMenuItem = new JMenuItem("Exit");
        exitMenuItem.setActionCommand("Exit");
        exitMenuItem.addActionListener(buttonListener);

        JMenuItem undoMenuItem = new JMenuItem("Undo");
        undoMenuItem.setActionCommand("Undo");
        undoMenuItem.addActionListener(buttonListener);

        JMenuItem redoMenuItem = new JMenuItem("Redo");
        redoMenuItem.setActionCommand("Redo");
        redoMenuItem.addActionListener(buttonListener);


        fileMenu.setForeground(Color.WHITE);
        fileMenu.setBackground(new Color(61, 61, 61));
        fileMenu.setBorder(BorderFactory.createEmptyBorder());

        loadTourMenuItem.setForeground(Color.WHITE);
        loadTourMenuItem.setBackground(new Color(61, 61, 61));
        loadTourMenuItem.setBorder(BorderFactory.createEmptyBorder());

        exitMenuItem.setForeground(Color.WHITE);
        exitMenuItem.setBackground(new Color(61, 61, 61));
        exitMenuItem.setBorder(BorderFactory.createEmptyBorder());

        //add menu items to menus
        fileMenu.add(loadTourMenuItem);

        fileMenu.add(exitMenuItem);
        fileMenu.add(undoMenuItem);
        fileMenu.add(redoMenuItem);


        //add menu to menubar
        this.add(fileMenu);
        this.setColor(new Color(61, 61, 61));
    }

    /**
     * setter of the color
     * @param color
     */
    public void setColor(Color color) {
        bgColor=color;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
        g2d.setColor(bgColor);
        g2d.fillRect(0, 0, getWidth() - 1, getHeight() - 1);

    }
}
