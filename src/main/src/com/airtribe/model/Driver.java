package com.airtribe.model;

public class Driver {
    private final String id;
    private final String name;
    private Location currentLocation;
    private boolean available;
    private final VehicleType vehicleType;
    private int completedRidesCount;

    public Driver(String id, String name, Location currentLocation, VehicleType vehicleType) {
        this.id = id;
        this.name = name;
        this.currentLocation = currentLocation;
        this.vehicleType = vehicleType;
        this.available = true;
        this.completedRidesCount = 0;
    }

    public String getId() { return id; }
    public String getName() { return name; }
    public Location getCurrentLocation() { return currentLocation; }
    public void setCurrentLocation(Location loc) { this.currentLocation = loc; }
    public boolean isAvailable() { return available; }
    public void setAvailable(boolean available) { this.available = available; }
    public VehicleType getVehicleType() { return vehicleType; }
    public int getCompletedRidesCount() { return completedRidesCount; }
    public void incrementCompletedRides() { completedRidesCount++; }

    @Override
    public String toString() {
        return String.format("Driver{id='%s', name='%s', loc=%s, available=%s, type=%s, completed=%d}",
                id, name, currentLocation, available, vehicleType, completedRidesCount);
    }
}
