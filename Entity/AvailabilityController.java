package Entity;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import java.util.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;
import com.google.gson.JsonElement;
import com.google.gson.JsonArray;
import com.google.gson.Gson;

public class AvailabilityController {
    
    public static void main(String[] args) {

        try {

            URL url = new URL("https://data.gov.sg/api/action/datastore_search?resource_id=139a3035-e624-4f56-b63f-89ae28d4ae4c&q=CHANGI VILLAGE");
            // URL url = new URL("https://api.data.gov.sg/v1/transport/carpark-availability");


            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            conn.connect();

            int responseCode = conn.getResponseCode();

            if (responseCode != 200) {
                throw new RuntimeException("HttpResponseCode: "+ responseCode);
            } else {

                StringBuilder informationString = new StringBuilder();
                Scanner sc = new Scanner(url.openStream());

                while (sc.hasNext()){
                    informationString.append(sc.nextLine());
                }

                sc.close();

                System.out.println(informationString);

                Gson g = new Gson();
                String info = g.toJson(informationString);


                JSONParser parse = new JSONParser();
                JSONArray dataObject = (JSONArray) parse.parse(String.valueOf(informationString));
                JSONArray array = new JSONArray();
                array.add(dataObject);
                // JSONObject object = new JSONObject(); 
                // object = (JSONObject) parse.parse(informationString.toString());
                // JSONArray jsonArray = g.toJson(informationString);

                // for (int i=0;i<jsonArray.size();i++){
                //     JSONObject jsonObject = (JSONObject) jsonArray.get(i);
                //     Set<Map.Entry<String, JsonElement>> entries = jsonObject.entrySet();
                //     for (Map.Entry<String, JsonElement> entry : entries) {
                //         System.out.println(entry.getKey() + " -> " + entry.getValue());
                //     }
                // }
                

                // JSONArray jsonArray = dataObject.getJSONArray(records);
                System.out.println(dataObject.get(0));

                // JSONObject carparkData = (JSONObject) dataObject.get(0);


            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
