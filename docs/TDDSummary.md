# TDD Summary - CreditScoreChecker

Reference: term_project_fall_2026

---

## Overview

The CreditScoreChecker feature was developed using **Test-Driven Development (TDD)**:

1. **RED** - Write failing tests first
2. **GREEN** - Write minimum code to pass tests
3. **REFACTOR** - Clean up code while keeping tests green

---

## TDD Process Timeline

### Step 1: Write Failing Tests (RED)
**Timestamp: 2025-12-23 14:00**

Created `CreditScoreCheckerTest.java` with tests BEFORE implementation:

```java
// Tests written FIRST - class doesn't exist yet
@Test
void score650_IsEligible() {
    CreditScoreChecker checker = new CreditScoreChecker();
    assertTrue(checker.isEligible(650));  // Will fail - class missing
}

@Test
void scoreBelow650_NotEligible() {
    CreditScoreChecker checker = new CreditScoreChecker();
    assertFalse(checker.isEligible(649));  // Will fail - class missing
}
```

**Result:** Compilation error - `CreditScoreChecker` class does not exist.

---

### Step 2: Implement Minimum Code (GREEN)
**Timestamp: 2025-12-23 14:05**

Created `CreditScoreChecker.java` with minimum implementation:

```java
public class CreditScoreChecker {
    public static final int THRESHOLD_ELIGIBLE = 650;
    
    public boolean isEligible(int score) {
        return score >= THRESHOLD_ELIGIBLE;
    }
}
```

**Result:** Tests pass ✓

---

### Step 3: Add More Tests (RED)
**Timestamp: 2025-12-23 14:10**

Added tests for credit tiers:

```java
@Test
void tier_Excellent() {
    assertEquals("Excellent", checker.getTier(750));  // Will fail - method missing
}

@Test
void tier_Good() {
    assertEquals("Good", checker.getTier(650));  // Will fail - method missing
}
```

**Result:** Tests fail - `getTier()` method not implemented.

---

### Step 4: Implement Tiers (GREEN)
**Timestamp: 2025-12-23 14:15**

Added tier calculation:

```java
public String getTier(int score) {
    if (score >= THRESHOLD_EXCELLENT) return "Excellent";
    if (score >= THRESHOLD_GOOD) return "Good";
    if (score >= THRESHOLD_FAIR) return "Fair";
    return "Poor";
}
```

**Result:** All tests pass ✓

---

### Step 5: Add Credit Limits Tests (RED → GREEN)
**Timestamp: 2025-12-23 14:20**

```java
@Test
void maxCredit_Excellent() {
    assertEquals(50000, checker.getMaxCredit(750));
}
```

Implemented `getMaxCredit()` method.

---

### Step 6: Add Client Integration (RED → GREEN)
**Timestamp: 2025-12-23 14:25**

```java
@Test
void clientEligibility() {
    Client client = new Client("C1", "John");
    client.setCreditScore(700);
    assertTrue(checker.isEligible(client));
}
```

Added overloaded method for Client objects.

---

## Final Implementation

```java
public class CreditScoreChecker {
    
    public static final int THRESHOLD_ELIGIBLE = 650;
    public static final int THRESHOLD_EXCELLENT = 750;
    public static final int THRESHOLD_GOOD = 650;
    public static final int THRESHOLD_FAIR = 550;
    
    public boolean isEligible(int score) {
        return score >= THRESHOLD_ELIGIBLE;
    }
    
    public boolean isEligible(Client client) {
        if (client == null) return false;
        return isEligible(client.getCreditScore());
    }
    
    public String getTier(int score) {
        if (score >= THRESHOLD_EXCELLENT) return "Excellent";
        if (score >= THRESHOLD_GOOD) return "Good";
        if (score >= THRESHOLD_FAIR) return "Fair";
        return "Poor";
    }
    
    public double getMaxCredit(int score) {
        if (score >= THRESHOLD_EXCELLENT) return 50000;
        if (score >= THRESHOLD_GOOD) return 25000;
        if (score >= THRESHOLD_FAIR) return 10000;
        return 0;
    }
}
```

---

## Test Summary

| Test ID | Description | Status |
|---------|-------------|--------|
| TDD01 | Score >= 650 is eligible | ✓ Pass |
| TDD02 | Score < 650 is NOT eligible | ✓ Pass |
| TDD03 | Boundary: exactly 650 | ✓ Pass |
| TDD04 | Boundary: exactly 649 | ✓ Pass |
| TDD05 | Tier: Excellent (750+) | ✓ Pass |
| TDD06 | Tier: Good (650-749) | ✓ Pass |
| TDD07 | Tier: Fair (550-649) | ✓ Pass |
| TDD08 | Tier: Poor (<550) | ✓ Pass |
| TDD09 | Max credit: Excellent | ✓ Pass |
| TDD10 | Max credit: Good | ✓ Pass |
| TDD11 | Max credit: Fair | ✓ Pass |
| TDD12 | Max credit: Poor | ✓ Pass |
| TDD13 | Client eligibility | ✓ Pass |
| TDD14 | Null client | ✓ Pass |

**Total: 14 tests, 14 passed**

---

## Benefits of TDD Demonstrated

1. **Clear Requirements** - Tests define expected behavior
2. **High Coverage** - All code has tests
3. **Confidence** - Safe to refactor
4. **Documentation** - Tests serve as examples
5. **Design Quality** - Forces modular design
