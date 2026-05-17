# RideWise Requirements

## 1. Project Overview
RideWise is a console-based ride booking application designed using object-oriented principles and the Strategy pattern.  
It supports rider registration, driver registration, ride requests, driver matching, fare calculation, and ride lifecycle tracking.

The project is built to be:
- Maintainable
- Readable
- Easily extendable
- Low coupling
- Open for future pricing and matching strategies

---

## 2. Functional Requirements

### 2.1 Register Riders
- The system must allow new riders to be registered.
- Each rider has:
    - `id`
    - `name`
    - `location`

### 2.2 Register Drivers
- The system must allow new drivers to be registered.
- Each driver has:
    - `id`
    - `name`
    - `currentLocation`
    - `available` status
    - `vehicleType`

### 2.3 Show Available Drivers
- The system must list all drivers who are currently available.

### 2.4 Request a Ride
- A rider must be able to request a ride by providing rider ID and trip distance.
- The system should create a ride request and assign an available driver.

### 2.5 Match Ride to Driver Using a Strategy
- Driver assignment must be handled through a pluggable strategy.
- Matching logic must not be hardcoded inside `RideService`.

### 2.6 Calculate Fare Using a Pricing Strategy
- Fare calculation must be handled through a pluggable strategy.
- Pricing logic must not be hardcoded inside `RideService`.

### 2.7 Track Ride Status
The ride status must follow this lifecycle:
- `REQUESTED`
- `ASSIGNED`
- `COMPLETED`
- `CANCELLED`

---

## 3. Non-Functional Requirements

### 3.1 Easily Extendable Pricing Algorithm
- New pricing rules should be added by implementing the fare strategy interface.
- Existing code should not need modification when a new fare strategy is introduced.

### 3.2 Easily Change Driver Matching Logic
- New driver matching rules should be added by implementing the ride matching strategy interface.
- The ride service should work with any matching implementation.

### 3.3 Low Coupling Between Services
- Rider, driver, and ride services should remain independent as much as possible.
- Services should communicate through abstractions instead of direct dependencies on implementation details.

### 3.4 Maintainable and Readable Code
- Code should follow clear naming conventions.
- Responsibilities should be separated across model, strategy, service, utility, and exception packages.

---

## 4. Domain Entities

### 4.1 Rider
Represents a customer requesting a ride.

**Fields**
- `id`
- `name`
- `location`

### 4.2 Driver
Represents a driver available for ride assignment.

**Fields**
- `id`
- `name`
- `currentLocation`
- `available`
- `vehicleType`

### 4.3 Ride
Represents a ride request and its lifecycle.

**Fields**
- `id`
- `rider`
- `driver`
- `distance`
- `status`

### 4.4 FareReceipt
Represents the fare generated for a ride.

**Fields**
- `rideId`
- `amount`
- `generatedAt`

---

## 5. Enumerations

### 5.1 RideStatus
Possible values:
- `REQUESTED`
- `ASSIGNED`
- `COMPLETED`
- `CANCELLED`

### 5.2 VehicleType
Possible values:
- `BIKE`
- `AUTO`
- `CAR`

---

## 6. Strategy and Composition Design

### 6.1 Ride Matching Strategy
```java
public interface RideMatchingStrategy {
    Driver findDriver(Rider rider, List<Driver> drivers);
}
