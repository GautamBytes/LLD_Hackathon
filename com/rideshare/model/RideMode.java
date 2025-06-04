package com.rideshare.model;

/**
 * Ride modes: NORMAL (one rider per ride) or CARPOOL (driver must have capacity >= 2).
 * Currently, we do not group multiple Rider objects in one Ride, but the matching
 * logic will pick a driver with capacity >=2 if CARPOOL is requested.
 */
public enum RideMode {
    NORMAL,
    CARPOOL
}

