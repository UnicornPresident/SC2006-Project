package Controller;
import Boundary.*;
import Entity.*;
import java.io.IOException;
import java.text.ParseException;
import java.util.*;

public class LocationController {
    public LocationController() {
    }

    public static void main(String[] args) throws IOException, ParseException {
        Scanner sc = new Scanner(System.in);
        LatLng latLng;
        InputLocation locationinput= new InputLocation();
        locationinput.input();
        LocationApi locationapi= new LocationApi(locationinput.getpostalCode());
        locationapi.urlconstructor();
        latLng=locationapi.GetLatLng();
        locationinput.setLatLng(latLng);
        while(locationinput.getLatLng().getLatitude()==404 && locationinput.getLatLng().getLongitude()==404){
            System.out.println("The Address does not exist!.");
            System.out.println("Please re-enter a more specfic location/different postal code:");
            locationinput.setpostalCode(sc.nextLine());
            locationapi.setAddress(locationinput.getpostalCode());
            locationapi.urlconstructor();
            latLng = locationapi.GetLatLng();
            locationinput.setLatLng(latLng);
        }
        
        System.out.println("lat: " + latLng.getLatitude());
        System.out.println("lng: " + latLng.getLongitude());
        System.out.println("Please input the max distance (in meters) to search for:");
        double maxDist = 0; // in meters
        while(maxDist == 0) {
            if (sc.hasNextDouble()){
                maxDist = sc.nextDouble();
                if (maxDist > 0) {
                    break;
                }
                else {
                    maxDist = 0;
                    System.out.println("Please re-input a valid distance:");  
                }    
            }
            else{
                System.out.println("Please re-input a valid distance:");
                sc.next();
            }
        }
        
        // while (maxDist == 0){
        //     if (sc.hasNext()){
        //         maxDist = sc.nextDouble();
        //         break;
        //     }
        //     else
        //         System.out.println("Please re-input a valid distance:");
        // }
        AvailabilityApi availabilityApi = new AvailabilityApi();
        availabilityApi.getAvailability();
        availabilityApi.getNearestCarparks(maxDist, latLng);
        System.out.println("Please choose sortByDistance-(0) or sortByAvailability-(1):");
        int input = 20;
        while (input != 1 && input != 0){
            if (sc.hasNextInt()){
                input = sc.nextInt();
                if (input == 0 || input == 1){
                    break;
                }
                else{
                    System.out.println("Please re-input a valid option:");
                }
            }
            else{
                System.out.println("Please re-input a valid option:");
                sc.next();
            }
        }
        if (input == 1){
            availabilityApi.sortByDistance();
        }
        else
            availabilityApi.sortByAvailability();

        availabilityApi.displayNearbyCarparks();
    }
}
