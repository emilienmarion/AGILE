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
    public static ArrayList<Point> getTheFinalPointList(HashMap<String, Point> listePointReq, MapView mapView, Request req) throws ParseException {
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
        return listePointDef;
    }

}
