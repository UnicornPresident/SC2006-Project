package carparkpkg;
import java.time.*;
public class TimeController extends Controller {
	private LocalTime start_time, end_time;

	//validate that if start and end date is the same i.e. same day, the start time must be before the end time if not it is invalid 
	@Override
	public boolean validate() {
		// TODO Auto-generated method stub
		InputTime input = new InputTime();
		InputDate date = new InputDate();
		start_time = input.getStartTime();
		end_time = input.getEndTime();
		if ((date.getStartDate() != date.getEndDate())) {
			return true;
		}else if ((date.getStartDate() == date.getEndDate())&& start_time.compareTo(end_time)<0) {
			return true;
		}else {
			return false;
		}
		
	}
}
