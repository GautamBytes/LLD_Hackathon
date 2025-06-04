package com.rideshare.decorator;

import com.rideshare.model.Ride;

/**
 * Flat discount decorator: subtracts a fixed discount amount from the wrapped fare.
 */
public class DiscountDecorator extends FareDecorator {
    private double discountAmount;

    public DiscountDecorator(FareCalculator wrapped, double discountAmount) {
        super(wrapped);
        this.discountAmount = discountAmount;
    }

    @Override
    public double calculateFare(Ride ride) {
        double baseFare = super.calculateFare(ride);
        double discounted = baseFare - discountAmount;
        return (discounted < 0) ? 0 : discounted;
    }
}

