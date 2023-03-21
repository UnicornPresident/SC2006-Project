import java.util.*;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.*;

public class AvailabilityApi extends Controller{
    
    private ArrayList<String[]> searchArray;
    private String Url = "https://api.data.gov.sg/v1/transport/carpark-availability";

    public AvailabilityApi(ArrayList<String[]> search){
        this.searchArray = search;
    }

    public String getUrl(){
        return this.Url;
    }

    public void display(){
        int j = 1;
        for (int i=0;i<searchArray.size();i++){
            if (searchArray.get(i)[4] != null && searchArray.get(i)[5] != null)
                System.out.println("Carpark no. " + (j) + "-> Address: " + searchArray.get(i)[0] + " has " + searchArray.get(i)[4] + " out of " + searchArray.get(i)[5] + " lots available.");
                j++;
        }
    }

    public void getAvailability() throws IOException{
        HttpURLConnection conn = (HttpURLConnection) new URL (this.Url).openConnection();
        conn.setRequestMethod("GET");
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
            JSONArray itemsArray = jsonResponse.getJSONArray("items");
            JSONObject dataObject = itemsArray.getJSONObject(0);
            JSONArray dataArray = dataObject.getJSONArray("carpark_data");
            if (validate(response)){
                for (int i=0; i<this.searchArray.size(); i++){
                    String carparkNo = searchArray.get(i)[3];
                    for (int j=0; j<dataArray.length(); ++j){
                        JSONObject item = dataArray.getJSONObject(j);
                        if (item.getString("carpark_number").equals(carparkNo)){
                            JSONArray lotsInfo = item.getJSONArray("carpark_info");
                            JSONObject lotsInfoArray = lotsInfo.getJSONObject(0);
                            this.searchArray.get(i)[4] = lotsInfoArray.getString("lots_available");
                            this.searchArray.get(i)[5] = lotsInfoArray.getString("total_lots");
                        }
                    }
                }
            }
            
        }

    }

    public boolean validate(String response){
        return true;
    }

    @Override
    public boolean validate(){
        return false;
    }



    
}
