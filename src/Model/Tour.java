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

    public Date getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(Date departureTime) {
        this.departureTime = departureTime;
    }

    public Date getArrivalTime() {
        return arrivalTime;
    }

    public void setArrivalTime(Date arrivalTime) {
        this.arrivalTime = arrivalTime;
    }

    public float getTotalDuration() {
        return totalDuration;
    }

    public void setTotalDuration(float totalDuration) {
        this.totalDuration = totalDuration;
    }

    public ArrayList<Point> getTheFinalPointList(HashMap<String, Point> listePointReq, MapView mapView, Request req) throws ParseException {
        Graph g = Algorithm.createGraph(listePointReq, mapView.getMap().getMapData(), req.getDepot());

        ArrayList<Path> ap = Algorithm.TSP(g);
       // ArrayList<String> listeString= (ArrayList<String>) listePointReq.keySet();

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
        this.tour = listePointDef;
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
        System.out.println("---------------------------------------------");
        System.out.println(this.tour);
    }

}
