package View;

import Utils.XmlUtils;

import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String[] args) {
	// write your code here
        ArrayList<ArrayList<HashMap<String,String>>> loadedMap=XmlUtils.readMap("xmlFiles/smallMap.xml");
        System.out.println(loadedMap);
        System.out.println("hello World");
    }
}
