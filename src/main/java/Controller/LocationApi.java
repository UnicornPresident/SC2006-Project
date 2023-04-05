package Controller;
import Entity.*;
import org.json.JSONArray;
import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class LocationApi extends Controller {
    // Api_key from google map
    private String API_KEY = "AIzaSyD4qpf0huSudBr0gTZEqWuHBf-_ooj6szI";
    private String address;
    private String Url;

    public LocationApi(String address){
        this.address=address;
    }
    //Used to construct the url for HTTP request
    public void urlconstructor(){
        this.Url ="https://maps.googleapis.com/maps/api/geocode/json?address=${"+ this.address + "}+&key=" +this.API_KEY;
    }

    public void setAddress(String address){
        this.address = address;
    }

    public LatLng GetLatLng() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL(this.Url).openConnection();
        conn.setRequestMethod("GET");
        conn.setRequestProperty("Accept", "text/csv");

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK) {
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            StringBuilder responseBuilder = new StringBuilder();
            int ch;
            while ((ch = isr.read()) != -1) {
                responseBuilder.append((char) ch);
            }
            String response = responseBuilder.toString();
            if (validate(response)){
                JSONObject jsonResponse = new JSONObject(response);
                JSONArray resultsArray = jsonResponse.getJSONArray("results");
                JSONObject location = resultsArray.getJSONObject(0).getJSONObject("geometry").getJSONObject("location");
                double lat = location.getDouble("lat");
                double lng = location.getDouble("lng");
                return new LatLng(lat, lng);

            }
        }
        return new LatLng(404,404);
    }

    public boolean validate(String response){
        JSONObject object = new JSONObject(response);
        String statusValue = object.getString("status");
        //System.out.println("The status value is " +statusValue);
        if (statusValue.equals("ZERO_RESULTS")) return false;
        else return true;
    }

    public boolean validate() {
        return false;
    }
}
