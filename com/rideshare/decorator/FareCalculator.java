package com.rideshare.decorator;

import com.rideshare.model.Ride;

/**
 * Core interface for fare calculation. Can be decorated to add surge,
 * discount, etc.
 */
public interface FareCalculator {
    double calculateFare(Ride ride);
}
