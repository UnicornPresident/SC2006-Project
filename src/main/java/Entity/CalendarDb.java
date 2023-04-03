package Entity;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Set;
import java.util.ArrayList;
import de.jollyday.Holiday;
import de.jollyday.HolidayCalendar;
import de.jollyday.HolidayManager;

 
public class CalendarDb {
	private ArrayList<String> dateTypes = new ArrayList<String>();
	private String countryCode = "SG";
	
	public ArrayList<String> dateType(LocalDate start_date, LocalDate end_date) {
		HolidayManager holidayManager = HolidayManager.getInstance(HolidayCalendar.valueOf(countryCode));
		int start_year = start_date.getYear();
		int end_year = end_date.getYear();
		Set<Holiday> holidays = holidayManager.getHolidays(start_year);
		holidays.addAll(holidayManager.getHolidays(end_year));
		System.out.println(holidays);
		for(LocalDate date = start_date; date.isBefore(end_date); date.plusDays(1)) {
			String day =  date.format(DateTimeFormatter.ofPattern("EEEE"));
			for (Holiday holiday: holidays) {
				if(date.equals(holiday.getDate())){
					day = "Public Holiday";
					break;
				}
			}
			dateTypes.add(day);
		}
		return dateTypes;
		
	}
	
	
	

}
