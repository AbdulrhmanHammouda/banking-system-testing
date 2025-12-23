# White-Box Analysis

## Overview

White-box testing examines the internal structure of code. Coverage is proven by **test design** that maps each test to a specific branch in the control flow graph (CFG).

> **Note:** Coverage was ensured by test design rather than automated tools (no JaCoCo).
> Reference: term_project_fall_2026

---

## Control Flow Graph: deposit()

```
                    ┌───────────────┐
                    │     START     │
                    └───────┬───────┘
                            │
                            ▼
                ┌───────────────────────┐
                │  Branch1:             │
                │  status == CLOSED?    │
                └───────────┬───────────┘
                     │             │
                   [YES]         [NO]
                     │             │
                     ▼             ▼
              ┌──────────┐    ┌───────────────────────┐
              │ Branch1a │    │  Branch2:             │
              │ return   │    │  amount <= 0?         │
              │ false    │    └───────────┬───────────┘
              └──────────┘         │             │
                                 [YES]         [NO]
                                   │             │
                                   ▼             ▼
                            ┌──────────┐   ┌──────────────┐
                            │ Branch2a │   │  Branch2b    │
                            │ return   │   │ balance +=   │
                            │ false    │   │ amount;      │
                            └──────────┘   │ return true  │
                                           └──────────────┘
```

### Branch List for deposit()

| Branch ID | Condition | Outcome |
|-----------|-----------|---------|
| Branch1a | status == CLOSED | TRUE → return false |
| Branch1b | status == CLOSED | FALSE → continue |
| Branch2a | amount <= 0 | TRUE → return false |
| Branch2b | amount <= 0 | FALSE → success |

### Test → Branch Mapping for deposit()

| Test Method | Branch Covered | Assertion |
|-------------|----------------|-----------|
| `deposit_Branch1a_StatusClosed` | Branch1a | Closed account → false |
| `deposit_Branch1b_StatusNotClosed` | Branch1b | Not closed → continues |
| `deposit_Branch2a_AmountZero` | Branch2a | Zero amount → false |
| `deposit_Branch2a_AmountNegative` | Branch2a | Negative → false |
| `deposit_Branch2b_AmountPositive` | Branch2b | Positive → success |

**Coverage: 4/4 branches = 100%**

---

## Control Flow Graph: withdraw()

```
                        ┌───────────────┐
                        │     START     │
                        └───────┬───────┘
                                │
                                ▼
                    ┌───────────────────────┐
                    │  Branch1:             │
                    │  status == CLOSED?    │
                    └───────────┬───────────┘
                         │             │
                       [YES]         [NO]
                         │             │
                         ▼             ▼
                  ┌──────────┐  ┌───────────────────────┐
                  │ Branch1a │  │  Branch2:             │
                  │ return   │  │  status == SUSPENDED? │
                  │ false    │  └───────────┬───────────┘
                  └──────────┘       │             │
                                   [YES]         [NO]
                                     │             │
                                     ▼             ▼
                              ┌──────────┐  ┌───────────────────────┐
                              │ Branch2a │  │  Branch3:             │
                              │ return   │  │  amount <= 0?         │
                              │ false    │  └───────────┬───────────┘
                              └──────────┘       │             │
                                               [YES]         [NO]
                                                 │             │
                                                 ▼             ▼
                                          ┌──────────┐  ┌───────────────────────┐
                                          │ Branch3a │  │  Branch4:             │
                                          │ return   │  │  amount > balance?    │
                                          │ false    │  └───────────┬───────────┘
                                          └──────────┘       │             │
                                                           [YES]         [NO]
                                                             │             │
                                                             ▼             ▼
                                                      ┌──────────┐  ┌──────────────┐
                                                      │ Branch4a │  │  Branch4b    │
                                                      │ return   │  │ balance -=   │
                                                      │ false    │  │ amount;      │
                                                      └──────────┘  │ return true  │
                                                                    └──────────────┘
```

### Branch List for withdraw()

| Branch ID | Condition | Outcome |
|-----------|-----------|---------|
| Branch1a | status == CLOSED | TRUE → return false |
| Branch2a | status == SUSPENDED | TRUE → return false |
| Branch3a | amount <= 0 | TRUE → return false |
| Branch4a | amount > balance | TRUE → return false |
| Branch4b | amount > balance | FALSE → success |

### Test → Branch Mapping for withdraw()

| Test Method | Branch Covered | Assertion |
|-------------|----------------|-----------|
| `withdraw_Branch1a_StatusClosed` | Branch1a | Closed → false |
| `withdraw_Branch2a_StatusSuspended` | Branch2a | Suspended → false |
| `withdraw_Branch3a_AmountZero` | Branch3a | Zero → false |
| `withdraw_Branch3a_AmountNegative` | Branch3a | Negative → false |
| `withdraw_Branch4a_InsufficientBalance` | Branch4a | Overdraft → false |
| `withdraw_Branch4b_SufficientBalance` | Branch4b | Valid → success |

**Coverage: 5/5 branches = 100%**

---

## Branch Coverage Summary

| Method | Total Branches | Branches Covered | Coverage % |
|--------|----------------|------------------|------------|
| deposit() | 4 | 4 | **100%** |
| withdraw() | 5 | 5 | **100%** |
| **TOTAL** | **9** | **9** | **100%** |

---

## Conclusion

All branches in the critical `deposit()` and `withdraw()` methods are covered by tests. Coverage is proven by explicit test-to-branch mapping documented in this analysis.
