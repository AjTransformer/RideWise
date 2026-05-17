package com.airtribe.strategy;

import com.airtribe.model.Ride;

public interface FareStrategy {
    double calculateFare(Ride ride);
}
