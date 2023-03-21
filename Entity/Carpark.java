
public class Carpark {
    private float cost;
    private float distance;
    private float availability;
    private String location;
    private String numberOfLots;
    private Price price;
    private String name;

    public Carpark() {
        this.cost = 0;
        this.distance = 0;
        this.availability = 0;
        this.location = "";
        this.numberOfLots = "";
        this.price = null;
        this.name = "";
    }

    public float getCost() {
        return this.cost;
    }
    
    public float getDistance() {
        return this.distance;
    }

    public float getAvailability() {
        return this.availability;
    }

    public String getLocation() {
        return this.location;
    }

    public String getNumberOfLots() {
        return this.numberOfLots;
    }

    public Price getPrice() {
        return this.price;
    }

    public String getName() {
        return this.name;
    }

    public void setCost(Price price) {
        //Logic to calculate cost based on price
    }

    public void setDistance(float dist) {
        this.distance = dist;
    }

    public void setAvailability(float avail) {
        this.availability = avail;
    }

}
