# Banking System Testing Project

## Final Report

---

## 1. Introduction

This project demonstrates the application of software testing techniques to a simple banking system. The goal is to show understanding of various testing methodologies and their practical application.

### Technologies Used

- **Java 21** - Programming language
- **Maven** - Build tool
- **JUnit 5** - Testing framework

---

## 2. System Overview

The banking system provides:

- Account management (create, deposit, withdraw, transfer)
- Account status management (Unverified → Verified → Suspended → Closed)
- Credit score checking for loan eligibility

### Main Components

| Component | Responsibility |
|-----------|----------------|
| Account | Core banking operations |
| Client | Customer information |
| AccountService | State transitions |
| TransactionProcessor | Transaction handling |
| ClientController | UI simulation |
| CreditScoreChecker | Credit evaluation |

---

## 3. Testing Techniques Applied

### 3.1 Black-Box Testing

**Definition:** Testing based on specifications without knowledge of internal code.

**Techniques Used:**
- Equivalence Partitioning
- Boundary Value Analysis

**Example:**
- Testing deposit with negative, zero, and positive amounts
- Testing withdrawal at balance boundaries

---

### 3.2 White-Box Testing

**Definition:** Testing based on internal code structure.

**Approach:**
- Analyzed control flow of `deposit()` and `withdraw()`
- Identified all branches
- Created tests to cover each branch

**Coverage achieved by test design** - each test explicitly targets a specific branch.

---

### 3.3 State-Based Testing

**Definition:** Testing state transitions and state-dependent behavior.

**States:** Unverified, Verified, Suspended, Closed

**Tested:**
- All valid transitions
- All invalid transitions
- Operations allowed/blocked per state

---

### 3.4 Integration Testing

**Definition:** Testing component interactions.

**Flow tested:**
```
UI Controller → Transaction Processor → Account
```

**Verified:**
- Data flows correctly between components
- Errors propagate correctly
- State changes reflect in UI

---

### 3.5 UI Testing (Simulated)

**Definition:** Testing user interface behavior.

**Approach:** Used `ClientController` to simulate UI without actual GUI.

**Tested:**
- Button enable/disable logic
- Status messages
- Error messages

---

### 3.6 Test-Driven Development

**Definition:** Writing tests before implementation.

**Feature:** CreditScoreChecker

**Process:**
1. Wrote failing tests for eligibility rules
2. Implemented minimum code to pass
3. Extended with tier and limit calculations

---

## 4. Test Summary

| Category | Tests |
|----------|-------|
| Black-Box | 13 |
| White-Box | 11 |
| State-Based | 17 |
| Integration | 8 |
| UI Simulation | 12 |
| TDD | 14 |
| **Total** | **75** |

---

## 5. Conclusion

This project demonstrates:

1. **Multiple testing techniques** applied to the same system
2. **Clear documentation** of test cases and coverage
3. **Test-driven development** for new features
4. **State-based testing** for lifecycle management

All tests pass successfully, and coverage is demonstrated through careful test design rather than relying on automated coverage tools.

---

## 6. How to Run

```bash
# Run all tests
mvn test

# Run specific test class
mvn test -Dtest=AccountBlackBoxTests
```

---

## Appendices

- [Test Cases](TestCases.md)
- [White-Box Analysis](WhiteBoxAnalysis.md)
- [State Diagram](StateDiagram.md)
- [TDD Summary](TDDSummary.md)
