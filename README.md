# 🚖 RideWise

> A console-based ride-hailing simulation in Java — built to demonstrate clean OOP, SOLID principles, and the Strategy Pattern.

---

## 📌 Overview

**RideWise** is a terminal-driven ride-hailing system modeled after real-world platforms like Ola or Uber. It simulates the full ride lifecycle — from registering riders and drivers to requesting rides, matching drivers, calculating fares, and generating receipts.

The project's primary goal is **architectural clarity**: every design decision is intentional, demonstrating how good Java code stays maintainable, extensible, and easy to reason about.

---

## ✨ Features

- 🧑 Register **Riders** and **Drivers** with location data
- 📍 Browse **available drivers** in real time
- 🚗 **Request a ride** — auto-matched to a driver via a pluggable strategy
- 💰 **Calculate fares** using a swappable pricing strategy
- 📋 **Track ride status**: `REQUESTED → ASSIGNED → COMPLETED / CANCELLED`
- 🧾 **Generate fare receipts** with amount and timestamp on completion

---

## 🗂️ Project Structure

```
com/
└── airtribe/
    ├── docs/
    │   └── Requirements.md
    ├── exception/
    │   └── NoDriverAvailableException.java
    ├── model/
    │   ├── Driver.java
    │   ├── FareReceipt.java
    │   ├── Location.java
    │   ├── Ride.java
    │   ├── Rider.java
    │   ├── RideStatus.java
    │   └── VehicleType.java
    ├── ridewise/
    │   └── Main.java
    ├── service/
    │   ├── DriverService.java
    │   ├── RiderService.java
    │   └── RideService.java
    ├── strategy/
    │   ├── DefaultFareStrategy.java
    │   ├── FareStrategy.java
    │   ├── LeastActiveDriverStrategy.java
    │   ├── NearestDriverStrategy.java
    │   ├── PeakHourFareStrategy.java
    │   └── RideMatchingStrategy.java
    └── util/
        └── IdGenerator.java
```

---

## 🧱 Domain Entities

| Class | Key Fields |
|---|---|
| `Rider` | `id`, `name`, `location` |
| `Driver` | `id`, `name`, `currentLocation`, `available` |
| `Ride` | `id`, `rider`, `driver`, `distance`, `status` |
| `FareReceipt` | `rideId`, `amount`, `generatedAt` |
| `Location` | Represents coordinates / position data |

**Enums:** `RideStatus` · `VehicleType (BIKE, AUTO, CAR)`

---

## 🎯 Strategy Pattern

The two core strategies are **injected into `RideService` at construction**, making it easy to swap logic without touching existing code.

### 🔀 Ride Matching Strategy

```java
public interface RideMatchingStrategy {
    Driver findDriver(Rider rider, List<Driver> drivers);
}
```

| Implementation | Behaviour |
|---|---|
| `NearestDriverStrategy` | Matches the geographically closest available driver |
| `LeastActiveDriverStrategy` | Matches the driver with the fewest completed rides |

### 💵 Fare Calculation Strategy

```java
public interface FareStrategy {
    double calculateFare(Ride ride);
}
```

| Implementation | Behaviour |
|---|---|
| `DefaultFareStrategy` | Flat per-km rate |
| `PeakHourFareStrategy` | Applies a surge multiplier during peak hours |

---

## 🔧 Service Layer

| Service | Responsibilities |
|---|---|
| `RiderService` | Register riders, look up rider by ID |
| `DriverService` | Register drivers, update availability, list available drivers |
| `RideService` | Request rides, assign drivers, calculate fares, complete rides |

---

## 🖥️ Console Menu (`Main.java`)

```
========== RideWise ==========
1. Add Rider
2. Add Driver
3. View Available Drivers
4. Request Ride
5. Complete Ride
6. View Rides
7. Exit
==============================
```

Each menu option:
- Interacts through the **service layer only**
- Catches and handles invalid input gracefully
- Contains **no tightly-coupled business logic**

---

## 🏛️ Design Principles

| Principle | How It's Applied |
|---|---|
| **SRP** | Each class and service has exactly one responsibility |
| **OCP** | New strategies can be added without modifying existing code |
| **LSP** | Strategy implementations are fully substitutable |
| **ISP** | Interfaces are small and focused (`FareStrategy`, `RideMatchingStrategy`) |
| **DIP** | `RideService` depends on abstractions, not concrete implementations |

---

## 🚀 Getting Started

### Prerequisites

- Java 11+
- Maven or any standard Java build tool

### Run

```bash
# Clone the repository
git clone https://github.com/your-username/ridewise.git
cd ridewise

# Compile
javac -d out $(find com/ -name "*.java")

# Run
java -cp out com.airtribe.ridewise.Main
```

---

## 📄 Documentation

Detailed docs are available in the `com/airtribe/docs/` folder:

- [`Requirements.md`](com/airtribe/docs/Requirements.md) — Full functional & non-functional requirements
