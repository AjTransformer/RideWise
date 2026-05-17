package com.airtribe.strategy;

import com.airtribe.model.Ride;

import java.time.LocalTime;

public class PeakHourFareStrategy implements FareStrategy {
    private final FareStrategy delegate;
    private final double peakMultiplier;
    private final LocalTime peakStart;
    private final LocalTime peakEnd;

    public PeakHourFareStrategy(FareStrategy delegate) {
        this(delegate, 1.5, LocalTime.of(17, 0), LocalTime.of(20, 0));
    }

    public PeakHourFareStrategy(FareStrategy delegate, double peakMultiplier, LocalTime peakStart, LocalTime peakEnd) {
        this.delegate = delegate;
        this.peakMultiplier = peakMultiplier;
        this.peakStart = peakStart;
        this.peakEnd = peakEnd;
    }

    @Override
    public double calculateFare(Ride ride) {
        double base = delegate.calculateFare(ride);
        LocalTime t = ride.getRequestedAt().toLocalTime();
        if (!t.isBefore(peakStart) && !t.isAfter(peakEnd)) {
            return base * peakMultiplier;
        }
        return base;
    }
}
