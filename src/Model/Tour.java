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

        Node predecessor;

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


        for (int i = 0; i < ap.size(); i++) {
            predecessor = ap.get(i).getPath();

            ArrayList<Node> listeNodeTot = new ArrayList<Node>();

            while (predecessor != null) {

                listeNodeTot.add(predecessor);
                costInter += (predecessor.getCost() / (3.6 * 15));
                //System.out.println("listeNodeTot[" + j + "] : " + listeNodeTot.get(j));

                for (String s : listePointReq.keySet()) {

                    if (predecessor.getIntersection().getId() == listePointReq.get(s).getId() && !listePointDef.contains(listePointReq.get(s))) {

                        listePointReq.get(s).setCostToReach(costInter);
                        dateInter = XmlUtils.findSchedule(dateInter, costInter, durationPrec);
                        listePointReq.get(s).setSchedule(dateInter);

                        listePointDef.add(listePointReq.get(s));
                        // System.out.println("Date inter" + listePointDef.get(k).getSchedule());
                        costInter = 0;
                        durationPrec = listePointDef.get(k).getDuration(); //à verifier
                        k++;
                    }
                }
                predecessor=predecessor.getPredecessor();
                j++;

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

}
