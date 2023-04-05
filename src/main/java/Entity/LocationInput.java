package Entity;
public class LocationInput {
    private String postalCode;
    private double xCoord;
    private double yCoord;

    public LocationInput() {
        this.postalCode = "";
        this.xCoord = 0;
        this.yCoord = 0;
    }

    public LocationInput(String postalCode) {
        this.postalCode = postalCode;
        this.xCoord = 0;
        this.yCoord = 0;
    }

    public double[] inputLocation(String postalCode) {
        double x = 0,y = 0;
        //Logic to convert postalCode to coordinates
        x = 1;
        y = 1;
        double[] coords = {x,y};
        return coords;
    }

    public double getXCoord() {
        return this.xCoord;
    }

    public double getYCoord() {
        return this.yCoord;
    }

    public String getPostalCode() {
        return this.postalCode;
    }

}