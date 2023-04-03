import org.json.JSONObject;
import java.lang.Math;
import org.json.JSONArray;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.*;

public class AvailabilityApi extends Controller{
    
    private String Url = "http://datamall2.mytransport.sg/ltaodataservice/CarParkAvailabilityv2";
    private String ApiKey = "Y4px7k0AT4GnCO/KEQFrRA==";
    private JSONObject allCarparks;
    private JSONObject nearbyCarparks;

    public AvailabilityApi(){ 
    }

    public String getUrl(){
        return this.Url;
    }

    public JSONObject getAllCarparks(){
        return this.allCarparks;
    }

    public JSONObject getNearbyCarparks(){
        return this.nearbyCarparks;
    }

    public void displayAllCarparks(){
        JSONArray array = this.allCarparks.getJSONArray("Carparks");
        for (int i = 0 ; i<array.length() ; i++) {
            System.out.println(array.getJSONObject(i));
        }
    }

    public void displayNearbyCarparks(){
        JSONArray array = this.nearbyCarparks.getJSONArray("Carparks");
        for (int i = 0 ; i<array.length() ; i++) {
            System.out.println(array.getJSONObject(i));
        }
    }



    public void getAvailability() throws IOException{
        HttpURLConnection conn = (HttpURLConnection) new URL (this.Url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("AccountKey", this.ApiKey);
        conn.setRequestProperty("accept", "application/json");
        conn.connect();
        
        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            StringBuilder responseBuilder = new StringBuilder();
            int ch;
            while ((ch = isr.read()) != -1){
                responseBuilder.append((char) ch);
            }
            String response = responseBuilder.toString();
            JSONObject jsonResponse = new JSONObject(response);
            // System.out.println(jsonResponse);
            JSONArray valueArray = jsonResponse.getJSONArray("value");
            JSONArray filteredArray = new JSONArray();
            if (validate(response)){
                for (int i = 0; i<valueArray.length() ; i++){
                    if (!valueArray.getJSONObject(i).getString("Agency").equals("LTA")) // To filter out LTA carparks
                        { 
                            JSONObject filteredCarpark = new JSONObject();
                            filteredCarpark.put("Agency", valueArray.getJSONObject(i).getString("Agency")); 
                            filteredCarpark.put("CarParkID", valueArray.getJSONObject(i).getString("CarParkID")); 
                            filteredCarpark.put("Address", valueArray.getJSONObject(i).getString("Development")); 
                            filteredCarpark.put("Available Lots", valueArray.getJSONObject(i).getInt("AvailableLots")); 
                            filteredCarpark.put("Latitude", valueArray.getJSONObject(i).getString("Location").split(" ")[0]); 
                            filteredCarpark.put("Longitude", valueArray.getJSONObject(i).getString("Location").split(" ")[1]); 
                            filteredArray.put(filteredCarpark);
                        }
                }
            }
            JSONObject filteredObject = new JSONObject();
            filteredObject.put("Carparks", filteredArray);
            this.allCarparks = filteredObject;
            
        }

    }

    public double distance(double lat1, double lat2, double lon1,
        double lon2, double el1, double el2) {

        final int R = 6371; // Radius of the earth

        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);
        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);
        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
        double distance = R * c * 1000; // convert to meters

        double height = el1 - el2;

        distance = Math.pow(distance, 2) + Math.pow(height, 2);

        return Math.sqrt(distance);
    }

    public void getNearestCarparks(double maxDist, LatLng latLng){
        JSONObject nearbyCarparksoObject = new JSONObject();
        JSONArray nearbyCarparksArray = new JSONArray();
        for (int i = 0 ; i < allCarparks.getJSONArray("Carparks").length() ; i++){
            JSONObject carpark = allCarparks.getJSONArray("Carparks").getJSONObject(i);
            double lat1 = Double.parseDouble(carpark.getString("Latitude"));
            double lat2 = latLng.getLatitude();
            double lng1 = Double.parseDouble(carpark.getString("Longitude"));
            double lng2 = latLng.getLongitude();

            // System.out.println(lat1 +" "+ lat2 +" "+ lng1 +" "+ lng2);
            double dist = distance(lat1, lat2, lng1, lng2, 0, 0);
            // System.out.println("dist = " + dist);
            if (dist < maxDist){
                carpark.put("Distance From Location (in metres)", dist);
                nearbyCarparksArray.put(carpark);
                // System.out.println(dist);
            }
        }
        nearbyCarparksoObject.put("Carparks", nearbyCarparksArray);
        this.nearbyCarparks = nearbyCarparksoObject;
    }

    public void sortByDistance(){
        JSONArray jsonArr = this.nearbyCarparks.getJSONArray("Carparks");
        JSONArray sortedJsonArray = new JSONArray();

        ArrayList<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0 ; i < jsonArr.length(); i++){
            jsonValues.add(jsonArr.getJSONObject(i));
        }
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            private static final String KEY_NAME = "Distance From Location (in metres)";

            public int compare(JSONObject a, JSONObject b) {
                double valA;
                double valB;              
                valA = (Double) a.get(KEY_NAME);
                valB = (Double) b.get(KEY_NAME);
                return (int) (valA - valB);
            }
        });

        for (int i = 0; i < jsonArr.length(); i++){
            sortedJsonArray.put(jsonValues.get(i));
        }
        JSONObject sortedJsonObject = new JSONObject();
        sortedJsonObject.put("Carparks", sortedJsonArray);
        this.nearbyCarparks = sortedJsonObject;
    }

    public void sortByAvailability(){
        JSONArray jsonArr = this.nearbyCarparks.getJSONArray("Carparks");
        JSONArray sortedJsonArray = new JSONArray();

        ArrayList<JSONObject> jsonValues = new ArrayList<JSONObject>();
        for (int i = 0 ; i < jsonArr.length(); i++){
            jsonValues.add(jsonArr.getJSONObject(i));
        }
        Collections.sort(jsonValues, new Comparator<JSONObject>() {
            private static final String KEY_NAME = "Available Lots";

            public int compare(JSONObject a, JSONObject b) {
                int valA;
                int valB;              
                valA = (int) a.get(KEY_NAME);
                valB = (int) b.get(KEY_NAME);
                return (int) (valA - valB);
            }
        });

        for (int i = 0; i < jsonArr.length(); i++){
            sortedJsonArray.put(jsonValues.get(i));
        }
        JSONObject sortedJsonObject = new JSONObject();
        sortedJsonObject.put("Carparks", sortedJsonArray);
        this.nearbyCarparks = sortedJsonObject;
    }

    public boolean validate(String response){
        return true;
    }

    // @Override
    // public boolean validate(){
    //     return false;
    // }



    
}
