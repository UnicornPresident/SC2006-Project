package Boundary;
import java.util.*;

public class InputAddress implements InputController {

    private String address;
    private static Scanner sc = new Scanner(System.in);
    public InputAddress(){
    }

    public String getAddress(){
        return address;
    }

    public void setAddress(String address){
        this.address = address;
    }

    //Requests for an input of the address of carpark to be searched for
    // @Override
    public void input(){   

        boolean validString = false;
        while (validString == false) {
            System.out.println("Please enter the address of the carpark to be searched for.");
            this.address = sc.next();
            validString=validate();
        }
    }

    //validate function to check that the input is a valid string
    // @Override
    public boolean validate() {
        if (this.address.matches("[a-zA-Z0-9]+")) return true;
        else return false;
    }
}