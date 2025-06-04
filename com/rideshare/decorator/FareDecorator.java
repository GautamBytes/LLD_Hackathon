package com.rideshare.decorator;

import com.rideshare.model.Ride;

/**
 * Abstract decorator wrapping any FareCalculator.
 */
public abstract class FareDecorator implements FareCalculator {
    protected FareCalculator wrapped;

    public FareDecorator(FareCalculator wrapped) {
        this.wrapped = wrapped;
    }

    @Override
    public double calculateFare(Ride ride) {
        return wrapped.calculateFare(ride);
    }
}

