import java.time.LocalDate;
import java.util.ArrayList;

public class DateController extends Controller{
	private LocalDate start_date = null;
	private LocalDate end_date = null;
	
	public DateController(LocalDate start_date, LocalDate end_date) {
		this.start_date = start_date;
		this.end_date = end_date;
	}
	
	//validate start date is before end date
	public boolean validate() {
		if (start_date.isBefore(end_date)) {
			return true;
		}else{
			return false;
		}
		
	}
	
	//get day times between start and end dates
	
	public ArrayList<String> getDayTypes(){
		CalendarDb cal = new CalendarDb();
		return cal.dateType(start_date, end_date);
	}
	
	

}
