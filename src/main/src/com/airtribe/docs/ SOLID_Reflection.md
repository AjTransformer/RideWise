---

## 3) `SOLID_Reflection.md`

Use this to explain how your design follows SOLID.

```md
# SOLID Reflection for RideWise

## 1. Introduction
RideWise was designed using the SOLID principles to keep the codebase flexible, maintainable, and easy to extend.

---

## 2. Single Responsibility Principle (SRP)
Each class has one clear responsibility.

### Examples
- `Rider` stores rider data only.
- `Driver` stores driver data only.
- `RideService` handles ride workflows only.
- `NearestDriverStrategy` handles nearest-driver selection only.
- `DefaultFareStrategy` handles fare calculation only.

### Reflection
Splitting responsibilities across classes makes the code easier to understand and modify.

---

## 3. Open/Closed Principle (OCP)
Classes should be open for extension but closed for modification.

### Examples
- New matching strategies can be added by implementing `RideMatchingStrategy`.
- New fare calculation strategies can be added by implementing `FareStrategy`.

### Reflection
If surge pricing or a smarter matching logic is needed later, the existing service code does not need to change.

---

## 4. Liskov Substitution Principle (LSP)
Subtypes should be replaceable for their base types.

### Examples
- `NearestDriverStrategy` and `LeastActiveDriverStrategy` can both replace `RideMatchingStrategy`.
- `DefaultFareStrategy` and `PeakHourFareStrategy` can both replace `FareStrategy`.

### Reflection
Any strategy implementation can be substituted without breaking the ride service.

---

## 5. Interface Segregation Principle (ISP)
Clients should not depend on interfaces they do not use.

### Examples
- `RideMatchingStrategy` only declares driver-finding behavior.
- `FareStrategy` only declares fare calculation behavior.

### Reflection
Small, focused interfaces keep dependencies clean and easy to implement.

---

## 6. Dependency Inversion Principle (DIP)
High-level modules should depend on abstractions, not concrete implementations.

### Examples
- `RideService` depends on `RideMatchingStrategy` and `FareStrategy` interfaces.
- `Main` injects concrete strategy implementations into `RideService`.

### Reflection
This makes the application easier to test and swap behavior without rewriting business logic.

---

## 7. Overall Reflection
The current design is intentionally modular:
- Services coordinate behavior
- Models hold state
- Strategies encapsulate algorithms
- Main only wires components together

This architecture supports future changes with minimal risk and low code duplication.
