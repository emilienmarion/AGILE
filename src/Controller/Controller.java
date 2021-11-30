package Controller;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;

import Model.MapData;
import Model.Request;
import Utils.XmlUtils;
import View.Frame;

public class Controller {
    private Frame frame;
    private MapData md;
    private Request loadRequest;
    public Controller(Frame frame) {
        this.frame = frame;
    }


    public void loadTour() {
        System.out.println("Controller.loadTour");
        String Firm="";
        JFileChooser chooser = new JFileChooser();//création dun nouveau filechosser
        chooser.setApproveButtonText("Select"); //intitulé du bouton
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            System.out.println("Vous avez choisis : "+chooser.getSelectedFile().getAbsolutePath()+"\n"); //si un fichier est selectionné, récupérer le fichier puis sont path et l'afficher dans le champs de texte
            Firm= chooser.getSelectedFile().getAbsolutePath();
            System.out.println(Firm);

        }
        loadRequest=XmlUtils.ReadRequest(Firm,this.md.getIntersections());
        //placerPoint(loadRequest);
        //frame.initMapSide(loadedMap);
        frame.switchToTourView(loadRequest);
        frame.display();

    }

    public MapData loadMap() {
        System.out.println("Controller.loadMap");
        String Firm="";
        JFileChooser chooser = new JFileChooser();//création dun nouveau filechosser
        chooser.setApproveButtonText("Select"); //intitulé du bouton
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            System.out.println("Vous avez choisis : "+chooser.getSelectedFile().getAbsolutePath()+"\n"); //si un fichier est selectionné, récupérer le fichier puis sont path et l'afficher dans le champs de texte
             Firm= chooser.getSelectedFile().getAbsolutePath();
            System.out.println(Firm);


        }
        MapData loadedMap = XmlUtils.readMap(Firm);

        this.md=loadedMap;
        frame.loadMap(loadedMap);

    frame.display();
        //View.Window frameh = new View.Window(1000,700,loadedMap);

        return loadedMap;
    }

    public void loadEditMode() {
        System.out.println("Controller.loadEditMode");
    }

    public void deletePoint(String i) {
        System.out.println("Controller.deleteRow : "+i);
        // TODO : dans Frame, faire une map qui lie id et JPanel pour pouvoir les supprimer, modifier etc...
        //frame.deleteRow(Integer.valueOf(i));
    }

    public void editPoint(String i) {
        System.out.println("Controller.editRow : "+i);
        frame.editPoint(Integer.valueOf(i));
        // TODO : dans Frame, faire une map qui lie id et JPanel pour pouvoir les supprimer, modifier etc...
        //frame.editRow(Integer.valueOf(i));
    }

    public void setMd(MapData md) {
        this.md = md;
    }

    public void placerPoint(Request req) {


    }
}

