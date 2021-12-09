package Model;

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


    public Tour(MapView mv){
        this.mapView = mv;
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
        // Detect the case where the arrival schedule must change
        if(firstToDelete.getId().equals(pointsDef.get(pointsDef.size()-2).getId())
                || secondToDelete.getId().equals(pointsDef.get(pointsDef.size()-2).getId())){
            changeArrival = true;
        }

        if(firstToDelete!=null && secondToDelete!=null) {
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

            // And delete on the data structure
            pointsDef.remove(firstToDelete);
            pointsDef.remove(secondToDelete);
            glueFreeScheduleList();
        }


        // Change the arrival time
        try {
            if (changeArrival) {
                Point lastDepot = pointsDef.get(pointsDef.size() - 1);
                Point previousPoint = pointsDef.get(pointsDef.size() - 2);
                Date newDate = XmlUtils.findSchedule(previousPoint.getSchedule(), lastDepot.getCostToReach(), previousPoint.getDuration());
                lastDepot.setSchedule(newDate);
            }
        }catch (Exception e){
            System.out.println("Tour.deletePoint ERROR : "+e);
        }
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

        if(freeSchedule.size()>2) {


            for (int i = 0; i < freeSchedule.size()-1; i++) {
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


                if (currentArrival.getHours() > nextDeparture.getHours()
                        || currentArrival.getHours() >= nextDeparture.getHours()
                        && currentArrival.getMinutes() > nextDeparture.getMinutes()
                        ||currentArrival.getHours() >= nextDeparture.getHours()
                        && currentArrival.getMinutes() >= nextDeparture.getMinutes()
                        && currentArrival.getSeconds() >= nextDeparture.getSeconds()) {
                    System.out.println("a1 > d2");
                    newCouple.add(freeSchedule.get(i).get(0));
                    newCouple.add(freeSchedule.get(i + 1).get(1));
                    coupleToDelete.add(freeSchedule.get(i));
                    coupleToDelete.add(freeSchedule.get(i + 1));
                    freeSchedule.add(newCouple);
                } else if (currentDeparture.getHours() < nextArrival.getHours()
                        || currentDeparture.getHours() <= nextArrival.getHours()
                        && currentDeparture.getMinutes() < nextArrival.getMinutes()
                        ||currentDeparture.getHours() <= nextArrival.getHours()
                        && currentDeparture.getMinutes() <= nextArrival.getMinutes()
                        && currentDeparture.getSeconds() <= nextArrival.getSeconds()) {
                    System.out.println("d1 < a2");
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


            if (currentArrival.getHours() > nextDeparture.getHours()
                || currentArrival.getHours() >= nextDeparture.getHours()
                    && currentArrival.getMinutes() > nextDeparture.getMinutes()
                ||currentArrival.getHours() >= nextDeparture.getHours()
                    && currentArrival.getMinutes() >= nextDeparture.getMinutes()
                    && currentArrival.getSeconds() >= nextDeparture.getSeconds()) {
                System.out.println("a1 > d2");
                newCouple.add(freeSchedule.get(0).get(0));
                newCouple.add(freeSchedule.get(1).get(1));
                coupleToDelete.add(freeSchedule.get(0));
                coupleToDelete.add(freeSchedule.get(1));
                freeSchedule.add(newCouple);
            } else if (currentDeparture.getHours() < nextArrival.getHours()
                || currentDeparture.getHours() <= nextArrival.getHours()
                    && currentDeparture.getMinutes() < nextArrival.getMinutes()
                ||currentDeparture.getHours() <= nextArrival.getHours()
                    && currentDeparture.getMinutes() <= nextArrival.getMinutes()
                    && currentDeparture.getSeconds() <= nextArrival.getSeconds()) {
                System.out.println("d1 < a2");
                newCouple.add(freeSchedule.get(0 + 1).get(0));
                newCouple.add(freeSchedule.get(0).get(1));
                coupleToDelete.add(freeSchedule.get(0));
                coupleToDelete.add(freeSchedule.get(0 + 1));
                freeSchedule.add(newCouple);
            }
        }
        // Delete useless ones
        for (ArrayList<Date> toDelete : coupleToDelete) {
            freeSchedule.remove(freeSchedule.indexOf(toDelete));
        }

        // Display the final free ranges
        freeSchedule.forEach((couple) -> {
            System.out.println(" FREE couple : " + couple.get(0) + " to " + couple.get(1));
        });
    }


    public void editPoint(String idPoint, String nvSchedule) throws ParseException {
        System.out.println("Tour.editPoint");
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date newScheduleDate = sdf.parse(nvSchedule);
        //float costToReach = tour.get(tour.size()-1).getCostToReach(); //this.graph.getContent();

        /*System.out.println(graph.toString());
        for(int j = 0; j<graph.getContent().size(); j ++){
            //System.out.println(tour.get(tour.size()-1)));
           if(graph.getContent().get(j).get(0).getIdOrigin().equals(tour.get(tour.size()-1).getId())){
                for(int k = 0; k<graph.getContent().get(j).size();k++){
                    System.out.println("je suis rentrer dans le premier if");
                    System.out.println(graph.getContent().get(j).get(k));
                    if(graph.getContent().get(j).get(k).getIdDestination().equals(idPoint) && !graph.getContent().get(j).get(k).equals(null)){
                        System.out.println("je suis rentrer dans le deuxieme if");
                        costToReach = graph.getContent().get(j).get(k).getLength();
                        costToReach = (float)(costToReach / (3.6 * 15));
                        break;
                    }
                }
            }
        }*/
      //  System.out.println("******************" + tour.get(tour.size()-2).getSchedule());
       // Date scheduleToCompare = XmlUtils.findSchedule(tour.get(tour.size()-2).getSchedule(), costToReach, tour.get(tour.size()-2).getDuration()); //pas sur le -1
      // System.out.println("-------------------> cout calcule :" + scheduleToCompare);
        Point pointToChange = new Point();
       // if(scheduleToCompare.before(newScheduleDate)){
            for(int i=0; i<pointsDef.size(); i++){
                if(idPoint.equals(pointsDef.get(i).getId())){
                    pointToChange = pointsDef.get(i);
                    pointsDef.remove(i);
                    pointToChange.setSchedule(newScheduleDate);
                }
            }
       // }


        pointsDef.add(pointToChange);
        System.out.println("Point modifié : " + pointToChange);
        System.out.println("nouveau tour : " + pointsDef);
        System.out.println("------------->nvSchedule point to change : " + pointToChange.getSchedule());
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
}
