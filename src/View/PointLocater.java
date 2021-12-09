package View;


import Controller.Controller;

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.*;
import java.util.*;
import java.util.List;
import javax.swing.*;
import java.util.HashMap;

public class PointLocater extends MouseAdapter {

    private Map map;
    private Controller controller;
    private Point pcurent;
    private boolean addPoint;


    public PointLocater(Map map1, Controller controller) {
        this.controller = controller;
        map = map1;
        addPoint = false;

    }

    public Point getPcurent() {
        return pcurent;
    }

    public boolean isAddPoint() {
        return addPoint;
    }

    public void setAddPoint(boolean addPoint) {
        this.addPoint = addPoint;
    }

    public void mousePressed(MouseEvent e) {
        if (map.getReq() != null) {
            Point p = e.getPoint();

            if (addPoint) {


                controller.addNewRequest((int) p.getX(), (int) p.getY());

            }


            float latitudeDep = map.getReq().getDepot().getLatitudeSurPanel();
            float longiteDep = map.getReq().getDepot().getLongitudeSurPanel();
            // System.out.println("Latitude "+latitudeDep);
            //System.out.println("Longitude : "+longiteDep);
            if (p.getX() < latitudeDep + 15 && p.getX() > latitudeDep - 15 && p.getY() < longiteDep + 20 && p.getY() > longiteDep - 20) {
                // System.out.println("je suis dans ce coordonée");
                controller.highLight(map.getReq().getDepot().getId());
            }

            HashMap<String, Model.Point> listePoint = map.getReq().getListePoint();


            //Parcourir les point de la requete et comparer les coordonées du point cliqué pour voir si elle corespond
            for (String s : listePoint.keySet()) {
                float latitude = listePoint.get(s).getLatitudeSurPanel();
                float longitude = listePoint.get(s).getLongitudeSurPanel();


                if (p.getX() < latitude + 30 && p.getX() > latitude - 30 && p.getY() < longitude + 30 && p.getY() > longitude - 30) {
                    controller.highLight(s);
                }
            }
        }
    }
}








