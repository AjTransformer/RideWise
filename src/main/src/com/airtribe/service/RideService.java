package com.airtribe.service;

import com.airtribe.exception.NoDriverAvailableException;
import com.airtribe.model.FareReceipt;
import com.airtribe.model.Ride;
import com.airtribe.model.RideStatus;
import com.airtribe.model.Driver;
import com.airtribe.model.Rider;
import com.airtribe.strategy.FareStrategy;
import com.airtribe.strategy.RideMatchingStrategy;
import com.airtribe.util.IdGenerator;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class RideService {
    private final RideMatchingStrategy matchingStrategy;
    private final FareStrategy fareStrategy;
    private final DriverService driverService;
    private final RiderService riderService;

    private final Map<String, Ride> rides = new ConcurrentHashMap<>();
    private final Map<String, FareReceipt> receipts = new ConcurrentHashMap<>();

    public RideService(RideMatchingStrategy matchingStrategy,
                       FareStrategy fareStrategy,
                       DriverService driverService,
                       RiderService riderService) {
        this.matchingStrategy = matchingStrategy;
        this.fareStrategy = fareStrategy;
        this.driverService = driverService;
        this.riderService = riderService;
    }

    public Ride requestRide(String riderId, double distanceKm) throws NoDriverAvailableException {
        Rider rider = riderService.getRiderById(riderId);
        if (rider == null) throw new IllegalArgumentException("Rider not found: " + riderId);

        String rideId = IdGenerator.generate("RID");
        Ride ride = new Ride(rideId, rider, distanceKm);
        rides.put(rideId, ride);

        // find a driver
        Driver driver = matchingStrategy.findDriver(rider, driverService.listAvailableDrivers());
        if (driver == null) {
            ride.setStatus(RideStatus.CANCELLED);
            throw new NoDriverAvailableException("No available driver found for rider " + rider.getName());
        }

        // assign
        ride.setDriver(driver);
        ride.setStatus(RideStatus.ASSIGNED);
        driver.setAvailable(false);

        // calculate fare
        double fare = fareStrategy.calculateFare(ride);
        FareReceipt receipt = new FareReceipt(rideId, fare);
        receipts.put(rideId, receipt);

        return ride;
    }

    public FareReceipt getFareReceipt(String rideId) {
        return receipts.get(rideId);
    }

    public Ride completeRide(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) throw new IllegalArgumentException("Ride not found: " + rideId);
        if (ride.getStatus() != RideStatus.ASSIGNED) {
            throw new IllegalStateException("Ride is not in ASSIGNED state: " + ride.getStatus());
        }
        ride.setStatus(RideStatus.COMPLETED);
        ride.setCompletedAt(LocalDateTime.now());

        Driver driver = ride.getDriver();
        if (driver != null) {
            driver.setAvailable(true);
            driver.incrementCompletedRides();
        }
        return ride;
    }

    public Ride cancelRide(String rideId) {
        Ride ride = rides.get(rideId);
        if (ride == null) throw new IllegalArgumentException("Ride not found: " + rideId);
        ride.setStatus(RideStatus.CANCELLED);
        Driver driver = ride.getDriver();
        if (driver != null) {
            driver.setAvailable(true);
        }
        return ride;
    }

    public Collection<Ride> listAllRides() {
        return rides.values();
    }
}
