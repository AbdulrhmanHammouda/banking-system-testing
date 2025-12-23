# Test Cases Document

## Overview

This document lists all test cases organized by testing technique.

---

## 1. Black-Box Tests

Testing based on **specifications** without knowledge of internal code.

### Equivalence Partitioning

| ID | Input Partition | Test | Expected |
|----|-----------------|------|----------|
| EP1 | Negative amount | deposit(-50) | Rejected |
| EP2 | Zero amount | deposit(0) | Rejected |
| EP3 | Positive amount | deposit(50) | Accepted |
| EP4 | Negative amount | withdraw(-50) | Rejected |
| EP5 | Zero amount | withdraw(0) | Rejected |
| EP6 | Valid amount | withdraw(50) | Accepted |
| EP7 | Over balance | withdraw(150) | Rejected |

### Boundary Value Analysis

| ID | Boundary | Test | Expected |
|----|----------|------|----------|
| BVA1 | Just below 0 | deposit(-0.01) | Rejected |
| BVA2 | At 0 | deposit(0.00) | Rejected |
| BVA3 | Just above 0 | deposit(0.01) | Accepted |
| BVA4 | At balance | withdraw(100) | Accepted |
| BVA5 | Above balance | withdraw(100.01) | Rejected |
| BVA6 | Below balance | withdraw(99.99) | Accepted |

---

## 2. White-Box Tests

Testing based on **internal code structure**.

### deposit() Branch Coverage

| Test | Branch | Condition |
|------|--------|-----------|
| deposit_Branch1_True_Closed | 1=T | status==CLOSED |
| deposit_Branch1_False_NotClosed | 1=F | status!=CLOSED |
| deposit_Branch2_True_Zero | 2=T | amount<=0 |
| deposit_Branch2_False_Positive | 2=F | amount>0 |

### withdraw() Branch Coverage

| Test | Branch | Condition |
|------|--------|-----------|
| withdraw_Branch1_True_Closed | 1=T | status==CLOSED |
| withdraw_Branch2_True_Suspended | 2=T | status==SUSPENDED |
| withdraw_Branch3_True_Zero | 3=T | amount<=0 |
| withdraw_Branch4_True_Overdraft | 4=T | amount>balance |
| withdraw_AllFalse_Success | All=F | Success path |

---

## 3. State-Based Tests

### Valid Transitions

| From | Action | To |
|------|--------|----|
| Unverified | verify() | Verified |
| Verified | suspend() | Suspended |
| Suspended | reinstate() | Verified |
| Any | close() | Closed |

### Invalid Transitions

| From | Action | Result |
|------|--------|--------|
| Verified | verify() | Rejected |
| Unverified | suspend() | Rejected |
| Closed | verify() | Rejected |
| Closed | close() | Rejected |

---

## 4. Integration Tests

| Test | Flow |
|------|------|
| depositFlow_Success | UI→Processor→Account |
| withdrawFlow_Success | UI→Processor→Account |
| transferFlow_Success | UI→Processor→Account×2 |
| fullAccountLifecycle | Complete lifecycle |

---

## 5. UI Simulation Tests

| Test | Verification |
|------|--------------|
| verifiedAccount_AllEnabled | All buttons enabled |
| suspendedAccount_OnlyDeposit | Withdraw/Transfer disabled |
| closedAccount_AllDisabled | All buttons disabled |
| successfulDeposit_ShowsStatus | Status message appears |
| insufficientFunds_ShowsError | Error message appears |

---

## 6. TDD Tests

| Test | Requirement |
|------|-------------|
| score650_IsEligible | Score ≥ 650 eligible |
| scoreBelow650_NotEligible | Score < 650 not eligible |
| tier_Excellent | 750+ = Excellent |
| tier_Good | 650-749 = Good |
| maxCredit_Excellent | $50,000 limit |

---

## Summary

| Category | Test Count |
|----------|------------|
| Black-Box | 13 |
| White-Box | 11 |
| State-Based | 17 |
| Integration | 8 |
| UI Simulation | 12 |
| TDD | 14 |
| **Total** | **75** |
