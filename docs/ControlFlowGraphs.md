# Control Flow Graphs

Reference: term_project_fall_2026

This document contains the control flow graphs (CFGs) for the critical methods in the Account class.

---

## 1. deposit(double amount) CFG

### Code

```java
public boolean deposit(double amount) {
    // Branch 1: Status check
    if (status.equals(CLOSED)) {      // Decision point 1
        return false;                  // Branch 1a (TRUE)
    }
    // Branch 2: Amount validation
    if (amount <= 0) {                // Decision point 2
        return false;                  // Branch 2a (TRUE)
    }
    // Success path
    balance += amount;                // Branch 2b (FALSE)
    return true;
}
```

### CFG Diagram

```
         ┌─────────┐
         │  START  │
         └────┬────┘
              │
              ▼
    ┌─────────────────────┐
    │  Decision Point 1   │
    │  status == CLOSED?  │
    └──────────┬──────────┘
          ┌────┴────┐
         YES       NO
          │         │
          ▼         ▼
    ┌─────────┐  ┌─────────────────────┐
    │ Node 2  │  │  Decision Point 2   │
    │ return  │  │  amount <= 0?       │
    │ false   │  └──────────┬──────────┘
    └─────────┘        ┌────┴────┐
                      YES       NO
                       │         │
                       ▼         ▼
                 ┌─────────┐  ┌─────────────┐
                 │ Node 3  │  │   Node 4    │
                 │ return  │  │ balance +=  │
                 │ false   │  │ amount;     │
                 └─────────┘  │ return true │
                              └─────────────┘
```

### Branch Summary

| Branch | Condition | Outcome |
|--------|-----------|---------|
| 1a | status == CLOSED | TRUE → return false |
| 1b | status == CLOSED | FALSE → continue |
| 2a | amount <= 0 | TRUE → return false |
| 2b | amount <= 0 | FALSE → success |

---

## 2. withdraw(double amount) CFG

### Code

```java
public boolean withdraw(double amount) {
    // Branch 1: Closed check
    if (status.equals(CLOSED)) {       // Decision point 1
        return false;                   // Branch 1a (TRUE)
    }
    // Branch 2: Suspended check
    if (status.equals(SUSPENDED)) {    // Decision point 2
        return false;                   // Branch 2a (TRUE)
    }
    // Branch 3: Amount validation
    if (amount <= 0) {                 // Decision point 3
        return false;                   // Branch 3a (TRUE)
    }
    // Branch 4: Balance check
    if (amount > balance) {            // Decision point 4
        return false;                   // Branch 4a (TRUE)
    }
    // Success path
    balance -= amount;                 // Branch 4b (FALSE)
    return true;
}
```

### CFG Diagram

```
              ┌─────────┐
              │  START  │
              └────┬────┘
                   │
                   ▼
         ┌─────────────────────┐
         │  Decision Point 1   │
         │  status == CLOSED?  │
         └──────────┬──────────┘
               ┌────┴────┐
              YES       NO
               │         │
               ▼         ▼
         ┌─────────┐  ┌─────────────────────┐
         │ return  │  │  Decision Point 2   │
         │ false   │  │  status==SUSPENDED? │
         └─────────┘  └──────────┬──────────┘
                           ┌────┴────┐
                          YES       NO
                           │         │
                           ▼         ▼
                     ┌─────────┐  ┌─────────────────────┐
                     │ return  │  │  Decision Point 3   │
                     │ false   │  │  amount <= 0?       │
                     └─────────┘  └──────────┬──────────┘
                                       ┌────┴────┐
                                      YES       NO
                                       │         │
                                       ▼         ▼
                                 ┌─────────┐  ┌─────────────────────┐
                                 │ return  │  │  Decision Point 4   │
                                 │ false   │  │  amount > balance?  │
                                 └─────────┘  └──────────┬──────────┘
                                                   ┌────┴────┐
                                                  YES       NO
                                                   │         │
                                                   ▼         ▼
                                             ┌─────────┐  ┌─────────────┐
                                             │ return  │  │ balance -=  │
                                             │ false   │  │ amount;     │
                                             └─────────┘  │ return true │
                                                          └─────────────┘
```

### Branch Summary

| Branch | Condition | Outcome |
|--------|-----------|---------|
| 1a | status == CLOSED | TRUE → return false |
| 2a | status == SUSPENDED | TRUE → return false |
| 3a | amount <= 0 | TRUE → return false |
| 4a | amount > balance | TRUE → return false |
| 4b | amount > balance | FALSE → success |

---

## 3. Cyclomatic Complexity

### Formula: V(G) = E - N + 2P

| Method | Edges (E) | Nodes (N) | Components (P) | V(G) |
|--------|-----------|-----------|----------------|------|
| deposit() | 6 | 5 | 1 | 3 |
| withdraw() | 10 | 8 | 1 | 4 |

### Interpretation

- **deposit()**: 3 independent paths → 3 test cases minimum
- **withdraw()**: 4 independent paths → 4 test cases minimum

Our test suite exceeds the minimum required test cases.

---

## 4. Path Coverage

### deposit() Paths

| Path | Sequence | Test |
|------|----------|------|
| P1 | START → D1(Y) → return false | WB-D-Branch1a |
| P2 | START → D1(N) → D2(Y) → return false | WB-D-Branch2a |
| P3 | START → D1(N) → D2(N) → return true | WB-D-Branch2b |

### withdraw() Paths

| Path | Sequence | Test |
|------|----------|------|
| P1 | START → D1(Y) → return false | WB-W-Branch1a |
| P2 | START → D1(N) → D2(Y) → return false | WB-W-Branch2a |
| P3 | START → D1(N) → D2(N) → D3(Y) → return false | WB-W-Branch3a |
| P4 | START → D1(N) → D2(N) → D3(N) → D4(Y) → return false | WB-W-Branch4a |
| P5 | START → D1(N) → D2(N) → D3(N) → D4(N) → return true | WB-W-Branch4b |
