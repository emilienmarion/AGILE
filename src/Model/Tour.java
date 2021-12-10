package Model;

import Controller.Controller;
import Utils.Algorithm;
import Utils.Array;
import Utils.XmlUtils;
import View.MapView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;


public class Tour
{
    private ArrayList<Point> pointsDef; // Final list of points composing the tour
    private ArrayList<Path> pathPointsDef; // Pathes from the pointDef list
    private HashMap<String, Point> points; // Primary points from the XML request
    private ArrayList<ArrayList<Date>> freeSchedule; // List of time slot available for addition or modification
    private Date departureTime; // Of the final tour
    private Date arrivalTime; // Of the final tour

    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private MapView mapView;
    private Request req;
    private Graph graph;
    private Controller controller;


    public Tour(MapView mv,Controller controller){
        this.mapView = mv;
        this.controller=controller;
        pointsDef = new ArrayList<Point>();
        pathPointsDef = new ArrayList<>();
        freeSchedule = new ArrayList<>();
    }

    public void loadNewRequest(Request r) throws ParseException {
        System.out.println("Tour.CONSTRUCTOR");
        this.req = r;
        this.points = req.getListePoint();
        points.put(req.getDepot().getId(),req.getDepot());
        computeTheFinalPointList();
    }


    public ArrayList<Point> getPointsDef() {
        return this.pointsDef;
    }

    public void setPointsDef(ArrayList<Point> pointsDef) {
        this.pointsDef = pointsDef;
    }

