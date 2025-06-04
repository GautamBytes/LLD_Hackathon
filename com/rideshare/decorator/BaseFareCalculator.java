package com.rideshare.decorator;

import com.rideshare.model.Ride;

/**
 * Base fare calculation: simply distance * baseRate (e.g., 10 units per km).
 */
public class BaseFareCalculator implements FareCalculator {
    private static final double RATE_PER_UNIT = 10.0;

    @Override
    public double calculateFare(Ride ride) {
        double distance = ride.getDistance();
        return distance * RATE_PER_UNIT;
    }
}
