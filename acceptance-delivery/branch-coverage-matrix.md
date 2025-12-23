# Branch Coverage Matrix

This matrix maps each branch to the tests that cover it.
X = Test covers this branch

## deposit() Branches

| Branch ID | Description | WB-D-Branch1a | WB-D-Branch1b | WB-D-Branch2a-Zero | WB-D-Branch2a-Neg | WB-D-Branch2b |
|-----------|-------------|---------------|---------------|--------------------|--------------------|---------------|
| Branch1a | status==CLOSED (T) | X | | | | |
| Branch1b | status==CLOSED (F) | | X | X | X | X |
| Branch2a | amount<=0 (T) | | | X | X | |
| Branch2b | amount<=0 (F) | | X | | | X |

## withdraw() Branches

| Branch ID | Description | WB-W-Branch1a | WB-W-Branch2a | WB-W-Branch3a-Zero | WB-W-Branch3a-Neg | WB-W-Branch4a | WB-W-Branch4b |
|-----------|-------------|---------------|---------------|--------------------|--------------------|---------------|---------------|
| Branch1a | status==CLOSED (T) | X | | | | | |
| Branch2a | status==SUSPENDED (T) | | X | | | | |
| Branch3a | amount<=0 (T) | | | X | X | | |
| Branch4a | amount>balance (T) | | | | | X | |
| Branch4b | amount>balance (F) | | | | | | X |

## Coverage Calculation

### deposit()
- Total branches: 4 (Branch1a, Branch1b, Branch2a, Branch2b)
- Branches covered: 4
- **Coverage: 100%**

### withdraw()
- Total branches: 5 (Branch1a, Branch2a, Branch3a, Branch4a, Branch4b)
- Branches covered: 5
- **Coverage: 100%**

### Overall
- Total branches: 9
- Covered: 9
- **Overall Coverage: 100%**
