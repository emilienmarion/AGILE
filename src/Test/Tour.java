package Test;

import Controller.Controller;
import Model.MapData;
import Model.Request;
import Utils.XmlUtils;
import View.Frame;
import View.MapView;
import org.junit.*;

import java.text.ParseException;

public class Tour {

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

        String Firm = "AGILE/xmlFiles/requestsSmall2.xml";

        Request loadRequest = XmlUtils.ReadRequest(Firm, controller.getMd().getIntersections());
        try {
            frame.getTour().loadNewRequest(loadRequest);

        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

}
