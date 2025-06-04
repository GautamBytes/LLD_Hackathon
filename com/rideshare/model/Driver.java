package com.rideshare.model;

import com.rideshare.observer.Observer;

/**
 * Driver class (implements Observer to receive ride notifications).
 * Includes rating, availability, vehicle type, and capacity (for carpool).
 */
public class Driver extends User implements Observer {
    private double rating;
    private boolean available = true;
    private RideType vehicleType;
    private int capacity; // number of riders this driver can take at once

    public Driver(String name, String phone, Location location, double rating, RideType vehicleType) {
        super(name, phone, location);
        this.rating = rating;
        this.vehicleType = vehicleType;
        this.capacity = determineCapacity(vehicleType);
    }

    private int determineCapacity(RideType type) {
        switch (type) {
            case BIKE:
                return 1;
            case SEDAN:
                return 4;
            case SUV:
                return 6;
            case AUTORICKSHAW:
                return 3;
            default:
                return 1;
        }
    }

    public double getRating() {
        return rating;
    }

    public void setRating(double rating) {
        this.rating = rating;
    }

    public boolean isAvailable() {
        return available;
    }

    public void setAvailable(boolean avail) {
        this.available = avail;
    }

    public RideType getVehicleType() {
        return vehicleType;
    }

    public int getCapacity() {
        return capacity;
    }

    @Override
    public void update(String message) {
        System.out.println("Driver " + name + " receives notification: " + message);
    }
}
