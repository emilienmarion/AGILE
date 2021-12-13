package Test;

import Controller.*;
import Model.MapData;
import Utils.XmlUtils;
import View.Frame;
import View.MapView;
import org.junit.*;
import static org.junit.Assert.*;

public class LoadingMap {
    private Frame frame;
    private Controller controller;
    private MapView mapView;

    @Before
    public void init(){
        // Write the initial application state here
        String mapPath = "AGILE/xmlFiles/smallMap.xml";
        MapData loadedMap = XmlUtils.readMap(mapPath);
        this.frame = new Frame(loadedMap,mapPath);
        this.controller = frame.getController();
        this.mapView = frame.getMapView();
    }

    @Test
    public void wellFormed(){
        // Same code as the method wich call mapView.loadMap()
        String Firm ="AGILE/xmlFiles/largeMap.xml";
        MapData loadedMap = XmlUtils.readMap(Firm);
        boolean result = mapView.loadMap(loadedMap, Firm);

        assertEquals(true, result);
    }

    @Test
    public void notXML(){
        String Firm ="AGILE/xmlFiles/largeMap.pnl";
        boolean result;

        if (!controller.verifXml(Firm)) {
            System.out.println("File type is not xml");
            result = false;
        } else {
            MapData loadedMap = XmlUtils.readMap(Firm);
            frame.loadMap(loadedMap, Firm);
            result = true;
        }

        assertEquals(false, result);
    }


}
