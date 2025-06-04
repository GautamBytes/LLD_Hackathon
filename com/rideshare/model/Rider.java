package com.rideshare.model;

import com.rideshare.observer.Observer;

/**
 * Rider class (implements Observer to receive ride notifications).
 */
public class Rider extends User implements Observer {
    public Rider(String name, String phone, Location location) {
        super(name, phone, location);
    }

    @Override
    public void update(String message) {
        System.out.println("Rider " + name + " receives notification: " + message);
    }
}

