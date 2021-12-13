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


public class Tour {
    private ArrayList<Point> pointsDef; // Final list of points composing the tour
    private ArrayList<Path> pathPointsDef; // Pathes from the pointDef list
    private HashMap<String, Point> points; // Primary points from the XML request
    private final ArrayList<ArrayList<Date>> freeSchedule; // List of time slot available for addition or modification
    private Date departureTime; // Of the final tour
    private Date arrivalTime; // Of the final tour

    private final SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private final MapView mapView;
    private Request req;
    private Graph graph;
    private final Controller controller;

    /**
     * This is the constructor of the class Tour which  stores the tour information
     *
     * @param mv
     * @param controller
     */
    public Tour(MapView mv, Controller controller) {
        this.mapView = mv;
        this.controller = controller;
        pointsDef = new ArrayList<Point>();
        pathPointsDef = new ArrayList<>();
        freeSchedule = new ArrayList<>();
    }

    /**
     * Set somme attributes from the information in a request
     *
     * @param r
     * @throws ParseException
     */
    public void loadNewRequest(Request r) throws ParseException {
        System.out.println("Tour.CONSTRUCTOR");
        if (points != null) this.points.clear();
        if (pointsDef != null) this.pointsDef.clear();
        if (pathPointsDef != null) this.pathPointsDef.clear();
        this.req = r;
        this.points = req.getListePoint();
        points.put(req.getDepot().getId(), req.getDepot());
        computeTheFinalPointList();
    }

    /**
     * Getteur of the list of the final Point
     *
     * @return ArrayList<Point>
     */
    public ArrayList<Point> getPointsDef() {
        return this.pointsDef;
    }

    /**
     * Setter of the list of the final Point
     *
     * @param pointsDef
     */
    public void setPointsDef(ArrayList<Point> pointsDef) {
        this.pointsDef = pointsDef;
    }

    /**
     * Getter of the Departure Time
     *
     * @return DepartureTime
     */
    public String getDepartureTime() {
        String ret = departureTime.getHours() + ":" + departureTime.getMinutes() + ":" + departureTime.getSeconds();
        return ret;
    }

    /**
     * Setter of the Departure Time
     *
     * @param departureTime
     */
    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    /**
     * Getter of the arrival Time
     *
     * @return ArrivalTime
     */
    public String getArrivalTime() {
        String ret = arrivalTime.getHours() + ":" + arrivalTime.getMinutes() + ":" + arrivalTime.getSeconds();
        return ret;
    }

    /**
     * Setter of the arrival Time
     *
     * @param arrivalTime
     */
    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    /**
     * Method which calculate the Total Duration of the Tour
     *
     * @return TotalDuration
     */
    public String getTotalDuration() {
        int h1 = departureTime.getHours();
        int m1 = departureTime.getMinutes();
        int s1 = departureTime.getSeconds();
        int h2 = arrivalTime.getHours();
        int m2 = arrivalTime.getMinutes();
        int s2 = arrivalTime.getSeconds();
        return h2 - h1 + "h " + (m2 - m1) + "min " + (s2 - s1) + "s";
    }

    /**
     * The big Method which allowed to calculate the final point List and the associated time for each Point
     *
     * @throws ParseException
     */
    public void computeTheFinalPointList() throws ParseException {
        System.out.println("Tour.computeTheFinalPointList");

        this.graph = Algorithm.createGraph(this.points, this.mapView.getMap().getMapData(), this.req.getDepot());
        pathPointsDef = Algorithm.TSP(this.graph);

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date currentDate = sdf.parse(req.getDepartureTime());
        int durationPrec;
        int compteur = 0;
        for (Path p : pathPointsDef) {
            Node n = p.getPath();
            if (compteur == 0) {
                Point depot = req.getDepot();
                Point depotToStock = new Point(depot);
                depotToStock.setId(depot.getId() + "start");
                depotToStock.setSchedule(currentDate);
                pointsDef.add(depotToStock);
            }
            String id = n.getIntersection().getId();
            Point current = req.getListePoint().get(id);
            //TODO : ici verfier que la duration c'est bien la prcedente parce que je te met ma main a couper que c'est l'actuel
            durationPrec = current.getDuration();
            current.setCostToReach(n.getCost());
            currentDate = XmlUtils.findSchedule(currentDate, current.getCostToReach(), durationPrec);
            current.setSchedule(currentDate);
            pointsDef.add(current);
            compteur++;
        }

        // Vérification of the pointDefs ArrayList

        for (Point p : pointsDef) {
            System.out.print(p.getId());
            System.out.print("-->");
            System.out.println(p.getCostToReach() * 3.6 / 15);
            System.out.println("schedule : " + p.getSchedule());
        }

        this.departureTime = this.pointsDef.get(0).getSchedule();
        System.out.println("DEPARTURE : " + this.departureTime);
        this.arrivalTime = this.pointsDef.get(pointsDef.size() - 1).getSchedule();
        System.out.println("arrival : " + this.arrivalTime);
    }

