package com.rideshare.model;

/**
 * Base class for all users (Rider / Driver).
 */
public abstract class User {
    protected String name;
    protected String phone;
    protected Location location;

    public User(String name, String phone, Location location) {
        this.name = name;
        this.phone = phone;
        this.location = location;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location newLocation) {
        this.location = newLocation;
    }
}
