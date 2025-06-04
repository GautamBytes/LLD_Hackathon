package com.rideshare.strategy;

import com.rideshare.model.Driver;
import com.rideshare.model.Location;
import com.rideshare.model.RideType;
import com.rideshare.model.RideMode;
import com.rideshare.model.Rider;

import java.util.List;

/**
 * Strategy interface for driver matching. Accepts a Rider, pickup location,
 * desired RideType, RideMode (NORMAL vs CARPOOL), and full driver list.
 */
public interface MatchingStrategy {
    Driver findDriver(Rider rider, Location pickup, RideType type, RideMode mode, List<Driver> drivers);
}

