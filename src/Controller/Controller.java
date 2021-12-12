package Controller;

import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import Model.*;
import Utils.XmlUtils;
import View.Frame;
import View.TourView;

public class Controller {
    private final Frame frame;
    private MapData md;
    private Request loadRequest;
    private final boolean firstLoadTour = false;
    private Tour tour;
    private int i=0;
    private Model.Point pickUp;
    private Model.Point delivery;
    private final ListOfCommands l;

    public Controller(Frame frame) {
        System.out.println("Controller.CONSTRUCTOR");
        this.frame = frame;
        this.l = new ListOfCommands();
    }



    /**
     * Open a JFileChooser. Return true and call frame.loadTour if the file is an xml. Return false in other cases.
     * @return boolean
     */
    public boolean loadTour() throws ParseException{

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
                tour.loadNewRequest(loadRequest);
                if (!firstLoadTour) {
                    frame.getMapView().getMap().setTour(tour);
                    frame.switchToTourView(loadRequest, Firm);
                } else {
                    frame.getMapView().getMap().setTour(tour);
                    //frame.getMapView().loadRequest(tour);
                    frame.loadTour(tour);
                }
                frame.display();
                return true;
            }
        }else{
            System.out.println("File chooser closed or an error hapenned");
            return false;
        }
    }

    /**
     * Return
     * @return CRE
     */
    public boolean loadMap() {
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

            if(!verifXml(Firm)){
                System.out.println("File type is not xml");
                return false;
            }else {

                MapData loadedMap = XmlUtils.readMap(Firm);
                this.md=loadedMap;
                frame.loadMap(loadedMap, Firm);
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

    /**
     * Return True if the extension's parameter "firm" equals "xml", false else.
     * @param firm
     * @return boolean
     */
    private boolean verifXml(String firm) {
        System.out.println("Controller.verifXml on : " + firm);
        String[] chain = firm.split("\\.");
        return chain.length > 0 && chain[chain.length - 1].equals("xml");
    }




    public void loadEditMode() {
        System.out.println("Controller.loadEditMode");
    }

    public void confirmDeleteRow (String i) {
        System.out.println("Controller.confirmDeleteRow : "+i);
        // TODO : dans Frame, faire une map qui lie id et JPanel pour pouvoir les supprimer, modifier etc...
        // Suppression dans le modèle de données
        DeleteRowCommand drc = new DeleteRowCommand(i, tour, frame);
        l.add(drc);
        drc.doCommand();

        // Actualisation des IHM
        displayMapView();
        frame.getMapView().loadRequest(tour);

        //frame.confirmDeleteRow(i);
        frame.display();

    }

    public void displayMapView()
    {
        try {
            frame.getTourView().loadRequest(frame.getTourView().getTourPath());
            frame.getMapView().loadRequest(tour);
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    public void editPoint(String id) throws ParseException {
        System.out.println("Controller.editRow : "+id);
        // TODO : dans Frame, faire une map qui lie id et JPanel pour pouvoir les supprimer, modifier etc...


        String nvSchedule = frame.editPoint(id);
        System.out.println("------------->nvSchedule : " + nvSchedule);
        tour.editPoint(id, nvSchedule, false);



        frame.display();
    }

    public void deletePoint( String i) {
        System.out.println("Controller.deleteRow : "+i);
        frame.deletePoint(i);
        frame.display();
    }

    public void addRequest( ) {
        System.out.println("Controller.addRequest ");
        i=0;
        frame.addRequest();
    }
     public void addNewRequest(int x,int y)  {


         if (i > 1) {  //sortir du mode ajout
             frame.sortirdeADD();
         } else {
             Intersection inter = md.findIntersection(x, y);

             if (inter != null) {
System.out.println("i est égale à"+i);

                 if (i == 0) {
                      pickUp = new Model.Point(inter, 0, "pickUp");

                     drawpoint(pickUp.getId());
                 } else if (i == 1) {
                      delivery = new Model.Point(inter, 0, "delivery");

                     drawpoint2(pickUp.getId(),delivery.getId());
                     pickUp.setIdAssociated(delivery.getId());
                     delivery.setIdAssociated(pickUp.getId());

                     tour.addRequest(this.pickUp,this.delivery);
                     try {

                         frame.getMapView().loadRequest(tour);
                         frame.getTourView().loadRequest(frame.getTourView().getTourPath());
                         frame.getTourView().updateHeader();
                        // frame.getMapView(). mettre à jour la map
                     } catch (ParseException e) {
                         e.printStackTrace();
                     }
                 }

                 i++;
             }

         }

     }

    public void drawpoint2(String idPickup,String idDelivery){
        frame.drawpoint2( idPickup,idDelivery);
    }
     public void drawpoint(String id){
        frame.drawpoint(id);
     }

    public MapData getMd() {
        return md;
    }

    public void setMd(MapData md) {
        this.md = md;
    }

    public void placerPoint(Request req) {}



    public void confirmPointEdition(String id, int type, String location, String hour) throws ParseException {
        System.out.println("Controller.confirmEdit");
        System.out.println("DBG : "+id+" "+type+" "+location+" "+hour);
        EditPointCommand edc = new EditPointCommand(frame, tour, id, type, location, hour);
        l.add(edc);
        edc.doCommand();

    }

    public void setTourObject(Tour tour) {
        System.out.println("Controller.setTourObject");
        this.tour = tour;
    }

    public void undo(){

        System.out.println("UNDO");
        l.undo();
    }

    public void redo(){
        System.out.println("REDO");
        l.redo();
    }

    public int getI() {
        return i;
    }

    public void setI(int i) {
        this.i = i;
    }

    public void refreshMap(Graph graph ){

    }
}

