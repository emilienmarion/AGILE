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
        switch (e.getActionCommand()) {
            case "Load map":
                controller.loadMap();
                break;
            case "Load Tour":
                controller.loadTour();
                break;
            default:
                if (e.getActionCommand().contains("deleteRow")) {
                    controller.deleteRow(e.getActionCommand().subSequence(9, e.getActionCommand().length()).toString());
                }else if (e.getActionCommand().contains("editRow")) {
                    controller.editRow(e.getActionCommand().subSequence(7, e.getActionCommand().length()).toString());
                }
                ;
        }
    }
}
