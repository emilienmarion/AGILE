package View;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import Controller.*;

public class buttonListener extends JFrame implements ActionListener {
    private Controller controller;

    public buttonListener(Controller controller){
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e){
        switch (e.getActionCommand()){
            case "Load map" : controller.loadMap();
            case "Load tour" : controller.loadTour();
        }

    }
}
