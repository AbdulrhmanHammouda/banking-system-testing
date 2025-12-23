# White-Box Analysis

## Overview

White-box testing examines the **internal structure** of code. As per project specification:
> "Analyze TransactionProcessor code for decision paths, loops and branches"

Coverage is proven by **test design** (branch→test mapping), not automated tools.

---

## 1. TransactionProcessor Analysis

### 1.1 deposit(Account account, double amount) CFG

```java
public Result deposit(Account account, double amount) {
    if (account == null) {                    // Decision 1
        return new Result(false, "Account is null");  // Branch 1a
    }
    boolean success = account.deposit(amount);
    if (success) {                            // Decision 2
        return new Result(true, "Deposited $" + amount);  // Branch 2a
    } else {
        return new Result(false, "Deposit failed");  // Branch 2b
    }
}
```

**Control Flow Graph:**
```
         ┌─────────┐
         │  START  │
         └────┬────┘
              │
              ▼
    ┌─────────────────────┐
    │  Decision 1:        │
    │  account == null?   │
    └──────────┬──────────┘
          ┌────┴────┐
        [YES]     [NO]
          │         │
          ▼         ▼
    ┌──────────┐  ┌─────────────────┐
    │ Branch1a │  │ account.deposit │
    │ "Account │  └────────┬────────┘
    │ is null" │           │
    └──────────┘           ▼
                 ┌─────────────────────┐
                 │  Decision 2:        │
                 │  success?           │
                 └──────────┬──────────┘
                       ┌────┴────┐
                     [YES]     [NO]
                       │         │
                       ▼         ▼
                 ┌──────────┐  ┌──────────┐
                 │ Branch2a │  │ Branch2b │
                 │ "Deposit │  │ "Deposit │
                 │ $amount" │  │ failed"  │
                 └──────────┘  └──────────┘
```

**Branch Coverage:**

| Branch | Condition | Test |
|--------|-----------|------|
| 1a | account == null | `deposit_Branch1a_NullAccount` |
| 2a | deposit succeeds | `deposit_Branch2a_Success` |
| 2b | deposit fails | `deposit_Branch2b_Failure` |

**Coverage: 3/3 = 100%**

---

### 1.2 withdraw(Account account, double amount) CFG

```java
public Result withdraw(Account account, double amount) {
    if (account == null) {                    // Decision 1
        return new Result(false, "Account is null");  // Branch 1a
    }
    if (amount > account.getBalance()) {      // Decision 2
        return new Result(false, "Insufficient funds");  // Branch 2a
    }
    boolean success = account.withdraw(amount);
    if (success) {                            // Decision 3
        return new Result(true, "Withdrew $" + amount);  // Branch 3a
    } else {
        return new Result(false, "Withdrawal failed");  // Branch 3b
    }
}
```

**Control Flow Graph:**
```
              ┌─────────┐
              │  START  │
              └────┬────┘
                   │
                   ▼
         ┌─────────────────────┐
         │  Decision 1:        │
         │  account == null?   │
         └──────────┬──────────┘
               ┌────┴────┐
             [YES]     [NO]
               │         │
               ▼         ▼
         ┌──────────┐  ┌─────────────────────┐
         │ Branch1a │  │  Decision 2:        │
         │ "null"   │  │  amount > balance?  │
         └──────────┘  └──────────┬──────────┘
                            ┌────┴────┐
                          [YES]     [NO]
                            │         │
                            ▼         ▼
                      ┌──────────┐  ┌───────────────────┐
                      │ Branch2a │  │ account.withdraw  │
                      │ "Insuf-  │  └─────────┬─────────┘
                      │ ficient" │            │
                      └──────────┘            ▼
                                   ┌─────────────────────┐
                                   │  Decision 3:        │
                                   │  success?           │
                                   └──────────┬──────────┘
                                         ┌────┴────┐
                                       [YES]     [NO]
                                         │         │
                                         ▼         ▼
                                   ┌──────────┐  ┌──────────┐
                                   │ Branch3a │  │ Branch3b │
                                   │ "Withdrew│  │ "failed" │
                                   └──────────┘  └──────────┘
```

**Branch Coverage:**

| Branch | Condition | Test |
|--------|-----------|------|
| 1a | account == null | `withdraw_Branch1a_NullAccount` |
| 2a | amount > balance | `withdraw_Branch2a_InsufficientFunds` |
| 3a | withdraw succeeds | `withdraw_Branch3a_Success` |
| 3b | withdraw fails | `withdraw_Branch3b_Failure` |

