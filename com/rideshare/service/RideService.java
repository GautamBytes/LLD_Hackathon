package com.rideshare.service;

import com.rideshare.model.*;
import com.rideshare.strategy.MatchingStrategy;
import com.rideshare.strategy.MatchingStrategyFactory;
import com.rideshare.decorator.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Singleton service to manage riders, drivers, and rides.
 * Demonstrates: Singleton, Strategy, Observer, Decorator patterns.
 */
public class RideService {
    private static RideService instance = new RideService();

    private List<Driver> drivers = new ArrayList<>();
    private List<Rider> riders = new ArrayList<>();
    private List<Ride> ongoingRides = new ArrayList<>();
    private List<Ride> rideHistory = new ArrayList<>();

    private RideService() { }

    public static RideService getInstance() {
        return instance;
    }

    // ========== User Management ==========
    public void registerRider(Rider rider) {
        riders.add(rider);
        System.out.println("Registered Rider: " + rider.getName() + " (" + rider.getPhone() + ")");
    }

    public void registerDriver(Driver driver) {
        drivers.add(driver);
        System.out.println("Registered Driver: " + driver.getName() + " [" + driver.getVehicleType() + 
                           ", Rating: " + driver.getRating() + ", Capacity: " + driver.getCapacity() + "]");
    }

    public void removeDriver(Driver driver) {
        drivers.remove(driver);
        System.out.println("Driver " + driver.getName() + " removed from service.");
    }

    public void removeRider(Rider rider) {
        riders.remove(rider);
        System.out.println("Rider " + rider.getName() + " removed from service.");
    }

    // ========== Ride Booking ==========
    /**
     * Rider requests a ride.
     * @param rider    the Rider requesting
     * @param pickup   pickup location
     * @param dropoff  dropoff location
     * @param type     desired vehicle type
     * @param mode     NORMAL or CARPOOL
     * @param strategy string key ("nearest" or "highestRated")
     * @return Ride if matched, or null if no driver available
     */
    public Ride requestRide(Rider rider, Location pickup, Location dropoff,
                            RideType type, RideMode mode, String strategy) {
        System.out.println("\n=== Ride Request ===");
        System.out.println(rider.getName() + " requests a " + type + " ride (Mode: " + mode +
                           ") from " + pickup + " to " + dropoff);

        MatchingStrategy matchAlgo = MatchingStrategyFactory.getStrategy(strategy);
        if (matchAlgo == null) {
            System.out.println("Invalid matching strategy: " + strategy);
            return null;
        }

        Driver matchedDriver = matchAlgo.findDriver(rider, pickup, type, mode, drivers);
        if (matchedDriver == null) {
            System.out.println("No available driver found for this ride.");
            return null;
        }

        // Assign ride
        matchedDriver.setAvailable(false);
        Ride ride = new Ride(rider, matchedDriver, pickup, dropoff, type, mode);
        ride.registerObserver(rider);
        ride.registerObserver(matchedDriver);
        ongoingRides.add(ride);

        // Notify that driver has accepted
        matchedDriver.update("You have been assigned Ride ID " + ride.getId() + ". Please proceed to pickup.");
        rider.update("Driver " + matchedDriver.getName() + " (Rating: " + matchedDriver.getRating() + 
                     ") has accepted your ride. Driver phone: " + matchedDriver.getPhone());
        ride.setStatus(RideStatus.ACCEPTED);
        return ride;
    }

    // ========== Ride Progression & Completion ==========
    /**
     * Simulates the ride lifecycle: EN_ROUTE_TO_PICKUP -> IN_PROGRESS -> COMPLETED,
     * calculates fare (with given FareCalculator), marks driver available, and notifies payment.
     */
    public void completeRide(Ride ride, FareCalculator fareCalculator) {
        System.out.println("\n=== Completing Ride ID: " + ride.getId() + " ===");
        // 1) EN_ROUTE_TO_PICKUP
        ride.setStatus(RideStatus.EN_ROUTE_TO_PICKUP);
        // Simulate travel toward pickup
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore for single-threaded demo
        }

        // 2) IN_PROGRESS (passenger onboard)
        ride.setStatus(RideStatus.IN_PROGRESS);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            // Ignore
        }

        // 3) COMPLETED
        ride.setStatus(RideStatus.COMPLETED);

        double fare = fareCalculator.calculateFare(ride);
        ride.setFare(fare);
        System.out.println("Ride " + ride.getId() + " completed. Fare calculated: " + fare);

        // Payment simulated as always successful
        ride.notifyObservers("Payment of " + fare + " received. Thank you!");

        // Mark driver available again
        Driver driver = ride.getDriver();
        driver.setAvailable(true);

        // Move ride from ongoing to history
        ongoingRides.remove(ride);
        rideHistory.add(ride);
    }

    /**
     * Demo main method to simulate a few ride requests.
     */
    public static void main(String[] args) {
        RideService service = RideService.getInstance();

        // Register some riders
        Rider alice = new Rider("Alice", "999-111-2222", new Location(0, 0));
        Rider bob   = new Rider("Bob",   "999-333-4444", new Location(5, 5));
        service.registerRider(alice);
        service.registerRider(bob);

        // Register some drivers
        Driver dave = new Driver("Dave",  "888-555-1234", new Location(1, 1), 4.9, RideType.SEDAN);
        Driver eve  = new Driver("Eve",   "888-555-5678", new Location(2, 2), 4.8, RideType.SUV);
        Driver frank = new Driver("Frank","888-555-9999", new Location(0, 1), 4.7, RideType.AUTORICKSHAW);
        service.registerDriver(dave);
        service.registerDriver(eve);
        service.registerDriver(frank);

        // 1) Alice requests a NORMAL Sedan ride using "nearest" strategy
        Ride ride1 = service.requestRide(
            alice,
            new Location(0, 0),
            new Location(3, 3),
            RideType.SEDAN,
            RideMode.NORMAL,
            "nearest"
        );
        if (ride1 != null) {
            // Base fare + 1.2x surge
            FareCalculator baseFare1 = new BaseFareCalculator();
            FareCalculator surgeFare1 = new SurgePricingDecorator(baseFare1, 1.2);
            service.completeRide(ride1, surgeFare1);
        }

        // 2) Bob requests a CARPOOL SUV ride using "highestRated" strategy
        Ride ride2 = service.requestRide(
            bob,
            new Location(5, 5),
            new Location(8, 8),
            RideType.SUV,
            RideMode.CARPOOL,
            "highestRated"
        );
        if (ride2 != null) {
            // Base fare with a flat discount of 5
            FareCalculator baseFare2 = new BaseFareCalculator();
            FareCalculator discountFare2 = new DiscountDecorator(baseFare2, 5.0);
            service.completeRide(ride2, discountFare2);
        }

        // 3) Another rider requests an Autorickshaw ride—no carpool, NORMAL
        Rider charlie = new Rider("Charlie", "999-777-8888", new Location(0.5, 0.5));
        service.registerRider(charlie);
        Ride ride3 = service.requestRide(
            charlie,
            new Location(0.5, 0.5),
            new Location(2, 2),
            RideType.AUTORICKSHAW,
            RideMode.NORMAL,
            "nearest"
        );
        if (ride3 != null) {
            FareCalculator baseFare3 = new BaseFareCalculator();
            service.completeRide(ride3, baseFare3);
        }
    }
}

