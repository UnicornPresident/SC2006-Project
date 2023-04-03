import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.time.*;
import java.lang.*;

public class PriceController {
    private LocalTime start_time, end_time;
    private LocalDate start_date, end_date;

    public PriceController() {

    }

    public double getPrice() {
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter Dateformatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        DateTimeFormatter Timeformatter = DateTimeFormatter.ofPattern("HH:mm");
        LocalDate start_date = null;
        LocalDate end_date = null;
        LocalTime start_time = null;
        LocalTime end_time = null;
        String start_date_input, end_date_input;
        String start_time_input, end_time_input;

        //Get date inputs
        DateController dc = new DateController(start_date, end_date);
        while(!dc.validate) {
            System.out.println("Start date (dd/MM/yyyy): ");
            start_date_input = sc.nextLine();
            start_date = LocalDate.parse(start_date_input, Dateformatter);
            System.out.println("End date (dd/MM/yyyy): ");
            end_date_input = sc.nextLine();
            end_date = LocalDate.parse(end_date_input, Dateformatter);
        }

        //Get time inputs
        TimeController tc = new TimeController(start_time, end_time);
        while(!tc.validate) {
            System.out.println("Start time (HH/mm): ");
            start_time_input = sc.nextLine();
            start_time = LocalTime.parse(start_time_input, Timeformatter);
            System.out.println("End time (HH/mm): ");
            end_time_input = sc.nextLine();
            end_time = LocalTime.parse(end_time_input, Timeformatter);
        }
        
        //Get date types
        ArrayList<String> dateTypes = dc.getDayTypes();

        //Initialising time integers
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

        //Get rate
        double rate = 0.02; // 2 cents a min for non restricted
        if(start_hour_1_int == 0 && start_hour_2_int > 7 && end_hour_1_int == 1 && end_hour_2_int < 7) {
            rate = 0.04;
        }

        //Get time parked
        int minutes_parked = 0;
        int hours_only = 0;
        int minutes_only = 0;

        hours_only = ((10 * end_hour_1_int) + end_hour_2_int) - ((10 * start_hour_1_int) + start_hour_2_int);
        
        if(hours_only == 0) {
            minutes_only = ((10 * end_min_1_int) + end_min_2_int) - ((10 * start_min_1_int) + start_min_2_int);
        } else {
            minutes_only = ((10 * end_min_1_int) + end_min_2_int + 60) - ((10 * start_min_1_int) + start_min_2_int);
        }

        minutes_parked = minutes_only + (60 * hours_only);

        //Get price
        double price = rate * minutes_parked;
    }

}
