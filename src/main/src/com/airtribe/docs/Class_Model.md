---

## 2) `Class_Model.md`

Use this to describe the classes in your project.

```md
# RideWise Class Model

## 1. Overview
This document explains the core classes in RideWise and how they collaborate to support ride booking, driver matching, and fare calculation.

---

## 2. Core Model Classes

### 2.1 Rider
Represents a person who requests a ride.

#### Fields
- `id`: Unique rider identifier
- `name`: Rider's full name
- `location`: Current location of the rider

#### Responsibility
Stores rider-related data used during ride request and driver matching.

---

### 2.2 Driver
Represents a driver who can accept and complete rides.

#### Fields
- `id`: Unique driver identifier
- `name`: Driver's name
- `currentLocation`: Driver's current location
- `available`: Indicates whether the driver is available for assignment
- `vehicleType`: Type of vehicle used by the driver
- `completedRidesCount`: Number of rides completed by the driver

#### Responsibility
Stores driver-related state used for assignment and matching.

---

### 2.3 Ride
Represents a ride request and its progress.

#### Fields
- `id`: Unique ride identifier
- `rider`: The rider who requested the ride
- `driver`: Assigned driver
- `distanceKm`: Trip distance
- `status`: Current ride status
- `requestedAt`: Time when the ride was requested
- `completedAt`: Time when the ride was completed

#### Responsibility
Tracks the lifecycle of a ride from request to completion/cancellation.

---

### 2.4 FareReceipt
Represents the calculated fare for a ride.

#### Fields
- `rideId`: Associated ride ID
- `amount`: Final fare amount
- `generatedAt`: Time the receipt was created

#### Responsibility
Stores fare information after the ride is assigned.

---

### 2.5 Location
Represents a coordinate pair used for distance calculations.

#### Fields
- `x`
- `y`

#### Responsibility
Supports spatial comparison between rider and driver locations.

---

## 3. Enumerations

### 3.1 RideStatus
Represents the lifecycle of a ride.

Values:
- `REQUESTED`
- `ASSIGNED`
- `COMPLETED`
- `CANCELLED`

### 3.2 VehicleType
Represents the type of vehicle a driver uses.

Values:
- `BIKE`
- `AUTO`
- `CAR`

---

## 4. Strategy Classes

### 4.1 RideMatchingStrategy
Interface for driver selection logic.

```java
public interface RideMatchingStrategy {
    Driver findDriver(Rider rider, List<Driver> drivers);
}
