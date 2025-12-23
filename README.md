# Banking System Testing Project

A simple Java banking system demonstrating software testing techniques.

## Quick Start

```bash
# Run all tests
mvn test
```

## Project Structure

```
src/main/java/banking/     # Source code
src/test/java/banking/     # Test code
  ├── blackbox/            # Black-box tests (EP, BVA)
  ├── whitebox/            # White-box tests (branch coverage)
  ├── state/               # State-based tests
  ├── integration/         # Integration tests
  ├── ui/                  # UI simulation tests
  └── tdd/                 # TDD tests
docs/                      # Documentation
```

## Testing Techniques

| Technique | File | Focus |
|-----------|------|-------|
| Black-Box | AccountBlackBoxTests.java | EP, BVA |
| White-Box | AccountWhiteBoxTests.java | Branch coverage |
| State-Based | AccountStateTests.java | State transitions |
| Integration | BankingIntegrationTests.java | Full flow |
| UI | UISimulationTests.java | Button states |
| TDD | CreditScoreCheckerTest.java | Test-first |

## Documentation

- [Final Report](docs/FinalReport.md)
- [Test Cases](docs/TestCases.md)
- [White-Box Analysis](docs/WhiteBoxAnalysis.md)
- [State Diagram](docs/StateDiagram.md)
- [TDD Summary](docs/TDDSummary.md)

## Requirements

- Java 21
- Maven 3.8+
