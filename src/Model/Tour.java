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
    private ArrayList<Point> tour;
    private Date departureTime;
    private Date arrivalTime;
    private float totalDuration;
    private SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");

    public Tour(ArrayList<Point> tour) {
        this.tour = tour;
    }

    public Tour(){

    }

    public ArrayList<Point> getTour() {
        return tour;
    }

    public void setTour(ArrayList<Point> tour) {
        this.tour = tour;
    }

    public Date getDepartureTime() { return departureTime; }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public void initDepatureTime() { setDepartureTime(this.tour.get(0).getSchedule()); }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public void initArrivalTime() { setArrivalTime(this.tour.get(tour.size()-1).getSchedule()); }

    public float getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(float totalDuration) {
        this.totalDuration = totalDuration;
    }

    public void initTotalDuration() {setTotalDuration((this.tour.get(tour.size()-1).getSchedule().getTime())-(this.tour.get(0).getSchedule().getTime()));}

    public ArrayList<Point> getTheFinalPointList(HashMap<String, Point> listePointReq, MapView mapView, Request req) throws ParseException {
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
        this.tour = listePointDef;
        initDepatureTime();
        initArrivalTime();
        initTotalDuration();

        return listePointDef;
    }

    public void deletePoint(String idPoint)
    {
        System.out.println("Tour.deletePoint");
        System.out.println(this.tour);
        for(int i = 0; i<tour.size();i++){
            if(idPoint.equals(tour.get(i).getId()))
            {
                this.tour.remove(i);
                break;
            }
        }
        setTour(this.tour);
        System.out.println("---------------------------------------------");
        System.out.println(this.tour);
    }

}