    /**
     * Delete a point fromme the final list
     *
     * @param idPoint
     */
    public void deletePoint(String idPoint) {
        System.out.println("Tour.deletePoint");

        Point firstToDelete = null;
        Point secondToDelete = null;

        for (Point p : pointsDef) {
            if (p.getId().equals(idPoint)) {
                System.out.println("Tour.pointsDef.deletePoint : " + p.getId());
                firstToDelete = p;
            } else if (!(p.getId().equals(pointsDef.get(pointsDef.size() - 1).getId())) && !(p.getId().equals(pointsDef.get(0).getId()))) {
                if (p.getIdAssociated().equals(idPoint)) {
                    System.out.println("Tour.pointsDef.deletePoint : " + p.getId());
                    secondToDelete = p;
                }
            }
        }

        boolean changeArrival = false;
        if (firstToDelete != null && secondToDelete != null) {

            // Detect the case where the arrival schedule must change
            System.out.println(" First point to delete  : " + firstToDelete.getId());
            System.out.println(" Second point to delete : " + secondToDelete.getId());
            String actualLastPointId = pointsDef.get(pointsDef.size() - 2).getId();
            System.out.println(" Actual last point      : " + actualLastPointId);
            if (firstToDelete.getId().equals(actualLastPointId)
                    || secondToDelete.getId().equals(actualLastPointId)) {
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
        } catch (Exception e) {
            System.out.println("Tour.deletePoint ERROR : " + e);
        }

    }

    /**
     * re-arranges the path after the supersession of a point
     *
     * @param p (Point)
     */
    private void computePathDeletedPoint(Point p) {

        int actualIndex = getIndexPointById(p.getId());
        Point base = pointsDef.get(actualIndex - 1);
        Point target = pointsDef.get(actualIndex + 1);
        System.out.println("Actual : " + actualIndex + " | base : " + base.getId() + " | target : " + target.getId());
        String baseId = base.getId();
        if (actualIndex == 1)
            baseId = baseId.substring(0, baseId.length() - 5); // Delete the "string" in the id for the first depot
        HashMap<String, Integer> tableIndex = graph.getTableIndex();
        System.out.println(tableIndex);
        System.out.println(baseId);
        int indexBase = tableIndex.get(baseId); //index dans la matrice
        int indexTarget = tableIndex.get(target.getId()); //pareil
        //shortcut between base and target
        Path shortcutPath = graph.getContent().get(indexBase).get(indexTarget).getAssociatedPath();
        pathPointsDef.remove(actualIndex - 1);
        pathPointsDef.remove(actualIndex - 1);
        pathPointsDef.add(actualIndex - 1, shortcutPath);
        graph.setSolution(pathPointsDef);
        /*ArrayList<Path> tempSolution = graph.getSolution();
        // Trouver le path d'un point
        tempSolution.remove(indexCurrent);*/


    }

    /**
     * Check if in the free time list, a range is separated in two and glue them together
     */
    private void glueFreeScheduleList() {
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

        if (freeSchedule.size() > 2) {
            int limit = freeSchedule.size();
            System.out.println("FreeScheduleList size : " + freeSchedule.size());
            for (int i = 0; i < limit - 1; i++) {
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
                        || currentArrival.getHours() >= nextDeparture.getHours()
                        && currentArrival.getMinutes() >= nextDeparture.getMinutes()
                        && currentArrival.getSeconds() >= nextDeparture.getSeconds())
                        && (nextDeparture.getHours() > currentDeparture.getHours()
                        || nextDeparture.getHours() >= currentDeparture.getHours()
                        && nextDeparture.getMinutes() > currentDeparture.getMinutes()
                        || nextDeparture.getHours() >= currentDeparture.getHours()
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
                        || currentDeparture.getHours() <= nextArrival.getHours()
                        && currentDeparture.getMinutes() <= nextArrival.getMinutes()
                        && currentDeparture.getSeconds() <= nextArrival.getSeconds())
                        && (nextDeparture.getHours() < currentDeparture.getHours()
                        || nextDeparture.getHours() <= currentDeparture.getHours()
                        && nextDeparture.getMinutes() < currentDeparture.getMinutes()
                        || nextDeparture.getHours() <= currentDeparture.getHours()
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
        } else if (freeSchedule.size() > 1) {
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
                    || currentArrival.getHours() >= nextDeparture.getHours()
                    && currentArrival.getMinutes() >= nextDeparture.getMinutes()
                    && currentArrival.getSeconds() >= nextDeparture.getSeconds())
                    && (nextDeparture.getHours() > currentDeparture.getHours()
                    || nextDeparture.getHours() >= currentDeparture.getHours()
                    && nextDeparture.getMinutes() > currentDeparture.getMinutes()
                    || nextDeparture.getHours() >= currentDeparture.getHours()
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
                    || currentDeparture.getHours() <= nextArrival.getHours()
                    && currentDeparture.getMinutes() <= nextArrival.getMinutes()
                    && currentDeparture.getSeconds() <= nextArrival.getSeconds())
                    && (nextDeparture.getHours() < currentDeparture.getHours()
                    || nextDeparture.getHours() <= currentDeparture.getHours()
                    && nextDeparture.getMinutes() < currentDeparture.getMinutes()
                    || nextDeparture.getHours() <= currentDeparture.getHours()
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
            if (freeSchedule.indexOf(toDelete) != -1)
                freeSchedule.remove(toDelete);
        }
        coupleToDelete.clear();

        // Display the final free ranges
        freeSchedule.forEach((couple) -> {
            System.out.println(" FREE couple : " + couple.get(0) + " to " + couple.get(1));
        });
    }

    /**
     * get the id in the final list of one point
     *
     * @param idPoint
     * @return idPoint in the list
     */
    private int getIndexPointById(String idPoint) {
        boolean flag = true;
        int index = -1;
        while (flag) {
            index++;
            if (pointsDef.get(index).getId().equals(idPoint)) flag = false;
        }
        return index;
    }

    /**
     * A method to display to help us during development
     *
     * @param ap
     */
    private void displayArrayPoint(ArrayList<Point> ap) {
        for (Point p : ap) {
            System.out.print(p.getId());
            System.out.print(" -> ");
            System.out.print(p.getCostToReach());
            System.out.print(", ");
            System.out.println(p.getSchedule());
        }
    }


    /**
     * A method to display to help us during development
     *
     * @param ap
     */
    private void displayArrayPath(ArrayList<Path> ap) {
        for (Path p : ap) {
            System.out.print(p.getId());
            System.out.print(" -> ");
            System.out.println(p.getPath().getCost());
        }
    }

    /**
     * This methods is use to edit a Point in the List ( Change the date )
     *
     * @param idPoint
     * @param nvSchedule
     * @param undo
     * @throws ParseException
     */
    public void editPoint(String idPoint, String nvSchedule, boolean undo) throws ParseException {
        System.out.println("Tour.editPoint");
        //recuperer notre point cible
        int index = getIndexPointById(idPoint);
        Point target = pointsDef.get(index);
        System.out.print("TARGET=");
        System.out.println(target);
        //recuperer le dernier point avant le depot
        Point lastest = pointsDef.get(pointsDef.size() - 2);
        HashMap<String, Integer> tableIndex = graph.getTableIndex();
        int indexLastest = tableIndex.get(lastest.getId()); //index dans la matrice des arcs du dernier sommet
        int indexTarget = tableIndex.get(target.getId()); //index dans la matrice des arcs de la cible
        //path entre le dernier et la target (on le prend dans la matrice)
        Path pathBetweenTargetLastest = graph.getContent().get(indexLastest).get(indexTarget).getAssociatedPath();//<---
        //verifier que l'horaire correspond cad si du dernier on peu aller a la target dans le temps imparti
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        float costTargetLastest = pathBetweenTargetLastest.getPath().getCost();
        Date dateToCompare = XmlUtils.findSchedule(lastest.getSchedule(), pathBetweenTargetLastest.getPath().getCost(), lastest.getDuration());
        System.out.println(nvSchedule);
        Date newSchedule = sdf.parse(nvSchedule);

        boolean dateCheck = newSchedule.after(dateToCompare);

        if (dateCheck || undo) {
            //dans le cas ou l'horaire correspond
            //variable qui permet de contenir dans tous les cas le delivery du couple (vu que on va les mettres a la fin)

            Point newLastest = null;
            Date newLastestSchedule = null;
            System.out.println("Tour.editPoint : Modif!!!!");
            //avoir les points autours de notre point cible
            Point targetBefore = pointsDef.get(index - 1);
            Point targetAfter = pointsDef.get(index + 1);
            String idTargetBefore = targetBefore.getId();

            if (index == 1) {
                idTargetBefore = idTargetBefore.substring(0, idTargetBefore.length() - 5); //If it's the first depot
            }

            int indexTargetBefore = tableIndex.get(idTargetBefore);
            int indexTargetAfter = tableIndex.get(targetAfter.getId());
            Path pathBeforeAfter = graph.getContent().get(indexTargetBefore).get(indexTargetAfter).getAssociatedPath();
            float costBeforeAfter = pathBeforeAfter.getPath().getCost();
            System.out.println("costBeforeAfter = " + costBeforeAfter);

            targetAfter.setCostToReach(costBeforeAfter);
            System.out.println(targetAfter);
            System.out.println(pointsDef.get(index + 1));
            pointsDef.remove(index); //on enleve la cible de pointsDef
            pathPointsDef.remove(index - 1);
            pathPointsDef.remove(index - 1);
            target.setCostToReach(costTargetLastest); //on change le cout pour arriver a la cible que l'on va placer a la fin
            target.setSchedule(newSchedule); //on change l'heure aussi
            pointsDef.add(pointsDef.size() - 1, target); //on ajoute a la fin juste devant le depot
            pathPointsDef.add(index - 1, pathBeforeAfter); //path entre les deux
            pathPointsDef.add(pathPointsDef.size() - 1, pathBetweenTargetLastest); //entre le dernier et la cible
            newLastest = target;
            newLastestSchedule = newSchedule;
            if (target.getType().equals("pickUp")) {
                //si c'est un pick up on s'occupe du delivery associé
                String idTargetDelivery = target.getIdAssociated(); //get l'id du delivery associé a la cible
                int indexDelivery = getIndexPointById(idTargetDelivery); //avoir l'index de la cible dans pointsDef
                Point delivery = pointsDef.get(indexDelivery); //get le devivery associé
                int indexTargetDelivery = tableIndex.get(idTargetDelivery); //obtenir l'index dans la matrice des arcs pour le delivery
                //path entre la target et la delivery associée
                Path pathBetweenTargetDelivery = graph.getContent().get(indexTarget).get(indexTargetDelivery).getAssociatedPath();
                //cout de ce trajet
                float costTargetDelivery = pathBetweenTargetDelivery.getPath().getCost();
                //on en deduit le nouvel horaire
                Date newScheduleDelivery = XmlUtils.findSchedule(newSchedule, costTargetDelivery, 0);
                //on enleve le delivery
                //avoir les points autours de notre point cible
                Point deliveryBefore = pointsDef.get(indexDelivery - 1);
                Point deliveryAfter = pointsDef.get(indexDelivery + 1);
                int indexDeliveryBefore = tableIndex.get(deliveryBefore.getId());
                int indexDeliveryAfter = tableIndex.get(deliveryAfter.getId());
                Path pathBeforeAfterDelivery = graph.getContent().get(indexDeliveryBefore).get(indexDeliveryAfter).getAssociatedPath();
                float costBeforeAfterDelivery = pathBeforeAfterDelivery.getPath().getCost();
                deliveryAfter.setCostToReach(costBeforeAfterDelivery);
                pointsDef.remove(indexDelivery);
                pathPointsDef.remove(indexDelivery - 1); // on enleve le delivery
                pathPointsDef.remove(indexDelivery - 1); // pareil
                //on set les changements dans le point
                delivery.setSchedule(newScheduleDelivery);
                delivery.setCostToReach(costTargetDelivery);
                //on le place juste avant le depot
                pointsDef.add(pointsDef.size() - 1, delivery);
                pathPointsDef.add(indexDelivery - 1, pathBeforeAfterDelivery);
                pathPointsDef.add(pathPointsDef.size() - 1, pathBetweenTargetDelivery);
                newLastest = delivery;
                newLastestSchedule = newScheduleDelivery;
            }
            //partie relative au depot a la fin
            Point depot = pointsDef.get(pointsDef.size() - 1);
            int indexDepot = tableIndex.get(depot.getId());
            int indexNewLastest = tableIndex.get(newLastest.getId());
            pathPointsDef.remove(pathPointsDef.size() - 1);
            Path pathBetweenNewLastestDepot = graph.getContent().get(indexNewLastest).get(indexDepot).getAssociatedPath();
            pathPointsDef.add(pathBetweenNewLastestDepot);
            float costNewLastestDepot = pathBetweenNewLastestDepot.getPath().getCost();
            Date newDepotSchedule = XmlUtils.findSchedule(newLastestSchedule, costNewLastestDepot, 0);
            depot.setCostToReach(costNewLastestDepot);
            depot.setSchedule(newDepotSchedule);
            this.arrivalTime = newDepotSchedule;
        }

        graph.setSolution(pathPointsDef);
    }

    /**
     * getter for the Graph
     *
     * @return graph
     */
    public Graph getGraph() {
        return graph;
    }

    /**
     * getter
     *
     * @return points (HashMap<String, Point>)
     */
    public HashMap<String, Point> getPoints() {
        return points;
    }

    /**
     * getter of the paths list
     *
     * @return
     */
    public ArrayList<Path> getPathPointsDef() {
        return pathPointsDef;
    }

    /**
     * This methods add a Point choose by the user in the fianl Point List with the good time
     *
     * @param pickUp
     * @param delivery
     */
    public void addRequest(Point pickUp, Point delivery) {
        System.out.println("je suis dans Tour.addRequest ");
        calculechemin(pickUp, delivery);
    }

    /**
     * This methods calculate the path and the time associated to a new Couple of point and add it to lists
     *
     * @param pickUp
     * @param delivery
     */
    public void calculechemin(Point pickUp, Point delivery) {
        System.out.print("pu=");
        System.out.println(pickUp);
        Point pointPrecedent = pointsDef.get(pointsDef.size() - 2);
        Point FinaleDepot = pointsDef.get(pointsDef.size() - 1);

        HashMap<String, Node> result1 = Algorithm.dijkstra(mapView.getMapData().getIntersections(), pointPrecedent);
        Path p1 = Algorithm.getPath(result1.get(pickUp.getId()));

        HashMap<String, Node> result2 = Algorithm.dijkstra(mapView.getMapData().getIntersections(), pickUp);
        Path p2 = Algorithm.getPath(result2.get(delivery.getId()));

        System.out.println(pointsDef.get(pointsDef.size() - 1));
        HashMap<String, Node> result3 = Algorithm.dijkstra(mapView.getMapData().getIntersections(), delivery);


        Path p3 = Algorithm.getPath(result3.get(pointsDef.get(pointsDef.size() - 1).getId()));

        Date datePickup = null;
        Date dateDelivery = null;
        Date finalDepot = null;
        try {
            datePickup = XmlUtils.findSchedule(pointPrecedent.getSchedule(), p1.getPath().getCost(), 0);
            dateDelivery = XmlUtils.findSchedule(datePickup, p2.getPath().getCost(), 0);
            finalDepot = XmlUtils.findSchedule(dateDelivery, p3.getPath().getCost(), 0);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        pickUp.setSchedule(datePickup);
        delivery.setSchedule(dateDelivery);
        FinaleDepot.setSchedule(finalDepot);
        this.arrivalTime = finalDepot;

        pathPointsDef.remove(pathPointsDef.size() - 1);
        pathPointsDef.add(p1);
        pathPointsDef.add(p2);
        pathPointsDef.add(p3);

        pointsDef.add(pointsDef.size() - 1, pickUp);
        pointsDef.add(pointsDef.size() - 1, delivery);

        this.graph.getListePoint().put(pickUp.getId(), pickUp);
        this.graph.getListePoint().put(delivery.getId(), delivery);

        this.graph.setSolution(pathPointsDef);


    }
}
