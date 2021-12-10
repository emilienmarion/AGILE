package Controller;

import Model.Intersection;
import Model.Point;
import Model.Tour;
import View.Frame;

import java.text.ParseException;

public class DeleteRowCommand implements Command{
    private String id;
    private Tour tour;
    private Frame frame;
    private Point pickUp;
    private Point delivery;
    public DeleteRowCommand(String id, Tour tour, Frame frame){
        this.id = id;
        this.tour = tour;
        this.frame = frame;
        if (tour.getPoints().get(id).equals("pickUp")){
            this.pickUp = tour.getPoints().get(id);
            String idDelivery = tour.getPoints().get(id).getIdAssociated();
            this.delivery = tour.getPoints().get(idDelivery);
        }else {
            this.delivery = tour.getPoints().get(id);
            String idPickUp = tour.getPoints().get(id).getIdAssociated();
            this.pickUp = tour.getPoints().get(idPickUp);
        }

    }
    public void doCommand(){
        tour.deletePoint(id);

        // Actualisation des IHM
        try {
            frame.getTourView().loadRequest(frame.getTourView().getTourPath());
            frame.getMapView().loadRequest(tour);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        //frame.confirmDeleteRow(i);
        frame.display();
    }
    public void undoCommand(){
        frame.drawpoint(pickUp.getId());
        frame.drawpoint2(pickUp.getId(),delivery.getId());
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



}
