# 🚖 Rideshare System - LLD Hackathon Submission

This project is a Low-Level Design (LLD) implementation of a simplified ride-sharing platform (like Uber/Ola) developed as part of a hackathon challenge. It showcases the use of **SOLID principles**, **design patterns**, and **extensible architecture**.

---

## ✅ SOLID Principles Implementation

### **S — Single Responsibility Principle**
- Each class handles one responsibility only.
  - `Rider`, `Driver`: store user-related data.
  - `RideService`: manages ride flow.
  - `MatchingStrategy`: selects appropriate driver.

### **O — Open/Closed Principle**
- The system is open for extension, closed for modification.
  - New ride types, pricing rules, or matching strategies can be added without touching existing logic.

### **L — Liskov Substitution Principle**
- Subtypes (`Rider`, `Driver`) can be used wherever `User` is expected.
- Strategy classes are interchangeable via the `MatchingStrategy` interface.

### **I — Interface Segregation Principle**
- Interfaces are fine-grained:
  - `Observer` only contains `update()`
  - `MatchingStrategy` only defines `findDriver()`

### **D — Dependency Inversion Principle**
- `RideService` depends on abstractions like `FareCalculator` and `MatchingStrategy`, not concrete classes.

---

## 💡 Design Patterns Used

| Pattern      | Where It's Used                                              |
|--------------|--------------------------------------------------------------|
| **Singleton**| `RideService` manages all state (drivers, riders, rides)     |
| **Strategy** | Different matching strategies (nearest, highest-rated)       |
| **Observer** | `Ride` notifies `Rider` and `Driver` on status changes       |
| **Decorator**| Flexible fare computation with surge/discount layers         |
| **Factory**  | `MatchingStrategyFactory` provides matching algorithm        |

---

## ⚙️ Features Implemented

- Rider and Driver registration
- Ride booking based on:
  - Ride type (Sedan, SUV, Bike, Autorickshaw)
  - Ride mode: `NORMAL` or `CARPOOL`
  - Matching strategy: `Nearest` or `HighestRated`
- Fare calculation with:
  - Base fare
  - Optional Surge pricing
  - Optional Discount
- Status lifecycle:
  - `REQUESTED → ACCEPTED → EN_ROUTE → IN_PROGRESS → COMPLETED`
- Notifications sent via Observer pattern
- In-memory storage and single-threaded simulation
- Console output for full trace of actions

---

## 🛠️ Assumptions & Simplifications

- All data is stored in-memory (no database).
- Locations are represented with simple 2D coordinates.
- Carpool is simulated: only drivers with `capacity >= 2` are matched, but actual pooling of riders is not implemented.
- Drivers always accept rides (no rejection logic).
- One ride per rider at a time.
- No concurrency or threading; single-threaded sequential flow.

---

## 🚀 How to Run

### 1. Clone the Repo
```bash
git clone https://github.com/GautamBytes/LLD_Hackathon.git
```

### 2. Compile the Code

```bash
javac com\rideshare\observer\*.java com\rideshare\model\*.java com\rideshare\strategy\*.java com\rideshare\decorator\*.java com\rideshare\service\*.java
```

### 3. Run the Main Program

```bash
java com.rideshare.service.RideService
```

---

## 🧪 Sample Simulation Flow

The main method demonstrates:

1. **Registration**
   * Registers 3 drivers and 3 riders

2. **Ride Requests**
   * Alice → Sedan (Nearest strategy + Surge pricing)
   * Bob → SUV (Highest-rated strategy + Discount)
   * Charlie → Autorickshaw (Basic ride)

3. **Fare Calculation**
   * Decorators applied based on ride request

4. **Status Notifications**
   * Sent to Rider and Driver at each stage

---

## 📌 Extensibility Ideas

* Add cancellation flow or driver rejection
* Integrate real maps (Google Maps APIs)
* Group riders into one car for real carpool logic
* Add support for scheduling future rides
* Persist rides and users to a database
* Extend fare calculator to support promo codes or time-based surge

---

## 👨‍💻 Author

Made with ❤️ for the hackathon by [Your Name]
GitHub: [github.com/YourUsername](https://github.com/GautamBytes)
LinkedIn: [linkedin.com/in/YourUsername](https://www.linkedin.com/in/gautam-manchandani/)

---
