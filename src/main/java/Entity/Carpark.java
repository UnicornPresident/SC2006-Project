package Entity;

public class Carpark {
    private double cost;
    private float distance;
    private float availability;
    private String address;
    private String numberOfLots;
    private double price;
    private String number;
    private String restricted;
    private String x_coord;
    private String y_coord;

    public Carpark() {
        this.cost = 0;
        this.distance = 0;
        this.availability = 0;
        this.address = "";
        this.numberOfLots = "";
        this.price = 0;
        this.number = "";
        this.restricted= "";
        this.x_coord = "";
        this.y_coord = "";
        }

    public Carpark(String number, String address, String x_coord, String y_coord, String restricted) {
        this.cost = 0;
        this.distance = 0;
        this.availability = 0;
        this.address = address;
        this.numberOfLots = "";
        this.price = 0;
        this.number = number;
        this.restricted = restricted;
        this.x_coord = x_coord;
        this.y_coord = y_coord;
    }

    public double getCost() {
        return this.cost;
    }
    
    public float getDistance() {
        return this.distance;
    }

    public float getAvailability() {
        return this.availability;
    }

    public String getNumberOfLots() {
        return this.numberOfLots;
    }

    public double getPrice() {
        return this.price;
    }


    public String getNumber() {
        return this.number;
    }

    public String getRestricted() {
        return this.restricted;
    }

    public String getX_coord() {
        return this.x_coord;
    }

    public String getY_coord() {
        return this.y_coord;
    }
    public void setCost(double price) {
        //Logic to calculate cost based on price
        
    }

    public void setDistance(float dist) {
        this.distance = dist;
    }

    public void setAvailability(float avail) {
        this.availability = avail;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public void setPrice(String restricted) {
        if(restricted == "Y") {
            this.price = 0.04;
        } else {
            this.price = 0.02;
        }
    }

}

    // public String getNumberOfLots() {
    //     return this.numberOfLots;
    // }

    // public Price getPrice() {
    //     return this.price;
    // }

    // public String getName() {
    //     return this.name;
    // }

    // public void setCost(Price price) {
    //     //Logic to calculate cost based on price
    // }

    // public void setDistance(float dist) {
    //     this.distance = dist;
    // }

    // public void setAvailability(float avail) {
    //     this.availability = avail;
    // }

// }
