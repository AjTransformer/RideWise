package com.airtribe.strategy;

import com.airtribe.model.Driver;
import com.airtribe.model.Rider;

import java.util.List;

public class NearestDriverStrategy implements RideMatchingStrategy {
    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        if (drivers == null || drivers.isEmpty()) return null;
        Driver best = null;
        double bestDistance = Double.MAX_VALUE;
        for (Driver d : drivers) {
            if (!d.isAvailable()) continue;
            double dist = rider.getLocation().distanceTo(d.getCurrentLocation());
            if (dist < bestDistance) {
                bestDistance = dist;
                best = d;
            }
        }
        return best;
    }
}
