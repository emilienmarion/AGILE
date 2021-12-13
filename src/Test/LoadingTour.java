package Test;

import Controller.Controller;
import Model.MapData;
import Model.Request;
import Utils.XmlUtils;
import View.Frame;
import View.MapView;
import org.junit.Before;
import org.junit.Test;

import java.text.ParseException;

import static org.junit.Assert.assertEquals;

public class LoadingTour {

    private Frame frame;
    private Controller controller;
    private MapView mapView;

    @Before
    public void init(){
        String mapPath = "AGILE/xmlFiles/smallMap.xml";
        MapData loadedMap = XmlUtils.readMap(mapPath);
        this.frame = new Frame(loadedMap,mapPath);
        this.controller = frame.getController();
        this.mapView = frame.getMapView();
    }

    @Test
    public void wellFormedTour(){
        boolean result;
        String Firm = "AGILE/xmlFiles/requestsSmall2.xml";

        Request loadRequest = XmlUtils.ReadRequest(Firm, controller.getMd().getIntersections());
        try {
            frame.getTour().loadNewRequest(loadRequest);
            result = true;

        } catch (ParseException e) {
            e.printStackTrace();
            result = false;
        }
        assertEquals(true, result);
    }

    @Test
    public void notXML(){
        boolean result;
        String Firm = "AGILE/xmlFiles/requestsSmall2.pnl";

        if (!controller.verifXml(Firm)) {
            System.out.println("File type is not xml");
            result = false;
        } else {
            Request loadRequest = XmlUtils.ReadRequest(Firm, controller.getMd().getIntersections());
            try {
                frame.getTour().loadNewRequest(loadRequest);
                result = true;

            } catch (ParseException e) {
                e.printStackTrace();
                result = false;
            }
        }
        assertEquals(false, result);
    }

}
