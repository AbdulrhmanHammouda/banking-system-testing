# Test Case Document

Reference: term_project_fall_2026

---

## 1. Black-Box Test Cases

### 1.1 Equivalence Partitioning

| ID | Method | Input Partition | Test Input | Expected | Actual |
|----|--------|-----------------|------------|----------|--------|
| EP-D1 | deposit | Positive amount | 100.0 | true | ✓ |
| EP-D2 | deposit | Zero amount | 0 | false | ✓ |
| EP-D3 | deposit | Negative amount | -100.0 | false | ✓ |
| EP-W1 | withdraw | Amount < balance | 50.0 (bal=100) | true | ✓ |
| EP-W2 | withdraw | Amount = balance | 100.0 (bal=100) | true | ✓ |
| EP-W3 | withdraw | Amount > balance | 150.0 (bal=100) | false | ✓ |

### 1.2 Boundary Value Analysis

| ID | Method | Boundary | Test Input | Expected | Actual |
|----|--------|----------|------------|----------|--------|
| BVA-D1 | deposit | Just above 0 | 0.01 | true | ✓ |
| BVA-D2 | deposit | At 0 | 0 | false | ✓ |
| BVA-D3 | deposit | Just below 0 | -0.01 | false | ✓ |
| BVA-W1 | withdraw | At balance | 100.0 | true | ✓ |
| BVA-W2 | withdraw | Just over balance | 100.01 | false | ✓ |
| BVA-W3 | withdraw | Just under balance | 99.99 | true | ✓ |

### 1.3 Mandatory Test Cases BB01-BB07

| ID | Description | Input | Expected | Status |
|----|-------------|-------|----------|--------|
| BB01 | deposit(-100) | -100 | false | ✓ Pass |
| BB02 | deposit(0) | 0 | false | ✓ Pass |
| BB03 | deposit(1) | 1 | true | ✓ Pass |
| BB04 | withdraw(50) bal=100 | 50 | true | ✓ Pass |
| BB05 | withdraw(150) bal=100 | 150 | false | ✓ Pass |
| BB06 | withdraw on Suspended | 50 | false | ✓ Pass |
| BB07 | deposit on Closed | 50 | false | ✓ Pass |

---

## 2. Decision Table: Transfer

| Rule | Sender Verified | Target Valid | Target Open | Amount Valid | Sufficient Funds | Result |
|------|-----------------|--------------|-------------|--------------|------------------|--------|
| R1 | ✓ | ✓ | ✓ | ✓ | ✓ | Success |
| R2 | ✗ | ✓ | ✓ | ✓ | ✓ | Fail |
| R3 | ✓ | ✗ (null) | - | ✓ | ✓ | Fail |
| R4 | ✓ | ✓ | ✗ (Closed) | ✓ | ✓ | Fail |
| R5 | ✓ | ✓ | ✓ | ✗ (<=0) | ✓ | Fail |
| R6 | ✓ | ✓ | ✓ | ✓ | ✗ | Fail |

---

## 3. White-Box Test Cases

### 3.1 deposit() Branch Coverage

| Test ID | Branch | Condition | Expected |
|---------|--------|-----------|----------|
| WB-D-Branch1a | Branch1a | status==CLOSED | false |
| WB-D-Branch1b | Branch1b | status!=CLOSED | continue |
| WB-D-Branch2a | Branch2a | amount<=0 | false |
| WB-D-Branch2b | Branch2b | amount>0 | true |

### 3.2 withdraw() Branch Coverage

| Test ID | Branch | Condition | Expected |
|---------|--------|-----------|----------|
| WB-W-Branch1a | Branch1a | status==CLOSED | false |
| WB-W-Branch2a | Branch2a | status==SUSPENDED | false |
| WB-W-Branch3a | Branch3a | amount<=0 | false |
| WB-W-Branch4a | Branch4a | amount>balance | false |
| WB-W-Branch4b | Branch4b | amount<=balance | true |

---

## 4. State-Based Test Cases

| ID | From State | Action | To State | Expected Result |
|----|------------|--------|----------|-----------------|
| ST01 | Unverified | verify() | Verified | Success |
| ST02 | Verified | suspend() | Suspended | Success |
| ST03 | Suspended | reinstate() | Verified | Success |
| ST04 | Any | close() | Closed | Success |
| ST05 | Verified | verify() | - | Fail (already) |
| ST06 | Unverified | suspend() | - | Fail (invalid) |
| ST07 | Closed | verify() | - | Fail (terminal) |

---

## 5. Test Summary

| Category | Tests | Passed | Failed |
|----------|-------|--------|--------|
| Black-Box | 22 | 22 | 0 |
| White-Box | 11 | 11 | 0 |
| State-Based | 17 | 17 | 0 |
| Integration | 8 | 8 | 0 |
| UI Simulation | 12 | 12 | 0 |
| TDD | 14 | 14 | 0 |
| **Total** | **84** | **84** | **0** |
