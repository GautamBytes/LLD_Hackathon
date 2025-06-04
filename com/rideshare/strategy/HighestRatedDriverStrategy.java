package com.rideshare.strategy;

import com.rideshare.model.Driver;
import com.rideshare.model.Location;
import com.rideshare.model.RideType;
import com.rideshare.model.RideMode;
import com.rideshare.model.Rider;

import java.util.List;

/**
 * Highest-rated matching strategy. Picks the available driver with best rating
 * who matches the requested vehicle type and (for CARPOOL) capacity >= 2.
 */
public class HighestRatedDriverStrategy implements MatchingStrategy {
    @Override
    public Driver findDriver(Rider rider, Location pickup, RideType type, RideMode mode, List<Driver> drivers) {
        Driver best = null;
        double maxRating = -1.0;

        for (Driver d : drivers) {
            if (!d.isAvailable()) continue;
            if (d.getVehicleType() != type) continue;
            if (mode == RideMode.CARPOOL && d.getCapacity() < 2) continue;

            if (best == null || d.getRating() > maxRating) {
                best = d;
                maxRating = d.getRating();
            }
        }

        return best;
    }
}

