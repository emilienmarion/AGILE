package Model;

import Utils.Algorithm;
import Utils.XmlUtils;
import View.MapView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class Tour
{
    private ArrayList<Point> pointsDef;
    private HashMap<String, Point> points;
    private Date departureTime;
    private Date arrivalTime;
    private float totalDuration;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private MapView mapView;
    private Request req;


    public Tour(MapView mv){
        this.mapView = mv;
    }

    public void loadNewRequest(Request r) throws ParseException {
        System.out.println("Tour.CONSTRUCTOR");
        this.req = r;
        this.points = req.getListePoint();
        points.put(req.getDepot().getId(),req.getDepot());
        computeTheFinalPointList(points, mapView, req);
    }


    public ArrayList<Point> getPointsDef() {
        return this.pointsDef;
    }

    public void setPointsDef(ArrayList<Point> pointsDef) {
        this.pointsDef = pointsDef;
    }

    public String getDepartureTime() {
        String ret = Integer.toString(departureTime.getHours()) + "h " + Integer.toString(departureTime.getMinutes())+"min "+Integer.toString(departureTime.getSeconds())+"s";
        return ret;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public String getArrivalTime() {
        String ret = Integer.toString(arrivalTime.getHours()) + "h " + Integer.toString(arrivalTime.getMinutes())+"min "+Integer.toString(arrivalTime.getSeconds())+"s";
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

    public void setTotalDuration(float totalDuration) {
        this.totalDuration = totalDuration;
    }



    public void computeTheFinalPointList(HashMap<String, Point> listePointReq, MapView mapView, Request req) throws ParseException {
        System.out.println("Tour.computeTheFinalPointList");
        Graph g = Algorithm.createGraph(listePointReq, mapView.getMap().getMapData(), req.getDepot());
        ArrayList<Path> ap = Algorithm.TSP(g);

        this.graph = Algorithm.createGraph(listePointReq, mapView.getMap().getMapData(), req.getDepot());
        ArrayList<Path> ap = Algorithm.TSP(this.graph);
        Node predecessor;

        Node node;


        ArrayList<Point> listePointDef = new ArrayList<Point>();
        float costInter = 0;
        int durationPrec = 0;
        int k = 0;
        int j=0;
        boolean test = true;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date dateInter = sdf.parse(req.getDepartureTime());
        System.out.println("date de depart : " + dateInter);
        req.getDepot().setSchedule(dateInter);
        listePointDef.add(req.getDepot());
     boolean isChecked=false;

        for (int i = 0; i < ap.size(); i++) {
            node = ap.get(i).getPath();

                costInter += (node.getCost() * (3.6 / 15));


                for (String s : listePointReq.keySet() ) {

                    if (node.getIntersection().getId() == listePointReq.get(s).getId() ) {

                        listePointReq.get(s).setCostToReach(costInter);
                        dateInter = XmlUtils.findSchedule(dateInter, costInter);
                        listePointReq.get(s).setSchedule(dateInter);


            //System.out.println("nodeID  : "+node.getIntersection().getId());
                        if (node.getIntersection().getId().equals(req.getDepot().getId())){
                            if (isChecked){
                                Point p=new Point();
                                p.setType("depot");
                                p.setSchedule(dateInter);
                                System.out.println("hehoooo"+p);
                                listePointDef.add(p);
                            }else{
                                listePointDef.add(listePointReq.get(s));
                                isChecked=true;
                                System.out.println("hehoooo22");
                            }
                        }else{
                            listePointDef.add(listePointReq.get(s));
                        }

                        costInter = 0;

                        k++;
                    }
                }

            }




        System.out.println("Point dans la liste def : " + listePointDef + "c'est fini la");
        this.pointsDef = listePointDef;

        setDepartureTime(this.pointsDef.get(0).getSchedule());
        setArrivalTime(this.pointsDef.get(pointsDef.size()-1).getSchedule());
        setTotalDuration((this.pointsDef.get(pointsDef.size()-1).getSchedule().getTime())-(this.pointsDef.get(0).getSchedule().getTime()));
    }

    public void deletePoint(String idPoint)
    {
        //to do : recalculer l'heure d'arrivee et la duration totale SSI le point supprime est le dernier de la liste
        // supprimer le pickup ou le delivery correspondant au point supprime
        System.out.println("Tour.deletePoint");
        System.out.println(this.pointsDef);
        for(int i = 0; i< pointsDef.size(); i++){
            if(idPoint.equals(pointsDef.get(i).getId()))
            {
                this.pointsDef.remove(i);
                break;
            }
        }
        setPointsDef(this.pointsDef);
        System.out.println("---------------------------------------------");
        System.out.println(this.pointsDef);
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
            for(int i=0; i<tour.size(); i++){
                if(idPoint.equals(tour.get(i).getId())){
                    pointToChange = tour.get(i);
                    tour.remove(i);
                    pointToChange.setSchedule(newScheduleDate);
                }
            }
       // }


        tour.add(pointToChange);
        System.out.println("Point modifié : " + pointToChange);
        System.out.println("nouveau tour : " + tour);
        System.out.println("------------->nvSchedule point to change : " + pointToChange.getSchedule());
    }



}
