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


    /**
     * Open a JFileChooser. Return true and call frame.loadTour if the file is an xml. Return false in other cases.
     * @return boolean
     */
    public boolean loadTour() {
        System.out.println("Controller.loadTour");
        String Firm="";
        JFileChooser chooser = new JFileChooser();//création dun nouveau filechosser
        chooser.setApproveButtonText("Select"); //intitulé du bouton

        if (chooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION && chooser.getSelectedFile() != null) {
            System.out.println("Vous avez choisis : " + chooser.getSelectedFile().getAbsolutePath() + "\n"); //si un fichier est selectionné, récupérer le fichier puis sont path et l'afficher dans le champs de texte
            Firm = chooser.getSelectedFile().getAbsolutePath();

            if(!verifXml(Firm)){
                System.out.println("File type is not xml");
                return false;
            }else {
                loadRequest = XmlUtils.ReadRequest(Firm, this.md.getIntersections());

                if (!firstLoadTour) {
                    frame.switchToTourView(loadRequest, Firm);
                } else {
                    frame.loadTour(loadRequest);
                }
                frame.display();

                return true;
            }
        }else{
            System.out.println("File chooser closed or an error hapenned");
            return false;
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
        frame.loadMap(loadedMap, Firm);
        frame.display();

        return loadedMap;
    }

    /**
     * Return True if the extension's parameter "firm" equals "xml", false else.
     * @param firm
     * @return boolean
     */
    private boolean verifXml(String firm) {
        System.out.println("Controller.verifXml on : " + firm);
        String chain[] = firm.split("\\.");
        if(chain.length > 0 && chain[chain.length-1].equals("xml")){
            return true;
        }else{
            return false;
        }
    }


    public void highLight(String i){
        frame.highlight(i);
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

