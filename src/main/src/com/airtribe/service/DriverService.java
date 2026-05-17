package com.airtribe.service;

import com.airtribe.model.Driver;
import com.airtribe.model.Location;
import com.airtribe.model.VehicleType;
import com.airtribe.util.IdGenerator;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

public class DriverService {
    private final Map<String, Driver> drivers = new ConcurrentHashMap<>();

    public Driver registerDriver(String name, double x, double y, VehicleType type) {
        String id = IdGenerator.generate("D");
        Driver d = new Driver(id, name, new Location(x, y), type);
        drivers.put(id, d);
        return d;
    }

    public Driver getDriverById(String id) {
        return drivers.get(id);
    }

    public void updateAvailability(String driverId, boolean available) {
        Driver d = drivers.get(driverId);
        if (d != null) d.setAvailable(available);
    }

    public List<Driver> listAvailableDrivers() {
        return drivers.values().stream().filter(Driver::isAvailable).collect(Collectors.toList());
    }

    public Collection<Driver> listAllDrivers() {
        return drivers.values();
    }

    public void incrementCompletedRides(String driverId) {
        Driver d = drivers.get(driverId);
        if (d != null) d.incrementCompletedRides();
    }

    public void updateLocation(String driverId, double x, double y) {
        Driver d = drivers.get(driverId);
        if (d != null) d.setCurrentLocation(new Location(x, y));
    }
}
