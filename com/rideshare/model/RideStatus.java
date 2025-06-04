package com.rideshare.model;

/**
 * Granular statuses of a ride’s lifecycle.
 */
public enum RideStatus {
    REQUESTED,
    ACCEPTED,
    EN_ROUTE_TO_PICKUP,
    IN_PROGRESS,
    COMPLETED
}

