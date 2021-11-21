package View;


import Utils.MapData;
import Utils.XmlUtils;


public class Main {

    public static void main(String[] args) {
	// write your code here
        MapData loadedMap=XmlUtils.readMap("xmlFiles/smallMap.xml");
        //System.out.println(loadedMap);
        System.out.println("hello World");

        Window myWind = new Window();
        myWind.Display();
    }
}
