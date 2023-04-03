package Boundary;
import Controller.*;
import java.util.Scanner;
import java.text.ParseException;
import java.time.*;



public class InputTime implements InputController{
	private LocalTime start_time = null;
	private LocalTime end_time = null;
	// @Override
	public void input(LocalDate start_date, LocalDate end_date) throws ParseException {
		Scanner sc = new Scanner(System.in);
		Boolean pass = false;
		while (pass == false) {
			try{
				System.out.print("Enter start time in HH:mm format (e.g. 18:30): ");
				String sinput = sc.nextLine();
				start_time = LocalTime.parse(sinput);
				pass = true;
			} catch (Exception e) {
				System.out.println("Please re-enter a valid start time in HH:mm format (e.g. 18:30): ");
			}
		}
		pass = false;
		while (pass == false) {
			try{
				System.out.print("Enter end time in HH:mm format (e.g. 18:30): ");
				String einput = sc.nextLine();
				end_time = LocalTime.parse(einput);
				pass = true;
			} catch (Exception e) {
				System.out.println("Please re-enter a valid end time in HH:mm format (e.g. 18:30): ");
			}
		}

		
		TimeController tc = new TimeController(start_time, end_time);
		if (tc.validate()== false) {
			System.out.println("Invalid duration, please enter valid start and end time.");
			input(start_date, end_date); 
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
	public boolean validate(){return true;}

	public void input(){}
}
