# State Transition Matrix

Reference: term_project_fall_2026

---

## Account States

| State | Description |
|-------|-------------|
| **Unverified** | Initial state for new accounts |
| **Verified** | Account is fully active |
| **Suspended** | Account has restrictions (violation) |
| **Closed** | Terminal state (admin action) |

---

## State Transition Diagram

```
                        ┌────────────────┐
                        │   UNVERIFIED   │ (Initial State)
                        └───────┬────────┘
                                │
                           verify()
                                │
                                ▼
                        ┌────────────────┐
            ┌───────────│    VERIFIED    │───────────┐
            │           └───────┬────────┘           │
            │                   │                    │
       reinstate()         violation()           close()
       (appeal)                 │                    │
            │                   ▼                    │
            │           ┌────────────────┐           │
            └───────────│   SUSPENDED    │           │
                        └───────┬────────┘           │
                                │                    │
                            close()                  │
                                │                    │
                                ▼                    ▼
                        ┌────────────────┐
                        │     CLOSED     │ (Terminal State)
                        └────────────────┘
```

---

## Transition Table

| From State | Action | To State | Valid? | Test ID |
|------------|--------|----------|--------|---------|
| Unverified | verify() | Verified | ✓ Yes | ST01 |
| Unverified | suspend() | - | ✗ No | ST06 |
| Unverified | close() | Closed | ✓ Yes | ST04a |
| Verified | verify() | - | ✗ No | ST05 |
| Verified | suspend() | Suspended | ✓ Yes | ST02 |
| Verified | close() | Closed | ✓ Yes | ST04b |
| Suspended | verify() | - | ✗ No | ST07a |
| Suspended | reinstate() | Verified | ✓ Yes | ST03 |
| Suspended | close() | Closed | ✓ Yes | ST04c |
| Closed | verify() | - | ✗ No | ST07b |
| Closed | reinstate() | - | ✗ No | ST08 |
| Closed | close() | - | ✗ No | ST09 |

---

## State-Dependent Operations Matrix

| Operation | Unverified | Verified | Suspended | Closed |
|-----------|------------|----------|-----------|--------|
| deposit() | ✓ Allowed | ✓ Allowed | ✓ Allowed | ✗ Blocked |
| withdraw() | ✓ Allowed | ✓ Allowed | ✗ Blocked | ✗ Blocked |
| transfer() | ✗ Blocked | ✓ Allowed | ✗ Blocked | ✗ Blocked |
| view() | ✓ Allowed | ✓ Allowed | ✓ Allowed | ✓ Allowed |

---

## Test Scenario Mapping

### Valid Transitions Tested

| Test ID | Scenario | From | To | Status |
|---------|----------|------|-----|--------|
| ST01 | Verify new account | Unverified | Verified | ✓ Pass |
| ST02 | Suspend on violation | Verified | Suspended | ✓ Pass |
| ST03 | Reinstate on appeal | Suspended | Verified | ✓ Pass |
| ST04a | Close from Unverified | Unverified | Closed | ✓ Pass |
| ST04b | Close from Verified | Verified | Closed | ✓ Pass |
| ST04c | Close from Suspended | Suspended | Closed | ✓ Pass |

### Invalid Transitions Tested

| Test ID | Scenario | From | Attempted | Status |
|---------|----------|------|-----------|--------|
| ST05 | Already verified | Verified | verify() | ✓ Rejected |
| ST06 | Can't suspend unverified | Unverified | suspend() | ✓ Rejected |
| ST07a | Can't verify suspended | Suspended | verify() | ✓ Rejected |
| ST07b | Can't verify closed | Closed | verify() | ✓ Rejected |
| ST08 | Can't reinstate closed | Closed | reinstate() | ✓ Rejected |
| ST09 | Can't close already closed | Closed | close() | ✓ Rejected |

---

## Summary

- **Valid transitions tested:** 6
- **Invalid transitions tested:** 6
- **State-dependent operations tested:** 7
- **Total state tests:** 19
- **All tests passing:** ✓
