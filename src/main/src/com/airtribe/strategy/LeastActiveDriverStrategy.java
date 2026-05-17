package com.airtribe.strategy;

import com.airtribe.model.Driver;
import com.airtribe.model.Rider;

import java.util.List;

public class LeastActiveDriverStrategy implements RideMatchingStrategy {
    @Override
    public Driver findDriver(Rider rider, List<Driver> drivers) {
        if (drivers == null || drivers.isEmpty()) return null;
        Driver best = null;
        int bestCount = Integer.MAX_VALUE;
        for (Driver d : drivers) {
            if (!d.isAvailable()) continue;
            int count = d.getCompletedRidesCount();
            if (count < bestCount) {
                bestCount = count;
                best = d;
            }
        }
        return best;
    }
}
