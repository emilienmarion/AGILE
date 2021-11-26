package Controller;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import View.Frame;

public class Controller {
    private Frame frame;
    public Controller(Frame frame) {
        this.frame = frame;
    }


    public void loadTour() {
        System.out.println("Controller.loadTour");
        frame.switchToTourView();
    }

    public void loadMap() {
        System.out.println("Controller.loadMap");

        JFileChooser chooser = new JFileChooser();//création dun nouveau filechosser
        chooser.setApproveButtonText("Select"); //intitulé du bouton
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            System.out.println("Vous avez choisis : "+chooser.getSelectedFile().getAbsolutePath()+"\n"); //si un fichier est selectionné, récupérer le fichier puis sont path et l'afficher dans le champs de texte
            String Firm = chooser.getSelectedFile().getAbsolutePath();
            System.out.println(Firm);
        }
    }

    public void loadEditMode() {
        System.out.println("Controller.loadEditMode");
    }

    public void deleteRow(String i) {
        System.out.println("Controller.deleteRow : "+i);
        // TODO : dans Frame, faire une map qui lie id et JPanel pour pouvoir les supprimer, modifier etc...
        //frame.deleteRow(Integer.valueOf(i));
    }

    public void editRow(String i) {
        System.out.println("Controller.editRow : "+i);
        // TODO : dans Frame, faire une map qui lie id et JPanel pour pouvoir les supprimer, modifier etc...
        //frame.editRow(Integer.valueOf(i));
    }
}
