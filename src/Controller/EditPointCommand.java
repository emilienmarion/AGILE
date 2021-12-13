package Controller;


import Model.Tour;
import View.Frame;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class EditPointCommand implements Command {
    private final String id;
    private final Frame frame;
    private final Tour tour;
    private final int type;
    private final String newLocation;
    private final String newHour;
    private String previousLocation;
    private final String previousHour;

    /**
     * constructor of the Class EditPointCommand
     * @param frame
     * @param tour
     * @param id
     * @param type
     * @param location
     * @param hour
     */
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

    /**
     * method which confirm edition and display modification
     */
    public void doCommand(){

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

    /**
     * method which allowed to undo the edition
     */
    public void undoCommand(){
        System.out.println("PREVIOUS HOUR" + previousHour);

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

