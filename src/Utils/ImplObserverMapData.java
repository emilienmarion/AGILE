package Utils;
package Model;
package View;

public class ImplObserverMapData implements Observer {

    public void update(Observable o) {

        MapData mapData = o.getMapData();
        loadMap();


    }
}