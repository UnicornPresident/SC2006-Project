import java.util.*;

public class InputLocation implements InputController{

    private String postalCode;
    private LatLng latLng;

    public LatLng getLatLng() {
        return latLng;
    }

    public void setLatLng(LatLng latLng) {
        this.latLng = latLng;
    }

    public InputLocation() {}

    public String getpostalCode() {
        return this.postalCode;
    }

    public void setpostalCode(String postalCode) {
        this.postalCode=postalCode;
    }


    //Repeatedly asks for user input for postal code until the input is valid.
    @Override
    public void input() {
        // TODO Auto-generated method stub
        Scanner scan = new Scanner(System.in);
        System.out.println("Please input your desired location:");
        String input = scan.nextLine();
        if (validatePostal(input)){
            this.postalCode = input;
        }
        else{
            input = input.replace(' ', '+');
            this.postalCode = input; 
        }
        scan.close();
    }
    //validate function to check that input is a 6-digit and no alphabets in the postal code
    public boolean validatePostal(String input) {
        int length=input.length();
        if (length!=6) return false;
        else if (input.matches("[a-zA-Z]+")) return false;
        else return true;
    }

    @Override
    public boolean validate(){return true;}
}
