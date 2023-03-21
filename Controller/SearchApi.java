import java.util.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class SearchApi extends Controller{

    private String SearchLocation;
    private String Url;

    public SearchApi(String search){
        this.SearchLocation = search;
    }
    public void urlconstructor() {
        this.Url = "https://data.gov.sg/api/action/datastore_search?resource_id=139a3035-e624-4f56-b63f-89ae28d4ae4c&q=" + this.SearchLocation;
    }
    public ArrayList<String[]> getSearchResults() throws IOException {
        HttpURLConnection conn = (HttpURLConnection) new URL (this.Url).openConnection();
        conn.setRequestMethod("GET");
        conn.connect();
        ArrayList<String[]> filteredRecords = new ArrayList<>();

        if (conn.getResponseCode() == HttpURLConnection.HTTP_OK){
            InputStream is = conn.getInputStream();
            InputStreamReader isr = new InputStreamReader(is);
            StringBuilder responseBuilder = new StringBuilder();
            int ch;
            while ((ch = isr.read()) != -1) {
                responseBuilder.append((char) ch);
            }
            String response = responseBuilder.toString();
            JSONObject jsonResponse = new JSONObject(response);
            JSONObject resultsObject = jsonResponse.getJSONObject("result");
            JSONArray recordsArray = resultsObject.getJSONArray("records");
            
            
                /*
                 * String[]: 
                 * [0] - Address
                 * [1] - x-coordinate
                 * [2] - y-coordinate
                 * [3] - Carpark Number
                 * [4] - Available No. of Lots
                 * [5] - Total No. of Lots  
                 */
            if (validate(response)){
                for (int i=0; i<recordsArray.length(); ++i) {
                JSONObject record = recordsArray.getJSONObject(i);
                String[] filteredRecord = new String[6];
                String address = record.getString("address");
                filteredRecord[0] = address;
                String x_coord = record.getString("x_coord");
                filteredRecord[1] = x_coord;
                String y_coord = record.getString("y_coord");
                filteredRecord[2] = y_coord;
                String carparkNo = record.getString("car_park_no");
                filteredRecord[3] = carparkNo;
                filteredRecords.add(filteredRecord);
                }   
            }
        }
        return filteredRecords;
    }

    public boolean validate(String response) {
        JSONObject object = new JSONObject(response);
        boolean statusValue = object.getBoolean("success");
        if (!statusValue == true) 
            return false;
        else
            return true;
    }

    @Override
    public boolean validate(){
        return false;
    }

}
