package Controller;
import java.time.*;

public class TimeController extends Controller {
	private LocalTime start_time, end_time;

	public TimeController(){}

	public TimeController(LocalTime start_time, LocalTime end_time){
		this.start_time = start_time;
		this.end_time = end_time;
	}

	public void setStartTime(LocalTime start_time){
		this.start_time = start_time;
	}

	public LocalTime getStartTime(){
		return this.start_time;
	}

	public void setEndTime(LocalTime end_time){
		this.end_time = end_time;
	}

	public LocalTime getEndTime(){
		return this.end_time;
	}

	//validate that if start and end date is the same i.e. same day, the start time must be before the end time if not it is invalid 
	// @Override
	public boolean validate(LocalDate start_date, LocalDate end_date) {
		// InputTime input = new InputTime();
		// InputDate date = new InputDate();
		// start_time = input.getStartTime();
		// end_time = input.getEndTime();
		if ((start_date != end_date)) {
			return true;
		}else if ((start_date == end_date)&& this.start_time.compareTo(this.end_time)<0) {
			return true;
		}else {
			return false;
		}
		
	}

	@Override
	public boolean validate(){return true;}
}
