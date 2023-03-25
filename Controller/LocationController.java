
import java.io.IOException;

public class LocationController {
    public LocationController() {
    }

    public static void main(String[] args) throws IOException {
        LatLng latLng;
        InputLocation locationinput= new InputLocation();
        locationinput.input();
        LocationApi locationapi= new LocationApi(locationinput.getpostalCode());
        locationapi.urlconstructor();
        latLng=locationapi.GetLatLng();
        locationinput.setLatLng(latLng);
        if(locationapi.GetLatLng().getLatitude()==404 && locationapi.GetLatLng().getLongitude()==404  ){
            System.out.println("The Address does not exist!.");
        }
        else{
            System.out.println("lat: " + latLng.getLatitude());
            System.out.println("lng: " + latLng.getLongitude());
        }
        double maxDist = 5000; // in meters
        AvailabilityApi availabilityApi = new AvailabilityApi();
        availabilityApi.getAvailability();
        availabilityApi.getNearestCarparks(maxDist, latLng);
        // mallApi.displayAllCarparks();
        availabilityApi.sortByAvailability();
        // availabilityApi.sortByDistance();
        availabilityApi.displayNearbyCarparks();
    }
}
