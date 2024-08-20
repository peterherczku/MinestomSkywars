package dev.blockeed.managers;

import com.google.gson.Gson;
import dev.blockeed.Main;
import dev.blockeed.entities.Map;
import dev.blockeed.utils.GsonUtil;

import java.io.InputStream;
import java.io.InputStreamReader;

public class MapManager {

    /*
    Change these later
     */

    public static Map loadMap() {
        InputStream inputStream = Main.class.getClassLoader().getResourceAsStream("map.json");

        if (inputStream == null) {
            System.err.println("File not found in resources: map.json");
            return null;
        }

        // Convert the InputStream to InputStreamReader
        InputStreamReader reader = new InputStreamReader(inputStream);

        // Create a Gson instance
        Gson gson = new Gson();

        // Parse the JSON into the MapData object
        Map map = gson.fromJson(reader, Map.class);

        // Close the reader
        try {
            reader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return map;
    }

    public static void saveMap(Map map) {
        String s = GsonUtil.getGson().toJson(map);
        System.out.println(s);
    }

}
