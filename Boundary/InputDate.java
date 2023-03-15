package carparkpkg;
import java.text.ParseException;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Scanner;


public class InputDate implements InputController{
	//static SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
    private LocalDate start_date = null;
    private LocalDate end_date = null;
  
    //method to get user input and verify that start and end dates are valid using date controller class, if all are valid get time 

	@Override 
	public void input()throws ParseException{
		// TODO Auto-generated method stub
		
        Scanner sc = new Scanner(System.in);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        
        System.out.println("Enter start date (dd/mm/yyyy):");
        String cinput1 = sc.nextLine();
        start_date = LocalDate.parse(cinput1, formatter);
      
        System.out.print(start_date); //checking
        
        System.out.println("Enter end date (dd/mm/yyyy):");
        String cinput2 = sc.nextLine();
        end_date = LocalDate.parse(cinput2, formatter);
        
        System.out.print(end_date); //checking
        
        DateController dc = new DateController(start_date, end_date);
        if (dc.validate() == false) {
        	System.out.println("Invalid dates, please enter valid start and end dates in the correct format");
        	input();
        }else {
        	InputTime time = new InputTime();
        	time.input();
        }
       
        
	}
	
	// to get start and end dates 
	
	public LocalDate getStartDate() {
		return start_date;
	}
	public LocalDate getEndDate() {
		return end_date;
	}

	


}
