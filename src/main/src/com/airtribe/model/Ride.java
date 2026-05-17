package com.airtribe.model;

import java.time.LocalDateTime;

public class Ride {
    private final String id;
    private final Rider rider;
    private Driver driver;
    private final double distanceKm;
    private RideStatus status;
    private final LocalDateTime requestedAt;
    private LocalDateTime completedAt;

    public Ride(String id, Rider rider, double distanceKm) {
        this.id = id;
        this.rider = rider;
        this.distanceKm = distanceKm;
        this.status = RideStatus.REQUESTED;
        this.requestedAt = LocalDateTime.now();
    }

    public String getId() { return id; }
    public Rider getRider() { return rider; }
    public Driver getDriver() { return driver; }
    public void setDriver(Driver driver) { this.driver = driver; }
    public double getDistanceKm() { return distanceKm; }
    public RideStatus getStatus() { return status; }
    public void setStatus(RideStatus status) { this.status = status; }
    public LocalDateTime getRequestedAt() { return requestedAt; }
    public LocalDateTime getCompletedAt() { return completedAt; }
    public void setCompletedAt(LocalDateTime completedAt) { this.completedAt = completedAt; }

    @Override
    public String toString() {
        return String.format("Ride{id='%s', rider=%s, driver=%s, distance=%.2fkm, status=%s}",
                id, rider.getName(), driver != null ? driver.getName() : "null", distanceKm, status);
    }
}
