package Controller;


import Model.Tour;
import View.Frame;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditPointCommand implements Command {
    private String id;
    private Frame frame;
    private Tour tour;
    private int type;
    private String newLocation;
    private String newHour;
    private String previousLocation;
    private String previousHour;

    public EditPointCommand(Frame frame, Tour tour, String id, int type, String location, String hour){
        this.id = id;
        this.frame = frame;
        this.tour = tour;
        this.type = type;
        this.newLocation = location;
        this.newHour = hour;
        Date previousHourDate = tour.getPoints().get(id).getSchedule();
        DateFormat dateFormat = new SimpleDateFormat("hh:mm:ss");
        this.previousHour = dateFormat.format(previousHourDate);



    }

    public void doCommand(){
        frame.confirmEdit(id);
        try {
            tour.editPoint(id, newHour, false);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("tour.getArrivalTime()" + tour.getArrivalTime());
        // TODO : appel des méthodes du modèle de données avec des arguments fictifs
        // Actualisation des IHM
        try {
            frame.getTourView().loadRequest(frame.getTourView().getTourPath());
            frame.getMapView().loadRequest(tour);
            frame.getTourView().updateHeader();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        frame.display();
    }
    public void undoCommand(){
        System.out.println("PREVIOUS HOUR" + previousHour);
        frame.confirmEdit(id);
        try {
            tour.editPoint(id, previousHour, true);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println("tour.getArrivalTime()" + tour.getArrivalTime());
        // TODO : appel des méthodes du modèle de données avec des arguments fictifs
        // Actualisation des IHM
        try {
            frame.getTourView().loadRequest(frame.getTourView().getTourPath());
            frame.getMapView().loadRequest(tour);
            frame.getTourView().updateHeader();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        frame.display();
    }

}

