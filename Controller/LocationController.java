
import java.io.IOException;
import java.util.*;

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
            System.out.println(locationapi.GetLatLng().getLatitude());
            System.out.println(locationapi.GetLatLng().getLongitude());
        }
        InputAddress addressInput = new InputAddress();
        addressInput.input();;
        SearchApi searchApi = new SearchApi(addressInput.getAddress());
        searchApi.urlconstructor();
        ArrayList<String[]> searchResults = searchApi.getSearchResults();
        AvailabilityApi availabilityApi = new AvailabilityApi(searchResults);
        availabilityApi.getAvailability();
        availabilityApi.display();
    }
}
