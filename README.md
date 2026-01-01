# Banking System Testing Project

A comprehensive Java banking system demonstrating software testing techniques.

**Reference:** term_project_fall_2026

---

## Quick Start
##run the backend server Apache Tomcat using this command
```cmd
 mvn clean package cargo:run
 ```


### Windows
```cmd
build-and-test.bat
```

### Linux/Mac
```bash
chmod +x build-and-test.sh
./build-and-test.sh
```

### Using Maven directly
```bash
mvn clean test
```

---

## Project Structure

```
project/
├── src/main/java/banking/          # Production code
│   ├── Account.java                # Core account class
│   ├── Client.java                 # Client model
│   ├── AccountService.java         # State transitions
│   ├── TransactionProcessor.java   # Transaction handling
│   ├── ClientController.java       # UI simulation
│   └── CreditScoreChecker.java     # TDD feature
│
├── src/test/java/banking/          # Test code
│   ├── blackbox/                   # Black-box tests (EP, BVA)
│   ├── whitebox/                   # White-box tests (branch coverage)
│   ├── state/                      # State-based tests
│   ├── integration/                # Integration tests
│   ├── ui/                         # UI simulation tests
│   └── tdd/                        # TDD tests
│
├── docs/                           # Documentation
│   ├── TestCaseDocument.md         # All test cases
│   ├── WhiteBoxAnalysis.md         # CFGs & branch mapping
│   ├── StateTransitionMatrix.md    # State machine
│   ├── ControlFlowGraphs.md        # CFG diagrams
│   ├── TDDSummary.md               # TDD process
│   └── UIBugList.md                # UI issues
│
├── acceptance-delivery/            # Final artifacts
│   ├── branch-coverage-matrix.md   # Coverage proof
│   ├── surefire-reports/           # Test reports (after run)
│   └── AcceptanceReport.docx       # Signed acceptance
│
├── pom.xml                         # Maven config (NO JaCoCo)
├── build-and-test.bat              # Windows script
├── build-and-test.sh               # Linux/Mac script
└── README.md                       # This file
```

---

## Testing Techniques Demonstrated

| Technique | Test File | Description |
|-----------|-----------|-------------|
| **Black-Box** | AccountBlackBoxTests.java | EP, BVA, Decision Tables |
| **White-Box** | AccountWhiteBoxTests.java | Branch coverage by design |
| **State-Based** | AccountStateTests.java | State transitions |
| **Integration** | BankingIntegrationTests.java | End-to-end flows |
| **UI** | UISimulationTests.java | Button states, messages |
| **TDD** | CreditScoreCheckerTest.java | Test-first development |

---

## Requirements

- Java 21
- Maven 3.8+ (or use IntelliJ bundled Maven)

---

## Coverage Proof

Coverage is proven by **test design**, not automated tools.

See:
- `docs/WhiteBoxAnalysis.md` - Branch → Test mapping
- `acceptance-delivery/branch-coverage-matrix.md` - Coverage matrix

**Result:** 100% branch coverage for deposit() and withdraw()

---

## Test Results

Run `build-and-test.bat` or `mvn test` to execute all tests.

Expected output:
- **Total tests:** 84
- **Passed:** 84
- **Failed:** 0

---

## Documentation

| Document | Description |
|----------|-------------|
| [TestCaseDocument.md](docs/TestCaseDocument.md) | All test cases |
| [WhiteBoxAnalysis.md](docs/WhiteBoxAnalysis.md) | CFGs & coverage |
| [StateTransitionMatrix.md](docs/StateTransitionMatrix.md) | State machine |
| [TDDSummary.md](docs/TDDSummary.md) | TDD process |

---

## Acceptance

See `acceptance-delivery/AcceptanceReport.docx` for the signed acceptance report.
