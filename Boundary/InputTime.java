package carparkpkg;
import java.util.Scanner;
import java.text.ParseException;
import java.time.LocalTime;


public class InputTime implements InputController{
	private LocalTime start_time = null;
	private LocalTime end_time = null;
	@Override
	public void input() throws ParseException {
		// TODO Auto-generated method stub
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter start time in HH:mm format: ");
		String sinput = sc.nextLine();
		start_time = LocalTime.parse(sinput);
		System.out.println("You entered: " + start_time);
		
		System.out.print("Enter end time in HH:mm format: ");
		String einput = sc.nextLine();
		end_time = LocalTime.parse(einput);
		System.out.println("You entered: " + end_time);
		
		TimeController tc = new TimeController();
		if (tc.validate()== false) {
			System.out.println("Invalid duration, please enter valid start and end time.");
			input(); 
		}else {
			//viewmap.displayResult();
		}
		
	}
	public LocalTime getStartTime() {
		return start_time;
	}
	public LocalTime getEndTime() {
		return end_time;
	}
	

}
