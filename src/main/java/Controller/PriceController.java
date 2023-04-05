package Controller;
import Boundary.*;
import Entity.*;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.zone.ZoneOffsetTransitionRule.TimeDefinition;
import java.util.*;
import java.time.*;
import java.lang.*;
import java.math.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.io.BufferedReader;
import java.io.FileReader;

public class PriceController {
    private LocalTime start_time, end_time;
    private LocalDate start_date, end_date;

    public PriceController() {

    }

    public double getPrice(String carparkId, LocalDate start_date, LocalDate end_date, LocalTime start_time, LocalTime end_time, ArrayList<String> dateTypes) throws ParseException {
        Scanner sc = new Scanner(System.in);
        String start_date_input = "", end_date_input = "";
        String start_time_input = "", end_time_input = "";
        Boolean restricted = false;

        //Initialising time integers
        start_time_input = start_time.toString();
        char start_hour_1 = start_time_input.charAt(0);
        int start_hour_1_int = start_hour_1;
        start_hour_1_int -= 48;
        char start_hour_2 = start_time_input.charAt(1);
        int start_hour_2_int = start_hour_2;
        start_hour_2_int -= 48;

        char start_min_1 = start_time_input.charAt(2);
        int start_min_1_int = start_min_1;
        start_min_1_int -= 48;
        char start_min_2 = start_time_input.charAt(3);
        int start_min_2_int = start_min_2;
        start_min_2_int -= 48;

        end_time_input = end_time.toString();
        char end_hour_1 = end_time_input.charAt(0);
        int end_hour_1_int = end_hour_1;
        end_hour_1_int -= 48;
        char end_hour_2 = end_time_input.charAt(1);
        int end_hour_2_int = end_hour_2;
        end_hour_2_int -= 48;

        char end_min_1 = end_time_input.charAt(2);
        int end_min_1_int = end_min_1;
        start_min_1_int -= 48;
        char end_min_2 = end_time_input.charAt(3);
        int end_min_2_int = end_min_2;
        start_min_2_int -= 48;

        //Get restricted
        String line = " ";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader("resource/hdb-carpark-information.csv"));
            while((line = br.readLine()) != null) {
                String[] carpark = br.readLine().split(splitBy);
                if (carpark.length == 0)
                    break;
                String number = carpark[0];
                // String address = carpark[1];
                // String x_coord = carpark[2];
                // String y_coord = carpark[3];
                String restrictedYN = carpark[4];
                String Y = "Y";
                if(number.equals(carparkId) && restrictedYN.equals(Y)) {
                    restricted = true;
                    break;
                }
            
            }
        }
        catch (IOException e) {
            e.printStackTrace();
        }

        //Defining rates
        double unrestricted_rate = 0.02; // 2 cents a min for non restricted
        double restricted_rate = 0.04;

        //Get restricted and unrestricted parking minutes
        int restricted_minutes_parked = 0;
        int restricted_hours_only = 0;
        int restricted_minutes_only = 0;
        int unrestricted_minutes_parked = 0;
        int unrestricted_hours_only = 0;
        int unrestricted_minutes_only = 0;

        if(restricted && start_date.getDayOfWeek() != DayOfWeek.SUNDAY) {
            
            //entire period is within restricted
            if(start_hour_1_int == 0 && start_hour_2_int >= 7 && end_hour_1_int == 1 && end_hour_2_int < 7 ) {
                restricted_hours_only = ((10 * end_hour_1_int) + end_hour_2_int) - ((10 * start_hour_1_int) + start_hour_2_int);
                if(restricted_hours_only == 0) {
                    restricted_minutes_only = ((10 * end_min_1_int) + end_min_2_int) - ((10 * start_min_1_int) + start_min_2_int);
                } else if((10 * end_min_1_int) + end_min_2_int < (10 * start_min_1_int) + start_min_2_int){ //less than one hour difference in minutes
                    restricted_minutes_only = ((10 * end_min_1_int) + end_min_2_int + 60) - ((10 * start_min_1_int) + start_min_2_int);
                    restricted_hours_only -= 1;
                } else {
                    restricted_minutes_only = ((10 * end_min_1_int) + end_min_2_int) - ((10 * start_min_1_int) + start_min_2_int);
                }
                    restricted_minutes_parked = restricted_minutes_only + (60 * restricted_hours_only);
            } else if(start_hour_1_int == 0 && start_hour_2_int < 7 && ((end_hour_1_int == 1 && end_hour_2_int < 7) || (end_hour_1_int == 0 && end_hour_2_int >= 7))) { //start before restricted
                //Calculate unrestricted parking time
                unrestricted_hours_only = 7 -  start_hour_2_int;
                unrestricted_minutes_only = 60 - ((10 * start_min_1_int) + start_min_2_int);
                unrestricted_minutes_parked = unrestricted_minutes_only + (60 * unrestricted_hours_only);
                //Calculate restricted parking time
                restricted_hours_only = ((10 * end_hour_1_int) + end_hour_2_int) - ((10 * start_hour_1_int) + start_hour_2_int) - unrestricted_hours_only;
                restricted_minutes_only = ((10 * end_min_1_int) + end_min_2_int);
                restricted_minutes_parked = restricted_minutes_only + (60 * restricted_hours_only);
            } else if((start_hour_1_int == 0 && start_hour_2_int >= 7) || (start_hour_1_int == 1 && start_hour_2_int < 7) && (end_hour_1_int == 1 && end_hour_2_int >= 7)) { //end after restricted
                //Calculate unrestricted parking time
                unrestricted_hours_only = end_hour_2_int - 7;
                unrestricted_minutes_only = (10 * end_min_1_int + end_min_2_int);
                unrestricted_minutes_parked = unrestricted_minutes_only + (60 * unrestricted_hours_only);
                //Calculate restricted parking time
                restricted_hours_only = 17 - ((10 * start_hour_1_int) + start_hour_2_int) - unrestricted_hours_only;
                restricted_minutes_only = 60 - ((10 * start_min_1_int) + start_min_2_int);
                restricted_minutes_parked = restricted_minutes_only + (60 * restricted_hours_only);
            } else { //start before restricted and end after restricted
                restricted_hours_only = 10;
                unrestricted_hours_only = ((10 * end_hour_1_int) + end_hour_2_int) - ((10 * start_hour_1_int) + start_hour_2_int) - restricted_hours_only;
                if((10 * end_min_1_int) + end_min_2_int < (10 * start_min_1_int) + start_min_2_int) { //less than one hour difference in minutes
                    unrestricted_minutes_only = ((10 * end_min_1_int) + end_min_2_int + 60) - ((10 * start_min_1_int) + start_min_2_int);
                    unrestricted_hours_only -= 1;
                } else {
                    unrestricted_minutes_only = ((10 * end_min_1_int) + end_min_2_int) - ((10 * start_min_1_int) + start_min_2_int);
                }
                restricted_minutes_parked = restricted_minutes_only + (60 * restricted_hours_only);
                unrestricted_minutes_parked = unrestricted_minutes_only + (60 * unrestricted_hours_only);
            }
        
    } else { //Not within restricted period or not within restricted area
        unrestricted_hours_only = ((10 * end_hour_1_int) + end_hour_2_int) - ((10 * start_hour_1_int) + start_hour_2_int);
        if(unrestricted_hours_only == 0) {
            unrestricted_minutes_only = ((10 * end_min_1_int) + end_min_2_int) - ((10 * start_min_1_int) + start_min_2_int);
        } else if((10 * end_min_1_int) + end_min_2_int < (10 * start_min_1_int) + start_min_2_int) {//less than one hour difference in minutes
            unrestricted_minutes_only = ((10 * end_min_1_int) + end_min_2_int + 60) - ((10 * start_min_1_int) + start_min_2_int);
            unrestricted_hours_only -= 1;
        } else {
            unrestricted_minutes_only = ((10 * end_min_1_int) + end_min_2_int) - ((10 * start_min_1_int) + start_min_2_int);
        }
            unrestricted_minutes_parked = unrestricted_minutes_only + (60 * unrestricted_hours_only);
    }
    //Get price
    double unrestricted_price = unrestricted_rate * unrestricted_minutes_parked;
    double restricted_price = restricted_rate * restricted_minutes_parked;
    double num = unrestricted_price + restricted_price;
    BigDecimal bd = new BigDecimal(num).setScale(2, RoundingMode.HALF_UP);  
    double sum = bd.doubleValue();  
    
    return sum;
    }
}
