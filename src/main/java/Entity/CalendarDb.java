package Entity;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONObject;

public class CalendarDb {
	String apiKey = "820d182364437e8a58702c485705fd3417ea49f2";
    	String country = "SG";
    	String year = "2023";
   	ArrayList<String> dateTypes = new ArrayList<String>();
    
    	public ArrayList<String> dateType(LocalDate start_date, LocalDate end_date) {
        	ArrayList<String> holidaysList = new ArrayList<String>();
        	try {
            		// Create the URL for the API endpoint
            		String urlString = "https://calendarific.com/api/v2/holidays?api_key=" + apiKey + "&country=" + country + "&year=" + year;
            		URL url = new URL(urlString);

            		// Open a connection to the URL using HttpURLConnection
            		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            		// Set the request method to GET
            		connection.setRequestMethod("GET");

            		// Get the response code
            		int responseCode = connection.getResponseCode();
            		// Check if the response code indicates success
            		if (responseCode == HttpURLConnection.HTTP_OK) {
                		// Read the response data from the connection's input stream
               			BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                		String line;
                		StringBuffer response = new StringBuffer();
                
                		while ((line = reader.readLine()) != null) {
                    			response.append(line);
                		}
                		reader.close();

                		JSONObject jsonResponse = new JSONObject(response.toString());
                		JSONArray holidaysArray = jsonResponse.getJSONObject("response").getJSONArray("holidays");
                    
                		for (int i = 0; i < holidaysArray.length(); i++) {
                    			JSONObject holidayObject = holidaysArray.getJSONObject(i);
								if (holidayObject.getString("primary_type").equals("Observance"))
									continue;
                    			String holidayName = holidayObject.getString("name");
                    			String holidayDateIsoStr = holidayObject.getJSONObject("date").getString("iso");
                    			String holidayDateStr = holidayDateIsoStr.substring(0, 10);
                    			DateTimeFormatter holidayFormatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
                    			LocalDate holidayDate = LocalDate.parse(holidayDateStr, holidayFormatter);
                    			holidaysList.add(holidayDate + ":" + holidayName);
            	    		}
            		} else {
               		System.out.println("GET request failed: " + responseCode);
            }
            for (LocalDate date = start_date; date.isBefore(end_date.plusDays(1)); date = date.plusDays(1)) {
                String day = date.format(DateTimeFormatter.ofPattern("EEEE"));
                boolean isHoliday = false;
                String holidayName = "";

                for (String holiday : holidaysList) {
                    DateTimeFormatter holidayformatter = DateTimeFormatter.ofPattern("uuuu-MM-dd");
                    LocalDate holidayDate = LocalDate.parse(holiday.split(":")[0], holidayformatter);
                    if (date.equals(holidayDate)) {
                        isHoliday = true;
                        holidayName = holiday.split(":")[1];
                        break;
                    } 
                }

                if (isHoliday) {
                    dateTypes.add("Public Holiday");
                    //System.out.println(holidayName);
                } else {
                    dateTypes.add(day);
                    //System.out.println(day);
                }
            }
            

        } catch (Exception e) {
            e.printStackTrace();
        }
        return dateTypes;
    }
}
