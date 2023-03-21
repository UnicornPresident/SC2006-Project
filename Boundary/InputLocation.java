import java.util.*;
import java.io.IOException;

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
        boolean validPostalcode=false;
        while (!validPostalcode){
            System.out.println("Please enter the Postal Code of your location.");
            this.postalCode=scan.next();
            validPostalcode=validate();
        }
    }
    //validate function to check that input is a 6-digit and no alphabets in the postal code
    @Override
    public boolean validate() {
        int length=this.postalCode.length();
        if (length!=6) return false;
        else if (this.postalCode.matches("[a-zA-Z]+")) return false;
        else return true;
    }
}
