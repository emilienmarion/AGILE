package Controller;


import Model.MapData;
import Utils.XmlUtils;
import View.*;


public class Main {

    public static void main(String[] args) {
        MapData loadedMap = XmlUtils.readMap("xmlFiles/smallMap.xml");
        System.out.println(loadedMap);
        System.out.println("hello World");
        Window frame=new Window(1000,700,loadedMap);

    }
}
