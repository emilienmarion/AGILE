package View;

import javax.swing.*;
import java.awt.event.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import Controller.*;
import Model.Point;
import View.*;

public class ButtonListener extends JFrame implements ActionListener {
    private TourView tourView;
    private final Controller controller;
    private String XMlMapPath;
    private final Frame frame;

    /**
     * constructor of the Class ButtonListener
     * @param controller
     * @param frame
     */
    public ButtonListener(Controller controller, Frame frame) {
        System.out.println("ButtonListener.CONSTRUCTOR");
        this.frame = frame;
        this.controller = controller;
    }

    /**
     * Listen buttons calls implememted with this ButtonListener class and link them to the controller
     * @param e Action listened
     */
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
            case "confirmEdition":
                //controller.confirmPointEdition();
                break;

            case "add request":
               controller.addRequest();
                break;

            case "back":
                controller.displayMapView();

            case "confirmDelete":
                controller.confirmDeleteRow(e.getActionCommand().subSequence(13, e.getActionCommand().length()).toString());

            case "Exit":
                System.exit(0);
                break;

            case "Undo":
                controller.undo();
                break;

            case "Redo":
                controller.redo();
                break;

            default:
                if (e.getActionCommand().contains("deleteRow")) {
                    controller.deletePoint(e.getActionCommand().subSequence(9, e.getActionCommand().length()).toString());
                } else if (e.getActionCommand().contains("editRow")) {
                    try {
                        controller.editPoint(e.getActionCommand().subSequence(7, e.getActionCommand().length()).toString());
                    } catch (ParseException ex) {
                        ex.printStackTrace();
                    }
                } else if (e.getActionCommand().contains("confirmDelete")) {
                    controller.confirmDeleteRow(e.getActionCommand().subSequence(13, e.getActionCommand().length()).toString());

                }
        }
    }
}