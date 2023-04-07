package Controller;
import java.io.IOException;
import java.time.LocalDate;
import java.util.*;
import java.time.*;
import java.math.*;
import java.math.BigDecimal;
import java.text.ParseException;
import java.io.BufferedReader;
import java.io.FileReader;

public class PriceController {

    public PriceController() {

    }

    public double getPrice(String carparkId, LocalDate start_date, LocalDate end_date, LocalTime start_time, LocalTime end_time, ArrayList<String> dateTypes) throws ParseException {
        String start_time_input = "", end_time_input = "";
        Boolean restricted = false;

        // System.out.println("START_TIME FROM UI: " + start_time);

        // System.out.println("END_TIME FROM UI: " + end_time);

        //Initialising time integers
        start_time_input = start_time.toString();
        // System.out.println("START_TIME_INPUT: " + start_time_input);

        char start_hour_1 = start_time_input.charAt(0);
        int start_hour_1_int = start_hour_1;
        start_hour_1_int -= 48;
        char start_hour_2 = start_time_input.charAt(1);
        int start_hour_2_int = start_hour_2;
        start_hour_2_int -= 48;

        char start_min_1 = start_time_input.charAt(3);
        int start_min_1_int = start_min_1;
        start_min_1_int -= 48;
        char start_min_2 = start_time_input.charAt(4);
        int start_min_2_int = start_min_2;
        start_min_2_int -= 48;

        end_time_input = end_time.toString();
        // System.out.println("END_TIME_INPUT: " + end_time_input);

        char end_hour_1 = end_time_input.charAt(0);
        int end_hour_1_int = end_hour_1;
        end_hour_1_int -= 48;
        char end_hour_2 = end_time_input.charAt(1);
        int end_hour_2_int = end_hour_2;
        end_hour_2_int -= 48;

        char end_min_1 = end_time_input.charAt(3);
        int end_min_1_int = end_min_1;
        end_min_1_int -= 48;
        char end_min_2 = end_time_input.charAt(4);
        int end_min_2_int = end_min_2;
        end_min_2_int -= 48;

        System.out.println("start_hour_1: " + start_hour_1_int + " start_hour_2_int: " + start_hour_2_int + " end_hour_1_int: " + end_hour_1_int + " end_hour_2_int: " + end_hour_2_int);

        //Get restricted
        String line = " ";
        String splitBy = ",";
        try {
            BufferedReader br = new BufferedReader(new FileReader("src/main/java/Controller/hdb-carpark-information.csv"));
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


        int noOfDays = dateTypes.size();
        LocalTime sevenAM = LocalTime.parse("07:00");
        LocalTime fivePM = LocalTime.parse("17:00");
        double totalRestrictedHours = 0;
        double totalUnrestrictedHours = 0;
        double totalCost = 0;
        for (int i = 0 ; i < noOfDays ; i++){
            // skips the day if public holiday/sunday - free parking
            if (dateTypes.get(i).equals("Sunday") || dateTypes.get(i).equals("Public Holiday"))
                continue;

            
            if (i == 0){
                // if only 1 day in carpark
                if (restricted && noOfDays == 1) {
                    // entire period is within restricted 7am - 5pm
                    if (start_time.isAfter(sevenAM) && end_time.isBefore(fivePM)) {
                        if (start_min_1_int < 3){ // count entire first hour
                            if (end_min_1_int > 2) { // count entire last hour
                                totalRestrictedHours += (end_hour_1_int*10+end_hour_2_int) - (start_hour_1_int*10+start_hour_2_int) + 0.5;
                            }
                            else if (end_min_1_int == 0)
                                totalRestrictedHours += (end_hour_1_int*10+end_hour_2_int) - (start_hour_1_int*10+start_hour_2_int);
                            else { //count only first half of last hour
                                totalRestrictedHours += (end_hour_1_int*10+end_hour_2_int) - (start_hour_1_int*10+start_hour_2_int) + 1;
                            }
                        }
                        else { // count 2nd half of first hour
                            if (end_min_1_int > 2) { // count entire last hour
                                totalRestrictedHours += (end_hour_1_int*10+end_hour_2_int) - (start_hour_1_int*10+start_hour_2_int);
                            }
                            else if (end_min_1_int == 0)
                                totalRestrictedHours += (end_hour_1_int*10 + end_hour_2_int) - (start_hour_1_int*10 + start_hour_2_int) - 0.5;
                            else { //count only first half of last hour
                                totalRestrictedHours += (end_hour_1_int*10+end_hour_2_int) - (start_hour_1_int*10+start_hour_2_int) + 0.5;
                            }
                        }
                    }
                    // starts before 7am, ends before 5pm
                    else if(start_time.isBefore(sevenAM) && end_time.isBefore(fivePM)){
                        if (start_min_1_int < 3) // count entire hour
                            totalUnrestrictedHours += 7 - start_hour_2_int;
                        else // count only 2nd half of first hour
                            totalRestrictedHours += 7 - start_hour_2_int - 0.5;

                        if (end_min_1_int < 3 && end_min_1_int > 0) { // only count first half of last hour
                            totalRestrictedHours += end_hour_1_int*10+end_hour_2_int - 7 - 0.5;
                        }
                        else { // count entire last hour
                            totalRestrictedHours += end_hour_1_int*10+end_hour_2_int - 7;
                        }
                    }
                    // starts after 7am, ends after 5pm
                    else if (start_time.isAfter(sevenAM) && end_time.isAfter(fivePM)){
                        if (end_min_1_int < 3 && end_min_1_int > 0){ // count only first half of last hour
                            totalUnrestrictedHours += end_hour_1_int*10+end_hour_2_int - 17 + 0.5;
                        }
                        else { // count entire last hour
                            totalUnrestrictedHours += end_hour_1_int*10+end_hour_2_int - 17 + 1;
                        }

                        if (start_min_1_int < 3){ // count entire first hour
                            totalRestrictedHours += 10 - (start_hour_1_int*10+start_hour_2_int - 7);
                        }
                        else { // count only 2nd half of first hour
                            totalRestrictedHours += 10 - (start_hour_1_int*10+start_hour_2_int - 7) - 0.5;
                        }
                    }
                    // starts before 7am, ends after 5pm
                    else{
                        if (start_min_1_int < 3) // count entire hour
                            totalUnrestrictedHours += 7 - start_hour_2_int;
                        else // count only 2nd half of first hour
                            totalRestrictedHours += 7 - start_hour_2_int - 0.5;

                        if (end_min_1_int < 3 && end_min_1_int > 0){ // count only first half of last hour
                            totalUnrestrictedHours += end_hour_1_int*10+end_hour_2_int - 17 + 0.5;
                        }
                        else { // count entire last hour
                            totalUnrestrictedHours += end_hour_1_int*10+end_hour_2_int - 17 + 1;
                        }

                        totalRestrictedHours += 10;
                    }
                }
                //unrestricted and only parking 1 day
                else if (!restricted && noOfDays == 1){
                    if (start_min_1_int < 3){ // count entire first hour
                        if (end_min_1_int < 3 && end_min_1_int > 0) {// count only first half of last hour
                            totalUnrestrictedHours += (end_hour_1_int*10+end_hour_2_int) - (start_hour_1_int*10+start_hour_2_int) + 0.5;
                            System.out.println("test1");
                        }
                        else if (end_min_1_int == 0){
                            totalUnrestrictedHours += (end_hour_1_int*10 + end_hour_2_int) - (start_hour_1_int*10 + start_hour_2_int);
                            System.out.println("test2");
                        }
                        else {// count entire last hour
                            totalUnrestrictedHours += (end_hour_1_int*10+end_hour_2_int) - (start_hour_1_int*10+start_hour_2_int) + 1;
                            System.out.println("test3");
                        }
                    }
                    else{ // count second half of first hour
                        if (end_min_1_int < 3 && end_min_1_int > 0) // count only first half of last hour
                            totalUnrestrictedHours += (end_hour_1_int*10+end_hour_2_int) - (start_hour_1_int*10+start_hour_2_int);
                        else if (end_min_1_int == 0)
                            totalUnrestrictedHours += (end_hour_1_int*10+end_hour_2_int) - (start_hour_1_int*10+start_hour_2_int) - 0.5;
                        else // count entire last hour
                            totalUnrestrictedHours += (end_hour_1_int*10+end_hour_2_int) - (start_hour_1_int*10+start_hour_2_int) + 0.5;
                    }
                }
            }
            // last day
            else if (i != 0 && i == noOfDays-1){
                if (restricted) {
                    // ends before 7am
                    if (end_time.isBefore(sevenAM)){
                        if (end_min_1_int < 3 && end_min_1_int > 0) // count only first half of last hour
                            totalUnrestrictedHours += end_hour_1_int*10+end_hour_2_int + 0.5;
                        else if (end_min_1_int == 0)
                            totalUnrestrictedHours += end_hour_1_int*10+end_hour_2_int;
                        else // count entire last hour
                            totalUnrestrictedHours += end_hour_1_int*10+end_hour_2_int + 1;
                    }
                    // ends between 7am and 5pm
                    else if (end_time.isBefore(fivePM)){
                        totalUnrestrictedHours += 7;
                        if (end_min_1_int < 3 && end_min_1_int > 0) // count only first half of last hour
                            totalRestrictedHours += end_hour_1_int*10+end_hour_2_int - 7 + 0.5;
                        else if (end_min_1_int == 0)
                            totalRestrictedHours += end_hour_1_int*10+end_hour_2_int - 7;
                        else // count entire last hour
                            totalRestrictedHours += end_hour_1_int*10+end_hour_2_int - 7 + 1;
                    }
                    // ends after 5pm
                    else {
                        totalRestrictedHours += 10;
                        if (end_min_1_int < 3 && end_min_1_int > 0) // count only first half of last hour
                            totalUnrestrictedHours += 7 + end_hour_1_int*10+end_hour_2_int - 15 + 0.5;
                        else if (end_min_1_int == 0)
                            totalUnrestrictedHours += 7 + end_hour_1_int*10+end_hour_2_int - 15;
                        else // count entire last hour
                            totalUnrestrictedHours += 7 + end_hour_1_int*10+end_hour_2_int - 15 + 1;
                    }
                }
                else {
                    if (end_min_1_int < 3 && end_min_1_int > 0) // count only first half of last hour
                        totalUnrestrictedHours += end_hour_1_int*10+end_hour_2_int + 0.5;
                    else if (end_min_1_int == 0)
                        totalUnrestrictedHours += end_hour_1_int*10+end_hour_2_int;
                    else // count entire last hour
                        totalUnrestrictedHours += end_hour_1_int*10+end_hour_2_int + 1;
                }
            }
            // other days (full 24 hour days)
            else{
                if (restricted){
                    totalRestrictedHours += 10;
                    totalUnrestrictedHours += 14;
                }
                else{
                    totalUnrestrictedHours += 24;
                }
            }

        }
        System.out.println("totalUnrestrictedHours = " + totalUnrestrictedHours);
        System.out.println("totalRestrictedHours = " + totalRestrictedHours);

        double sum = totalRestrictedHours*1.2*2 + totalUnrestrictedHours*0.6*2;

        
    BigDecimal bd = new BigDecimal(sum).setScale(2, RoundingMode.HALF_UP);  
    totalCost = bd.doubleValue();  
    
    return totalCost;
    }
}
