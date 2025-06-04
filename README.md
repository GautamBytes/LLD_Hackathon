# Rideshare System - Design Explanation

## SOLID Principles Implementation

**Single Responsibility Principle (SRP):** Each class has one focus (e.g., Rider holds rider info, RideService manages rides, MatchingStrategy handles matching).

**Open/Closed Principle (OCP):** We can add new ride types, strategies, or fare decorators without modifying existing code (just extend interfaces). For example, adding a new LuxuryRideType or new matching strategy does not change core logic.

**Liskov Substitution Principle (LSP):** Subclasses (Rider, Driver) can be used wherever User is expected. Strategy implementations are interchangeable via the MatchingStrategy interface.

**Interface Segregation Principle (ISP):** Interfaces are specific (e.g., Observer only has update(), MatchingStrategy only has findDriver(), so classes only implement what they need).

**Dependency Inversion Principle (DIP):** High-level modules (e.g., RideService) depend on abstractions (MatchingStrategy, FareCalculator) rather than concrete classes.

## Design Patterns Used

- **Singleton:** RideService is a singleton managing registrations and ride flows.
- **Factory:** MatchingStrategyFactory returns a strategy by name; we could similarly use a factory to create rides or users.
- **Strategy:** MatchingStrategy interface allows selecting different driver-matching algorithms (NearestDriverStrategy, HighestRatedDriverStrategy).
- **Observer:** Ride implements Subject and notifies Observer listeners (Rider, Driver) when status changes (e.g., from REQUESTED to COMPLETED).
- **Decorator:** FareCalculator is decorated by SurgePricingDecorator or DiscountDecorator to modify the base fare calculation (e.g., apply a multiplier or subtract a discount).

## Simplifications & Assumptions

- In-memory lists store all users and rides (no database).
- Single-threaded simulation with no concurrency or real-time constraints.
- Locations use simple Euclidean distance.
- We assume immediate driver acceptance (no cancellations or retries).
- Vehicle types and other attributes are simplified (one RideType per driver).

## Running the Simulation

1. **Compile** all .java files under the com.rideshare package structure (no external libraries needed).

2. **Run** `RideService.main()`. The program will simulate: 
   - Registering users/drivers
   - Handling two ride requests (one with nearest-driver strategy and surge pricing, one with highest-rated-driver strategy and discount)
   - Print status updates and fares

3. **Observe** console output to trace the flow (driver matching, status updates, and final fare calculation with decorators applied).

This solution closely follows the requirements, demonstrating the happy-path flows and the specified design patterns.