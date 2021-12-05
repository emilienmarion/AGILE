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
    private boolean firstLoadTour = false;



    public Controller(Frame frame) {
        this.frame = frame;
    }

    public void loadTour() {
        System.out.println("Controller.loadTour");
        String Firm="";
        JFileChooser chooser = new JFileChooser();//création dun nouveau filechosser
        chooser.setApproveButtonText("Select"); //intitulé du bouton
        try{
            if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
                System.out.println("Vous avez choisis : " + chooser.getSelectedFile().getAbsolutePath() + "\n"); //si un fichier est selectionné, récupérer le fichier puis sont path et l'afficher dans le champs de texte
                Firm = chooser.getSelectedFile().getAbsolutePath();
            }
            loadRequest=XmlUtils.ReadRequest(Firm,this.md.getIntersections());

            if(!firstLoadTour){
                frame.switchToTourView(loadRequest);
            }else{
                frame.loadTour(loadRequest);
            }
            frame.display();
        }catch (Exception e){
            System.out.println("Controller.loadTour Error : " + e);
        }
    }


    public void highLight(String i){
frame.highlight(i);
    }

    public MapData loadMap() {
        System.out.println("Controller.loadMap");
        String Firm="";
        JFileChooser chooser = new JFileChooser();//création dun nouveau filechosser
        chooser.setApproveButtonText("Select"); //intitulé du bouton
        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION)
        {
            System.out.println("Vous avez choisis: "+chooser.getSelectedFile().getAbsolutePath()+"\n"); //si un fichier est selectionné, récupérer le fichier puis sont path et l'afficher dans le champs de texte
            try {
                Firm = chooser.getSelectedFile().getAbsolutePath();
                System.out.println(Firm);
            }catch (Exception e){
                System.out.println("Error : " + e);
            }
        }
        MapData loadedMap = XmlUtils.readMap(Firm);
        this.md=loadedMap;
        frame.loadMap(loadedMap);
        frame.display();

        return loadedMap;
    }

    public void loadEditMode() {
        System.out.println("Controller.loadEditMode");
    }

    public void deleteRow(String i) {
        System.out.println("Controller.deleteRow : "+i);
        // TODO : dans Frame, faire une map qui lie id et JPanel pour pouvoir les supprimer, modifier etc...
        //frame.deleteRow(Integer.valueOf(i));
    }

    public void editPoint(String i) {
        System.out.println("Controller.editRow : "+i);
        // TODO : dans Frame, faire une map qui lie id et JPanel pour pouvoir les supprimer, modifier etc...
        frame.editPoint(i);
        frame.display();
    }

    public MapData getMd() {
        return md;
    }

    public void setMd(MapData md) {
        this.md = md;
    }

    public void placerPoint(Request req) {}



    public void confirmPointEdition(String id, int type, String location, String hour) {
        System.out.println("Controller.confirmEdit");
        System.out.println("DBG : "+id+" "+type+" "+location+" "+hour);
        frame.confirmEdit(id);
        // TODO : appel des méthodes du modèle de données avec des arguments fictifs
    }
}

