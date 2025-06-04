package com.rideshare.strategy;

import com.rideshare.model.Driver;
import com.rideshare.model.Location;
import com.rideshare.model.RideType;
import com.rideshare.model.RideMode;
import com.rideshare.model.Rider;

import java.util.List;

/**
 * Nearest-driver matching strategy. Picks the closest available driver
 * who matches the requested vehicle type and (for CARPOOL) capacity >= 2.
 */
public class NearestDriverStrategy implements MatchingStrategy {
    @Override
    public Driver findDriver(Rider rider, Location pickup, RideType type, RideMode mode, List<Driver> drivers) {
        Driver nearest = null;
        double minDist = Double.MAX_VALUE;

        for (Driver d : drivers) {
            if (!d.isAvailable()) continue;
            if (d.getVehicleType() != type) continue;
            if (mode == RideMode.CARPOOL && d.getCapacity() < 2) continue;

            double dist = d.getLocation().distanceTo(pickup);
            if (dist < minDist) {
                minDist = dist;
                nearest = d;
            }
        }

        return nearest;
    }
}

