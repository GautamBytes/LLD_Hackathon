package com.rideshare.model;

import com.rideshare.observer.Observer;
import com.rideshare.observer.Subject;

import java.util.ArrayList;
import java.util.List;

/**
 * Ride entity that connects one Rider and one Driver (with optional carpool flag).
 * Implements Subject for notifying observers (Rider, Driver, and any others).
 */
public class Ride implements Subject {
    private static int idCounter = 1;
    private int id;
    private Rider rider;
    private Driver driver;
    private Location pickup;
    private Location dropoff;
    private RideType type;
    private RideMode mode;
    private RideStatus status;
    private double fare; // final fare, set at completion
    private List<Observer> observers = new ArrayList<>();

    public Ride(Rider rider, Driver driver, Location pickup, Location dropoff, RideType type, RideMode mode) {
        this.id = idCounter++;
        this.rider = rider;
        this.driver = driver;
        this.pickup = pickup;
        this.dropoff = dropoff;
        this.type = type;
        this.mode = mode;
        this.status = RideStatus.REQUESTED;
    }

    public int getId() {
        return id;
    }

    public Rider getRider() {
        return rider;
    }

    public Driver getDriver() {
        return driver;
    }

    public Location getPickup() {
        return pickup;
    }

    public Location getDropoff() {
        return dropoff;
    }

    public RideType getType() {
        return type;
    }

    public RideMode getMode() {
        return mode;
    }

    public RideStatus getStatus() {
        return status;
    }

    public double getDistance() {
        // Euclidean distance (for demonstration)
        return pickup.distanceTo(dropoff);
    }

    public double getFare() {
        return fare;
    }

    public void setFare(double fare) {
        this.fare = fare;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
        notifyObservers("Ride " + id + " status changed to " + status);
    }

    @Override
    public void registerObserver(Observer o) {
        observers.add(o);
    }

    @Override
    public void removeObserver(Observer o) {
        observers.remove(o);
    }

    @Override
    public void notifyObservers(String message) {
        for (Observer o : observers) {
            o.update(message);
        }
    }
}
