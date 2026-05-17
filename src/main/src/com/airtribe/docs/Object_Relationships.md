# RideWise Object Relationships

## 1. Overview
This document describes how the main objects in RideWise interact with each other.

---

## 2. Relationship Summary

### 2.1 Rider to Ride
- One rider can request many rides over time.
- Each ride belongs to one rider.

**Type:** One-to-Many  
**Example:** `Rider -> Ride`

---

### 2.2 Driver to Ride
- One driver can complete many rides over time.
- Each ride is assigned to one driver.

**Type:** One-to-Many  
**Example:** `Driver -> Ride`

---

### 2.3 Ride to Rider
- Each ride has exactly one rider.

**Type:** Many-to-One  
**Example:** `Ride -> Rider`

---

### 2.4 Ride to Driver
- Each ride has one assigned driver at a time.
- A ride may temporarily have no driver while being created.

**Type:** Many-to-One / Optional Association  
**Example:** `Ride -> Driver`

---

### 2.5 Ride to FareReceipt
- Each ride generates one fare receipt after assignment.

**Type:** One-to-One  
**Example:** `Ride -> FareReceipt`

---

### 2.6 Driver to VehicleType
- Each driver uses exactly one vehicle type.

**Type:** One-to-One Association  
**Example:** `Driver -> VehicleType`

---

## 3. Service Relationships

### 3.1 RiderService and Rider
- `RiderService` creates and stores `Rider` objects.
- It provides lookup by rider ID.

### 3.2 DriverService and Driver
- `DriverService` creates and stores `Driver` objects.
- It manages driver availability and location.

### 3.3 RideService and Ride
- `RideService` creates, assigns, completes, and cancels `Ride` objects.
- It also stores `FareReceipt` objects.

### 3.4 RideService and Strategies
- `RideService` depends on:
    - `RideMatchingStrategy`
    - `FareStrategy`

This dependency is through abstraction, not concrete classes.

---

## 4. Sequence of Interactions

### 4.1 Add Rider
1. User selects “Add Rider”
2. `Main` calls `RiderService.registerRider()`
3. A new `Rider` object is created and stored

### 4.2 Add Driver
1. User selects “Add Driver”
2. `Main` calls `DriverService.registerDriver()`
3. A new `Driver` object is created and stored

### 4.3 Request Ride
1. User selects “Request Ride”
2. `Main` calls `RideService.requestRide()`
3. `RideService` gets rider from `RiderService`
4. `RideMatchingStrategy` chooses a driver
5. `FareStrategy` calculates fare
6. `Ride` becomes `ASSIGNED`
7. `FareReceipt` is created

### 4.4 Complete Ride
1. User enters ride ID
2. `Main` calls `RideService.completeRide()`
3. Ride status changes to `COMPLETED`
4. Driver availability is restored

---

## 5. Domain Relationship Diagram in Text Form

```text
RiderService ----> Rider
DriverService ----> Driver
RideService ----> Ride
RideService ----> FareReceipt
RideService ----> RideMatchingStrategy
RideService ----> FareStrategy

Rider ----> Ride
Driver ----> Ride
Ride ----> Rider
Ride ----> Driver
Ride ----> FareReceipt
