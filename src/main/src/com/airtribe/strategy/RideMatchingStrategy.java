package com.airtribe.strategy;

import com.airtribe.model.Driver;
import com.airtribe.model.Rider;

import java.util.List;

public interface RideMatchingStrategy {
    Driver findDriver(Rider rider, List<Driver> drivers);
}
