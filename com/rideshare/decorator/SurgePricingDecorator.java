package com.rideshare.decorator;

import com.rideshare.model.Ride;

/**
 * Surge pricing multiplies the base fare by a surge multiplier.
 */
public class SurgePricingDecorator extends FareDecorator {
    private double surgeMultiplier;

    public SurgePricingDecorator(FareCalculator wrapped, double surgeMultiplier) {
        super(wrapped);
        this.surgeMultiplier = surgeMultiplier;
    }

    @Override
    public double calculateFare(Ride ride) {
        return super.calculateFare(ride) * surgeMultiplier;
    }
}

