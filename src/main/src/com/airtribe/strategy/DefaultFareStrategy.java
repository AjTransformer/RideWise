package com.airtribe.strategy;

import com.airtribe.model.Ride;

public class DefaultFareStrategy implements FareStrategy {
    private final double baseFare;
    private final double perKmRate;

    public DefaultFareStrategy() {
        this.baseFare = 50.0; // base
        this.perKmRate = 10.0; // per km
    }

    public DefaultFareStrategy(double baseFare, double perKmRate) {
        this.baseFare = baseFare;
        this.perKmRate = perKmRate;
    }

    @Override
    public double calculateFare(Ride ride) {
        return baseFare + perKmRate * ride.getDistanceKm();
    }
}