**Coverage: 4/4 = 100%**

---

### 1.3 transfer(Account from, Account to, double amount) CFG

```java
public Result transfer(Account from, Account to, double amount) {
    if (from == null || to == null) {         // Decision 1
        return new Result(false, "Account not found");  // Branch 1a
    }
    boolean success = from.transfer(to, amount);
    if (success) {                            // Decision 2
        return new Result(true, "Transferred $" + amount);  // Branch 2a
    } else {
        return new Result(false, "Transfer failed");  // Branch 2b
    }
}
```

**Control Flow Graph:**
```
         ┌─────────┐
         │  START  │
         └────┬────┘
              │
              ▼
    ┌───────────────────────────┐
    │  Decision 1:              │
    │  from==null || to==null?  │
    └──────────────┬────────────┘
              ┌────┴────┐
            [YES]     [NO]
              │         │
              ▼         ▼
        ┌──────────┐  ┌────────────────┐
        │ Branch1a │  │ from.transfer  │
        │ "Account │  └───────┬────────┘
        │ not found│          │
        └──────────┘          ▼
                    ┌─────────────────────┐
                    │  Decision 2:        │
                    │  success?           │
                    └──────────┬──────────┘
                          ┌────┴────┐
                        [YES]     [NO]
                          │         │
                          ▼         ▼
                    ┌──────────┐  ┌──────────┐
                    │ Branch2a │  │ Branch2b │
                    │ "Trans-  │  │ "failed" │
                    │ ferred"  │  │          │
                    └──────────┘  └──────────┘
```

**Branch Coverage:**

| Branch | Condition | Test |
|--------|-----------|------|
| 1a | from/to null | `transfer_Branch1a_FromNull`, `transfer_Branch1a_ToNull` |
| 2a | transfer succeeds | `transfer_Branch2a_Success` |
| 2b | transfer fails | `transfer_Branch2b_Failure` |

**Coverage: 3/3 = 100%**

---

## 2. Account.deposit() and Account.withdraw() Analysis

(Also tested for completeness - see AccountWhiteBoxTests.java)

### 2.1 Account.deposit() Branches

| Branch | Condition | Test |
|--------|-----------|------|
| 1a | status == CLOSED | `deposit_Branch1a_StatusClosed` |
| 2a | amount <= 0 | `deposit_Branch2a_AmountZero/Negative` |
| 2b | amount > 0 (success) | `deposit_Branch2b_AmountPositive` |

**Coverage: 100%**

### 2.2 Account.withdraw() Branches

| Branch | Condition | Test |
|--------|-----------|------|
| 1a | status == CLOSED | `withdraw_Branch1a_StatusClosed` |
| 2a | status == SUSPENDED | `withdraw_Branch2a_StatusSuspended` |
| 3a | amount <= 0 | `withdraw_Branch3a_AmountZero/Negative` |
| 4a | amount > balance | `withdraw_Branch4a_InsufficientBalance` |
| 4b | success path | `withdraw_Branch4b_SufficientBalance` |

**Coverage: 100%**

---

## 3. Overall Branch Coverage Summary

| Class | Method | Branches | Covered | Coverage |
|-------|--------|----------|---------|----------|
| TransactionProcessor | deposit() | 3 | 3 | **100%** |
| TransactionProcessor | withdraw() | 4 | 4 | **100%** |
| TransactionProcessor | transfer() | 3 | 3 | **100%** |
| Account | deposit() | 3 | 3 | **100%** |
| Account | withdraw() | 5 | 5 | **100%** |
| **TOTAL** | | **18** | **18** | **100%** |

---

## 4. Cyclomatic Complexity

| Method | Formula V(G) = E - N + 2 | Complexity |
|--------|--------------------------|------------|
| TransactionProcessor.deposit() | 6 - 5 + 2 | 3 |
| TransactionProcessor.withdraw() | 8 - 6 + 2 | 4 |
| TransactionProcessor.transfer() | 6 - 5 + 2 | 3 |
| Account.deposit() | 6 - 5 + 2 | 3 |
| Account.withdraw() | 10 - 8 + 2 | 4 |

All methods have moderate complexity (3-4), indicating good testability.
