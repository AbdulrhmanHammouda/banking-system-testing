# White-Box Analysis

## Overview

White-box testing is based on examining the **internal structure** of the code. Coverage is demonstrated by **test design** that explicitly targets each branch in the control flow.

> **Note:** Coverage was ensured by test design rather than automated tools.

---

## Control Flow Graph: deposit()

```
START
  │
  ▼
┌─────────────────────┐
│ Branch 1:           │
│ status == CLOSED?   │
└─────────────────────┘
     │         │
    YES       NO
     │         │
     ▼         ▼
 [return   ┌─────────────────────┐
  false]   │ Branch 2:           │
           │ amount <= 0?        │
           └─────────────────────┘
                │         │
               YES       NO
                │         │
                ▼         ▼
            [return   [balance += amount]
             false]        │
                           ▼
                      [return true]
                           │
                           ▼
                          END
```

### Branches in deposit()

| Branch | Condition | True | False |
|--------|-----------|------|-------|
| 1 | status == CLOSED | Return false | Continue |
| 2 | amount <= 0 | Return false | Add to balance |

### Test Coverage

| Test | Branch Covered |
|------|----------------|
| `deposit_Branch1_True_Closed` | Branch 1 = TRUE |
| `deposit_Branch1_False_NotClosed` | Branch 1 = FALSE |
| `deposit_Branch2_True_Zero` | Branch 2 = TRUE |
| `deposit_Branch2_True_Negative` | Branch 2 = TRUE |
| `deposit_Branch2_False_Positive` | Branch 2 = FALSE |

**Result: 100% branch coverage achieved**

---

## Control Flow Graph: withdraw()

```
START
  │
  ▼
┌─────────────────────┐
│ Branch 1:           │
│ status == CLOSED?   │
└─────────────────────┘
     │         │
    YES       NO
     │         │
     ▼         ▼
 [return   ┌─────────────────────┐
  false]   │ Branch 2:           │
           │ status == SUSPENDED?│
           └─────────────────────┘
                │         │
               YES       NO
                │         │
                ▼         ▼
            [return   ┌─────────────────────┐
             false]   │ Branch 3:           │
                      │ amount <= 0?        │
                      └─────────────────────┘
                           │         │
                          YES       NO
                           │         │
                           ▼         ▼
                       [return   ┌─────────────────────┐
                        false]   │ Branch 4:           │
                                 │ amount > balance?   │
                                 └─────────────────────┘
                                      │         │
                                     YES       NO
                                      │         │
                                      ▼         ▼
                                  [return   [balance -= amount]
                                   false]        │
                                                 ▼
                                            [return true]
                                                 │
                                                 ▼
                                                END
```

### Branches in withdraw()

| Branch | Condition | True | False |
|--------|-----------|------|-------|
| 1 | status == CLOSED | Return false | Continue |
| 2 | status == SUSPENDED | Return false | Continue |
| 3 | amount <= 0 | Return false | Continue |
| 4 | amount > balance | Return false | Subtract |

### Test Coverage

| Test | Branch Covered |
|------|----------------|
| `withdraw_Branch1_True_Closed` | Branch 1 = TRUE |
| `withdraw_Branch2_True_Suspended` | Branch 2 = TRUE |
| `withdraw_Branch3_True_Zero` | Branch 3 = TRUE |
| `withdraw_Branch3_True_Negative` | Branch 3 = TRUE |
| `withdraw_Branch4_True_Overdraft` | Branch 4 = TRUE |
| `withdraw_AllFalse_Success` | All branches FALSE |

**Result: 100% branch coverage achieved**

---

## Summary

- All branches in `deposit()` are covered by tests
- All branches in `withdraw()` are covered by tests
- Each test is clearly labeled with the branch it covers
- Coverage is proven by **logical analysis**, not by tools
