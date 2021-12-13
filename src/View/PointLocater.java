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
     * setter of the boolean addPoint which manage if a point can be added
     * @param addPoint
     */
    public void setAddPoint(boolean addPoint) {
        this.addPoint = addPoint;
    }

    /**
     * method which allowed the selection of point on map with a click
     * @param e
     */
    public void mousePressed(MouseEvent e) {
        if (map.getTour() != null) {
            Point p = e.getPoint();

            if (addPoint) {
                controller.addNewRequest((int) p.getX(), (int) p.getY());
            }

              ArrayList<Model.Point> pointsDef= map.getTour().getPointsDef();

              for(Model.Point point : pointsDef){
                  float latitude = point.getLatitudeOnPanel();
                  float longitude = point.getLongitudeOnPanel();
                  if (p.getX() < latitude + 30 && p.getX() > latitude - 30 && p.getY() < longitude + 30 && p.getY() > longitude - 30) {
                      controller.highLight(point.getId());

                  }
            }
        }
    }
}








