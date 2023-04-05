package com.example.application.views.main;

public class ParkingData {
    private String address;
    private int availableLots;
    private double price;
    private double distance;

    public ParkingData(String address, int availableLots, double price, double distance) {
        this.address = address;
        this.availableLots = availableLots;
        this.price = price;
        this.distance = distance;
    }

    public String getAddress() {
        return address;
    }

    public int getAvailableLots() {
        return availableLots;
    }

    public double getPrice() {
        return price;
    }

    public double getDistance(){
        return distance;
    }
}
