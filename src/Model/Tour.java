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
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
    private MapView mapView;
    private Request req;
    private Graph graph;


    public Tour(MapView mv){
        this.mapView = mv;
        pointsDef=new ArrayList<Point>();
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
//bouges moi ca garcon
    private Node getTail(Node n){
        while (n.getPredecessor()!=null){
            n=n.getPredecessor();
        }
        return n;
    }
    public void computeTheFinalPointList() throws ParseException {
        System.out.println("Tour.computeTheFinalPointList");

        this.graph = Algorithm.createGraph(this.points, this.mapView.getMap().getMapData(), this.req.getDepot());
        ArrayList<Path> tspResult = Algorithm.TSP(this.graph);
        Node node;
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date currentDate = sdf.parse(req.getDepartureTime());
        int durationPrec;
        int compteur=0;
        for (Path p:tspResult){
            Node n=p.getPath();
            if (compteur==0){
                Point depot=req.getDepot();
                Point depotToStock=new Point(depot);
                depotToStock.setId(depot.getId()+"start");
                depotToStock.setSchedule(currentDate);
                pointsDef.add(depotToStock);
            }
            String id=n.getIntersection().getId();
            Point current=req.getListePoint().get(id);
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

        setDepartureTime(this.pointsDef.get(0).getSchedule());
        setArrivalTime(this.pointsDef.get(pointsDef.size()-1).getSchedule());
        /*
        // A commenter ci dessous
        ArrayList<Point> listePointDef = new ArrayList<>();
        pointsDef = new ArrayList<>();

        float costInter = 0;
        int durationPrec = 0;
        int k = 0;
        int j=0;
        boolean test = true;

        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        Date dateInter = sdf.parse(req.getDepartureTime());
        System.out.println("date de depart : " + dateInter);
        req.getDepot().setSchedule(dateInter);

        //listePointDef.add(req.getDepot());
        pointsDef.add(req.getDepot());

        boolean isChecked=false;

        for (int i = 0; i < tspResult.size(); i++) {
            node = tspResult.get(i).getPath();
                costInter += (node.getCost() * (3.6 / 15));

                for (String s : listePointReq.keySet() ) {
                    if (node.getIntersection().getId() == listePointReq.get(s).getId() ) {
                        // Change values into listePointReq
                        listePointReq.get(s).setCostToReach(costInter);
                        dateInter = XmlUtils.findSchedule(dateInter, costInter);
                        listePointReq.get(s).setSchedule(dateInter);

                        //System.out.println("nodeID  : "+node.getIntersection().getId());
                        // Depot gestion
                        if (node.getIntersection().getId().equals(req.getDepot().getId())){
                            if (isChecked){
                                // Create the last depot
                                Point p= new Point();
                                p.setType("depot");
                                p.setSchedule(dateInter);
                                p.setId("1");
                                //System.out.println("hehoooo"+p);
                                pointsDef.add(p);
                            }else{
                                pointsDef.add(listePointReq.get(s));
                                isChecked=true;
                                //System.out.println("hehoooo22");
                            }
                        }else{
                            pointsDef.add(listePointReq.get(s));
                        }
                        costInter = 0;
                        k++;
                    }
                }
            }

        System.out.println("Point dans la liste def : " + pointsDef + "c'est fini la");

        setDepartureTime(this.pointsDef.get(0).getSchedule());
        setArrivalTime(this.pointsDef.get(pointsDef.size()-1).getSchedule());*/
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






}
