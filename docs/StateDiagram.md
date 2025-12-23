# State Diagram

## Account Status State Machine

### States

| State | Description |
|-------|-------------|
| Unverified | Initial state for new accounts |
| Verified | Account is active and fully operational |
| Suspended | Account has restrictions due to violations |
| Closed | Terminal state - account is permanently closed |

---

## State Transition Diagram

```
                    ┌──────────────┐
                    │  UNVERIFIED  │ (Initial)
                    └──────┬───────┘
                           │
                      verify()
                           │
                           ▼
                    ┌──────────────┐
        ┌──────────│   VERIFIED   │──────────┐
        │          └──────────────┘          │
        │                 │                  │
   reinstate()       suspend()           close()
        │                 │                  │
        │                 ▼                  │
        │          ┌──────────────┐          │
        └──────────│  SUSPENDED   │          │
                   └──────┬───────┘          │
                          │                  │
                      close()                │
                          │                  │
                          ▼                  ▼
                    ┌──────────────┐
                    │    CLOSED    │ (Terminal)
                    └──────────────┘
```

---

## Transition Table

| From State | Action | To State | Valid? |
|------------|--------|----------|--------|
| Unverified | verify() | Verified | ✓ |
| Unverified | suspend() | - | ✗ |
| Unverified | reinstate() | - | ✗ |
| Unverified | close() | Closed | ✓ |
| Verified | verify() | - | ✗ |
| Verified | suspend() | Suspended | ✓ |
| Verified | reinstate() | - | ✗ |
| Verified | close() | Closed | ✓ |
| Suspended | verify() | - | ✗ |
| Suspended | suspend() | - | ✗ |
| Suspended | reinstate() | Verified | ✓ |
| Suspended | close() | Closed | ✓ |
| Closed | Any | - | ✗ |

---

## State-Dependent Operations

| Operation | Unverified | Verified | Suspended | Closed |
|-----------|------------|----------|-----------|--------|
| deposit() | ✓ | ✓ | ✓ | ✗ |
| withdraw() | ✓ | ✓ | ✗ | ✗ |
| transfer() | ✗ | ✓ | ✗ | ✗ |

---

## Test Coverage

All state transitions are tested in `AccountStateTests.java`:

- 6 valid transitions tested
- 4 invalid transitions tested  
- 7 state-dependent operations tested
