package View;

import javax.swing.*;
import java.awt.event.*;
import java.text.ParseException;

import Controller.*;
import View.*;

public class ButtonListener extends JFrame implements ActionListener {
    private TourView tourView;
    private Controller controller;
    private String XMlMapPath;
    private Frame frame;

    public ButtonListener(Controller controller, Frame frame) {
        this.frame = frame;
        this.controller = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {

        switch (e.getActionCommand()) {
            case "Load map":
                controller.loadMap();
                break;
            case "Load Tour":

                try {
                    controller.loadTour();
                } catch (ParseException ex) {
                    ex.printStackTrace();
                }

                break;
            case "Confirm Edition":
                //controller.confirmPointEdition();
                break;
            case "Confirm Delete":
                controller.confirmDeleteRow(e.getActionCommand().subSequence(9, e.getActionCommand().length()).toString());
            default:
                if (e.getActionCommand().contains("deleteRow")) {
                    controller.deletePoint(e.getActionCommand().subSequence(9, e.getActionCommand().length()).toString());
                } else if (e.getActionCommand().contains("editRow")) {
                    controller.editPoint(e.getActionCommand().subSequence(7, e.getActionCommand().length()).toString());
                }
        }
    }
}