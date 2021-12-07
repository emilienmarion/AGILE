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

           /* inter = ap.get(i).getPath();

            for (int j = 0; j < inter.size(); j++) {

                listeNodeTot.add(inter.get(j));
                costInter += (inter.get(j).getCost()/(3.6*15));
                System.out.println("listeNodeTot[" + j + "] : " + listeNodeTot.get(j));
                intersecTot.add(inter.get(j).getIntersection());

                for (String s : listePointReq.keySet()) {







                    if (intersecTot.get(j).getId() == listePointReq.get(s).getId() && !listePointDef.contains(listePointReq.get(s))) {
                        System.out.println("je rentre dans ce if");

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

            }

        }*/




        System.out.println("Point dans la liste def : " + listePointDef + "c'est fini la");
        return listePointDef;
    }

}