    public String getDepartureTime() {
        String ret = Integer.toString(departureTime.getHours()) + ":" + Integer.toString(departureTime.getMinutes())+":"+Integer.toString(departureTime.getSeconds());
        return ret;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        String ret = Integer.toString(arrivalTime.getHours()) + ":" + Integer.toString(arrivalTime.getMinutes())+":"+Integer.toString(arrivalTime.getSeconds());
        return ret;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public String getTotalDuration() {
        int h1 = departureTime.getHours();
        int m1 = departureTime.getMinutes();
        int s1 = departureTime.getSeconds();
        int h2 = arrivalTime.getHours();
        int m2 = arrivalTime.getMinutes();
        int s2 = arrivalTime.getSeconds();
        return Integer.toString(h2-h1)+"h "+Integer.toString(m2-m1)+"min "+Integer.toString(s2-s1)+"s";
    }

    public void computeTheFinalPointList() throws ParseException {
        System.out.println("Tour.computeTheFinalPointList");

        this.graph = Algorithm.createGraph(this.points, this.mapView.getMap().getMapData(), this.req.getDepot());
        pathPointsDef = Algorithm.TSP(this.graph);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date currentDate = sdf.parse(req.getDepartureTime());
        int durationPrec;
        int compteur=0;
        for (Path p:pathPointsDef){
            Node n=p.getPath();
            if (compteur==0){
                Point depot=req.getDepot();
                Point depotToStock=new Point(depot);
                depotToStock.setId(depot.getId()+"start");
                depotToStock.setSchedule(currentDate);
                pointsDef.add(depotToStock);
            }
            String id = n.getIntersection().getId();
            Point current = req.getListePoint().get(id);
            //TODO : ici verfier que la duration c'est bien la prcedente parce que je te met ma main a couper que c'est l'actuel
            durationPrec = current.getDuration();
            current.setCostToReach(n.getCost());
            currentDate = XmlUtils.findSchedule(currentDate,current.getCostToReach(),durationPrec);
            current.setSchedule(currentDate);
            pointsDef.add(current);
            compteur++;
        }

        // Vérification of the pointDefs ArrayList

        for (Point p: pointsDef){
            System.out.print(p.getId());
            System.out.print("-->");
            System.out.println(p.getCostToReach()*3.6/15);
            System.out.println("schedule : " + p.getSchedule());
        }

        this.departureTime = this.pointsDef.get(0).getSchedule();
        System.out.println("DEPARTURE : "+ this.departureTime);
        this.arrivalTime = this.pointsDef.get(pointsDef.size()-1).getSchedule();
        System.out.println("arrival : "+ this.arrivalTime);
    }


    public void deletePoint(String idPoint){
        System.out.println("Tour.deletePoint");

        Point firstToDelete = null;
        Point secondToDelete = null;

        for(Point p : pointsDef){
            if(p.getId().equals(idPoint)){
                System.out.println("Tour.pointsDef.deletePoint : "+ p.getId());
                firstToDelete = p;
            }else if(!(p.getId().equals(pointsDef.get(pointsDef.size()-1).getId())) && !(p.getId().equals(pointsDef.get(0).getId()))) {
                if(p.getIdAssociated().equals(idPoint)){
                    System.out.println("Tour.pointsDef.deletePoint : "+ p.getId());
                    secondToDelete = p;
                }
            }
        }

        boolean changeArrival = false;
        if(firstToDelete!=null && secondToDelete!=null) {

            // Detect the case where the arrival schedule must change
            System.out.println(" First point to delete  : " + firstToDelete.getId());
            System.out.println(" Second point to delete : " + secondToDelete.getId());
            String actualLastPointId = pointsDef.get(pointsDef.size()-2).getId();
            System.out.println(" Actual last point      : " + actualLastPointId);
            if(firstToDelete.getId().equals(actualLastPointId)
                    || secondToDelete.getId().equals(actualLastPointId)){
                changeArrival = true;
                System.out.println("Tour.deletePoint : Point deleted just before the returnal deposit");
            }

            // Free the schedule for avalaible new schedule in case of modification or addition
            // for the first point to delete

            ArrayList<Date> couple = new ArrayList<>();
            // Add the departure time from the point just before the deleted one
            int position1 = pointsDef.indexOf(firstToDelete);
            Date leavingDate = pointsDef.get(position1 - 1).getSchedule();
            couple.add(leavingDate);
            // Add the expected arrival time to the point just after the deleted one (except if it's the entrepot)
            Date arrivalDate = pointsDef.get(position1 + 1).getSchedule();
            couple.add(arrivalDate);
            freeSchedule.add(couple);
            System.out.println("Tour : new free schedule from " + leavingDate + " to " + arrivalDate);

            // For the second point to delete (associated)
            ArrayList<Date> couple2 = new ArrayList<>();
            // Add the departure time from the point just before the deleted one
            int position2 = pointsDef.indexOf(secondToDelete);
            Date leavingDate2 = pointsDef.get(position2 - 1).getSchedule();
            couple2.add(leavingDate2);
            // Add the expected arrival time to the point just after the deleted one (except if it's the entrepot)
            Date arrivalDate2 = pointsDef.get(position2 + 1).getSchedule();
            couple2.add(arrivalDate2);
            freeSchedule.add(couple2);
            System.out.println("Tour : new free schedule from " + leavingDate2 + " to " + arrivalDate2);

            // And delete on the data structure, recompute the graph
            computePathDeletedPoint(firstToDelete);
            pointsDef.remove(firstToDelete);
            computePathDeletedPoint(secondToDelete);
            pointsDef.remove(secondToDelete);
            glueFreeScheduleList();
        }


        // Change the arrival time
        try {
            if (changeArrival) {
                Point lastDepot = pointsDef.get(pointsDef.size() - 1);
                Point previousPoint = pointsDef.get(pointsDef.size() - 2);
                // TODO : calculer le travelling time à partir du point précédent
                int travellingTime = 0;
                int newCost = previousPoint.getDuration() + travellingTime;
                lastDepot.setCostToReach(newCost);
                Date newDate = XmlUtils.findSchedule(previousPoint.getSchedule(), lastDepot.getCostToReach(), previousPoint.getDuration());
                lastDepot.setSchedule(newDate);
                this.arrivalTime = newDate;
            }
        }catch (Exception e){
            System.out.println("Tour.deletePoint ERROR : "+e);
        }

    }

    private void computePathDeletedPoint(Point p){

        int actualIndex = getIndexPointById(p.getId());
        Point base = pointsDef.get(actualIndex-1);
        Point target = pointsDef.get(actualIndex+1);
        System.out.println("Actual : "+ actualIndex+" | base : "+base.getId()+" | target : "+target.getId());
        String baseId=base.getId();
        if (actualIndex==1) baseId=baseId.substring(0,baseId.length()-5);
        //calculechemin(base, target);
        HashMap<String,Integer> tableIndex=graph.getTableIndex();
        System.out.println(tableIndex);
        System.out.println(baseId);
        int indexBase = tableIndex.get(baseId); //index dans la matrice
        int indexTarget = tableIndex.get(target.getId()); //pareil
        int indexCurrent = tableIndex.get(p.getId()); //pareil
        //shortcut between base and target
        Path shortcutPath = graph.getContent().get(indexBase).get(indexTarget).getAssociatedPath();
        pathPointsDef.remove(actualIndex-1);
        pathPointsDef.remove(actualIndex-1);
        pathPointsDef.add(actualIndex-1,shortcutPath);
        graph.setSolution(pathPointsDef);
        /*ArrayList<Path> tempSolution = graph.getSolution();
        // Trouver le path d'un point
        tempSolution.remove(indexCurrent);*/



    }

    /**
     *  Check if in the free time list, a range is separated in two and glue them together
     */
    private void glueFreeScheduleList(){
        ArrayList<ArrayList<Date>> coupleToDelete = new ArrayList<>();
        ArrayList<Date> newCouple = new ArrayList<>();
        Date currentDeparture = new Date();
        Date currentArrival = new Date();
        Date nextDeparture = new Date();
        Date nextArrival = new Date();

        // Display the initial free ranges
        freeSchedule.forEach((couple) -> {
            System.out.println(" initial FREE couple : " + couple.get(0) + " to " + couple.get(1));
        });

        if(freeSchedule.size()>2) {
            int limit = freeSchedule.size();
            System.out.println("FreeScheduleList size : "+freeSchedule.size() );
            for (int i = 0; i < limit-1; i++) {
                // One range is across the other
                currentDeparture = freeSchedule.get(i).get(0);
                currentArrival = freeSchedule.get(i).get(1);
                nextDeparture = freeSchedule.get(i + 1).get(0);
                nextArrival = freeSchedule.get(i + 1).get(1);

                System.out.println("GLUE line " + i);
                System.out.println("      current Departure " + currentDeparture);
                System.out.println("      current Arrival   " + currentArrival);
                System.out.println("      next Departure    " + nextDeparture);
                System.out.println("      next Arrival      " + nextArrival);


                if ((currentArrival.getHours() > nextDeparture.getHours()
                        || currentArrival.getHours() >= nextDeparture.getHours()
                        && currentArrival.getMinutes() > nextDeparture.getMinutes()
                        ||currentArrival.getHours() >= nextDeparture.getHours()
                        && currentArrival.getMinutes() >= nextDeparture.getMinutes()
                        && currentArrival.getSeconds() >= nextDeparture.getSeconds())
                        &&(nextDeparture.getHours() > currentDeparture.getHours()
                        || nextDeparture.getHours() >= currentDeparture.getHours()
                        && nextDeparture.getMinutes() > currentDeparture.getMinutes()
                        ||nextDeparture.getHours() >= currentDeparture.getHours()
                        && nextDeparture.getMinutes() >= currentDeparture.getMinutes()
                        && nextDeparture.getSeconds() >= currentDeparture.getSeconds())) {
                    System.out.println("a1 > d2");
                    newCouple.clear();
                    newCouple.add(freeSchedule.get(i).get(0));
                    newCouple.add(freeSchedule.get(i + 1).get(1));
                    coupleToDelete.add(freeSchedule.get(i));
                    coupleToDelete.add(freeSchedule.get(i + 1));
                    freeSchedule.add(newCouple);
                } else if ((currentDeparture.getHours() < nextArrival.getHours()
                        || currentDeparture.getHours() <= nextArrival.getHours()
                        && currentDeparture.getMinutes() < nextArrival.getMinutes()
                        ||currentDeparture.getHours() <= nextArrival.getHours()
                        && currentDeparture.getMinutes() <= nextArrival.getMinutes()
                        && currentDeparture.getSeconds() <= nextArrival.getSeconds())
                        &&(nextDeparture.getHours() < currentDeparture.getHours()
                        || nextDeparture.getHours() <= currentDeparture.getHours()
                        && nextDeparture.getMinutes() < currentDeparture.getMinutes()
                        ||nextDeparture.getHours() <= currentDeparture.getHours()
                        && nextDeparture.getMinutes() <= currentDeparture.getMinutes()
                        && nextDeparture.getSeconds() <= currentDeparture.getSeconds())) {
                    System.out.println("d1 < a2");
                    newCouple.clear();
                    newCouple.add(freeSchedule.get(i + 1).get(0));
                    newCouple.add(freeSchedule.get(i).get(1));
                    coupleToDelete.add(freeSchedule.get(i));
                    coupleToDelete.add(freeSchedule.get(i + 1));
                    freeSchedule.add(newCouple);
                }
            }
        } else if (freeSchedule.size()>1) {
            // One range is across the other
            currentDeparture = freeSchedule.get(0).get(0);
            currentArrival = freeSchedule.get(0).get(1);
            nextDeparture = freeSchedule.get(1).get(0);
            nextArrival = freeSchedule.get(1).get(1);

            System.out.println("GLUE line " + 0);
            System.out.println("      current Departure " + currentDeparture);
            System.out.println("      current Arrival   " + currentArrival);
            System.out.println("      next Departure    " + nextDeparture);
            System.out.println("      next Arrival      " + nextArrival);


            if ((currentArrival.getHours() > nextDeparture.getHours()
                || currentArrival.getHours() >= nextDeparture.getHours()
                    && currentArrival.getMinutes() > nextDeparture.getMinutes()
                ||currentArrival.getHours() >= nextDeparture.getHours()
                    && currentArrival.getMinutes() >= nextDeparture.getMinutes()
                    && currentArrival.getSeconds() >= nextDeparture.getSeconds())
            &&(nextDeparture.getHours() > currentDeparture.getHours()
                    || nextDeparture.getHours() >= currentDeparture.getHours()
                    && nextDeparture.getMinutes() > currentDeparture.getMinutes()
                    ||nextDeparture.getHours() >= currentDeparture.getHours()
                    && nextDeparture.getMinutes() >= currentDeparture.getMinutes()
                    && nextDeparture.getSeconds() >= currentDeparture.getSeconds())) {
                System.out.println("d1 < d2 < a1");
                newCouple.add(freeSchedule.get(0).get(0));
                newCouple.add(freeSchedule.get(1).get(1));
                coupleToDelete.add(freeSchedule.get(0));
                coupleToDelete.add(freeSchedule.get(1));
                freeSchedule.add(newCouple);
            } else if ((currentDeparture.getHours() < nextArrival.getHours()
                || currentDeparture.getHours() <= nextArrival.getHours()
                    && currentDeparture.getMinutes() < nextArrival.getMinutes()
                ||currentDeparture.getHours() <= nextArrival.getHours()
                    && currentDeparture.getMinutes() <= nextArrival.getMinutes()
                    && currentDeparture.getSeconds() <= nextArrival.getSeconds())
            &&(nextDeparture.getHours() < currentDeparture.getHours()
                    || nextDeparture.getHours() <= currentDeparture.getHours()
                    && nextDeparture.getMinutes() < currentDeparture.getMinutes()
                    ||nextDeparture.getHours() <= currentDeparture.getHours()
                    && nextDeparture.getMinutes() <= currentDeparture.getMinutes()
                    && nextDeparture.getSeconds() <= currentDeparture.getSeconds())) {
                System.out.println("d2< d1 < a2");
                newCouple.add(freeSchedule.get(1).get(0));
                newCouple.add(freeSchedule.get(0).get(1));
                coupleToDelete.add(freeSchedule.get(0));
                coupleToDelete.add(freeSchedule.get(1));
                freeSchedule.add(newCouple);
            }
        }

        // Delete useless ones
        for (ArrayList<Date> toDelete : coupleToDelete) {
            if(freeSchedule.indexOf(toDelete) != -1)
                freeSchedule.remove(freeSchedule.indexOf(toDelete));
        }
        coupleToDelete.clear();

        // Display the final free ranges
        freeSchedule.forEach((couple) -> {
            System.out.println(" FREE couple : " + couple.get(0) + " to " + couple.get(1));
        });
    }

private int getIndexPointById(String idPoint){
    boolean flag=true;
    int index=-1;
    while (flag){
        index++;
        if (pointsDef.get(index).getId().equals(idPoint)) flag=false;
    }
    return index;
}
private void displayArray(ArrayList<Point> ap){
    for (Point p:ap){
        System.out.print(p.getId());
        System.out.print(" -> ");
        System.out.print(p.getCostToReach());
        System.out.print(", ");
        System.out.println(p.getSchedule());
    }
}
    public void editPoint(String idPoint, String nvSchedule) throws ParseException {
        System.out.println("Tour.editPoint");
        displayArray(pointsDef);
        //recuperer notre point cible
        int index=getIndexPointById(idPoint);
        Point target=pointsDef.get(index);
        System.out.print("TARGET=");
        System.out.println(target);
        //recuperer le dernier point avant le depot
        Point lastest=pointsDef.get(pointsDef.size()-2);
        HashMap<String, Integer> tableIndex=graph.getTableIndex();
        int indexLastest=tableIndex.get(lastest.getId());
        int indexTarget=tableIndex.get(target.getId());
        Path pathBetweenTargetLastest=graph.getContent().get(indexLastest).get(indexTarget).getAssociatedPath();
        //verifier que l'horaire correspond cad si du dernier on peu aller a la target dans le temps imparti
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        float costTargetLastest=pathBetweenTargetLastest.getPath().getCost();
        Date dateToCompare = XmlUtils.findSchedule(lastest.getSchedule(), pathBetweenTargetLastest.getPath().getCost(), lastest.getDuration());
        Date newSchedule = sdf.parse(nvSchedule);
        boolean dateCheck = newSchedule.after(dateToCompare);
        if (dateCheck){
            Point newLastest=null;
            Date newLastestSchedule=null;
            System.out.println("Modif!!!!");
            pointsDef.remove(index);
            target.setCostToReach(costTargetLastest);
            target.setSchedule(newSchedule);
            pointsDef.add(pointsDef.size()-1,target);
            newLastest=target;
            newLastestSchedule=newSchedule;
            if (target.getType().equals("pickUp")){
                //si c'est un pick up on s'occupe du delivery associé
                String idTargetDelivery=target.getIdAssociated();
                int indexDelivery=getIndexPointById(idTargetDelivery);
                Point delivery=pointsDef.get(indexDelivery);
                int indexTargetDelivery=tableIndex.get(idTargetDelivery);
                Path pathBetweenTargetDelivery=graph.getContent().get(indexTarget).get(indexTargetDelivery).getAssociatedPath();
                float costTargetDelivery=pathBetweenTargetDelivery.getPath().getCost();
                Date newScheduleDelivery = XmlUtils.findSchedule(newSchedule, costTargetDelivery, 0);
                pointsDef.remove(indexDelivery);
                delivery.setSchedule(newScheduleDelivery);
                delivery.setCostToReach(costTargetDelivery);
                pointsDef.add(pointsDef.size()-1,delivery);
                newLastest=delivery;
                newLastestSchedule=newScheduleDelivery;
            }
            Point depot = pointsDef.get(pointsDef.size()-1);
            int indexDepot=tableIndex.get(depot.getId());
            int indexNewLastest=tableIndex.get(newLastest.getId());

            Path pathBetweenNewLastestDepot = graph.getContent().get(indexNewLastest).get(indexDepot).getAssociatedPath();
            float costNewLastestDepot=pathBetweenNewLastestDepot.getPath().getCost();
            Date newDepotSchedule = XmlUtils.findSchedule(newLastestSchedule, costNewLastestDepot, 0);
            depot.setCostToReach(costNewLastestDepot);
            depot.setSchedule(newDepotSchedule);
            this.arrivalTime = newDepotSchedule;
        }
        displayArray(pointsDef);
    }


    public Graph getGraph() {
        return graph;
    }

    public HashMap<String, Point> getPoints() {
        return points;
    }


    public ArrayList<Path> getPathPointsDef() {
        return pathPointsDef;
}

    public void addRequest(Point pickUp, Point delivery) {
        System.out.println("je suis dans Tour.addRequest ");

        calculechemin(pickUp,delivery);


    }

    public void calculechemin(Point pickUp,Point delivery){
        System.out.print("pu=");
        System.out.println(pickUp);
        Point pointPrecedent=pointsDef.get(pointsDef.size()-2);
        Point FinaleDepot=pointsDef.get(pointsDef.size()-1);

        HashMap<String, Node> result1 = Algorithm.dijkstra(mapView.getMapData().getIntersections(), pointPrecedent);
        Path p1=Algorithm.getPath(result1.get(pickUp.getId()));

        HashMap<String, Node> result2 = Algorithm.dijkstra(mapView.getMapData().getIntersections(), pickUp);
        Path p2=Algorithm.getPath(result2.get(delivery.getId()));

        System.out.println(pointsDef.get(pointsDef.size()-1));
        HashMap<String, Node> result3 = Algorithm.dijkstra(mapView.getMapData().getIntersections(), delivery);


        Path p3=Algorithm.getPath(result3.get(pointsDef.get(pointsDef.size()-1).getId()));

        Date datePickup=null;
        Date dateDelivery=null;
        Date finalDepot=null;
        try {
         datePickup=  XmlUtils.findSchedule(pointPrecedent.getSchedule(),p1.getPath().getCost(),0);
            dateDelivery=  XmlUtils.findSchedule(datePickup,p2.getPath().getCost(),0);
            finalDepot=  XmlUtils.findSchedule(dateDelivery,p3.getPath().getCost(),0);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pickUp.setSchedule(datePickup);
        delivery.setSchedule(dateDelivery);
        FinaleDepot.setSchedule(finalDepot);
        this.arrivalTime = finalDepot;

        pathPointsDef.remove(pathPointsDef.size()-1);
        pathPointsDef.add(p1);
        pathPointsDef.add(p2);
        pathPointsDef.add(p3);

        pointsDef.add(pointsDef.size()-1, pickUp);
        pointsDef.add(pointsDef.size()-1,delivery);

        this.graph.getListePoint().put(pickUp.getId(),pickUp);
        this.graph.getListePoint().put(delivery.getId(),delivery);

        this.graph.setSolution(pathPointsDef);


       // controller.refreshMap();

       // this.mapView.getMap().setGraph(this.graph);
       // this.mapView.getMap().repaint();

    }
}
