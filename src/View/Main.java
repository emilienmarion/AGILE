package View;

import Utils.MapData;
import Utils.XmlUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	// write your code here
        MapData loadedMap=XmlUtils.readMap("xmlFiles/smallMap.xml");
        System.out.println(loadedMap);
        System.out.println("hello World");
    }
}
