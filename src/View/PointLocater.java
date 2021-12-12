package View;


import Controller.Controller;

import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class PointLocater extends MouseAdapter {

    private final Map map;
    private final Controller controller;
    private Point pcurent;
    private boolean addPoint;


    /**
     * constructor of the Class PointLocater
     * @param map1
     * @param controller
     */
    public PointLocater(Map map1, Controller controller) {
        this.controller = controller;
        map = map1;
        addPoint = false;


    }

    /**
     * getter of the current point
     * @return pcurrent
     */
    public Point getPcurent() {
        return pcurent;
    }

    /**
     * method which refers if the point must be add
     * @return addPoint
     */
    public boolean isAddPoint() {
        return addPoint;
    }

    /**
     * setter of the boolean add point which manage ..
     * @param addPoint
     */
    public void setAddPoint(boolean addPoint) {
        this.addPoint = addPoint;
    }

    /**
     * method which allowed to select point on map with a click
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        if (map.getTour() != null) {
            Point p = e.getPoint();


            if (addPoint) {


                controller.addNewRequest((int) p.getX(), (int) p.getY());

            }

/*
            float latitudeDep = map.getReq().getDepot().getLatitudeSurPanel();
            float longiteDep = map.getReq().getDepot().getLongitudeSurPanel();
            // System.out.println("Latitude "+latitudeDep);
            //System.out.println("Longitude : "+longiteDep);
            if (p.getX() < latitudeDep + 15 && p.getX() > latitudeDep - 15 && p.getY() < longiteDep + 20 && p.getY() > longiteDep - 20) {
                // System.out.println("je suis dans ce coordonée");
                controller.highLight(map.getReq().getDepot().getId());
            }
*/

              ArrayList<Model.Point> pointsDef= map.getTour().getPointsDef();

              for(Model.Point point : pointsDef){
                  float latitude = point.getLatitudeOnPanel();
                  float longitude = point.getLongitudeOnPanel();
                  if (p.getX() < latitude + 30 && p.getX() > latitude - 30 && p.getY() < longitude + 30 && p.getY() > longitude - 30) {
                      controller.highLight(point.getId());

                  }
            }

           /* //Parcourir les point de la requete et comparer les coordonées du point cliqué pour voir si elle corespond
            for (String s : listePoint.keySet()) {
                float latitude = listePoint.get(s).getLatitudeSurPanel();
                float longitude = listePoint.get(s).getLongitudeSurPanel();


                if (p.getX() < latitude + 30 && p.getX() > latitude - 30 && p.getY() < longitude + 30 && p.getY() > longitude - 30) {
                    controller.highLight(s);

                }
            }*/
        }
    }
}








