import org.json.JSONObject;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.ResourceBundle;
import java.util.*;

public class LocationApi extends Controller {
    // Api_key from google map
    static String API_KEY = "AIzaSyD4qpf0huSudBr0gTZEqWuHBf-_ooj6szI";
    private String address;
    private String Url;

    public LocationApi(String address){
        this.address=address;
    }
    //Used to construct the url for HTTP request
    public void urlconstructor(){
        this.Url ="https://maps.googleapis.com/maps/api/geocode/json?address=${"+ this.address + "}+&key=" +this.API_KEY;
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
                String[] parts = response.split(",");
                String str1= findNumericChar(parts[16]);
                String str2= findNumericChar(parts[17]);
                double num1=Double.parseDouble(str1);
                double num2=Double.parseDouble(str2);
                return new LatLng(num1,num2);
            }
        }
        return new LatLng(404,404);
    }


    public String findNumericChar(String inputString){
        String number = "-1";
        Pattern pattern = Pattern.compile("\\d+(\\.\\d+)?"); // Match one or more digits, optionally followed by a decimal point and one or more digits
        Matcher matcher = pattern.matcher(inputString);

        if (matcher.find()) {
             number = matcher.group();
        }
        return number;
    }

    public boolean validate(String response){
        JSONObject object = new JSONObject(response);
        String statusValue = object.getString("status");
        //System.out.println("The status value is " +statusValue);
        if (statusValue.equals("ZERO_RESULTS")) return false;
        else return true;
    }

    @Override
    public boolean validate() {
        return false;
    }
}
