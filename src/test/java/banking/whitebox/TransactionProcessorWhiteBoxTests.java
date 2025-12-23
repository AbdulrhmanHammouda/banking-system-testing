package banking.whitebox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import banking.Account;
import banking.TransactionProcessor;
import banking.TransactionProcessor.Result;

/**
 * White-Box Tests for TransactionProcessor class.
 * 
 * As per project specification: "Analyze TransactionProcessor code for:
 *   - Decision paths
 *   - Loops and branches"
 * 
 * Coverage is proven by TEST DESIGN (branch→test mapping).
 * See docs/WhiteBoxAnalysis.md for Control Flow Graphs.
 */
@DisplayName("White-Box Tests - TransactionProcessor")
public class TransactionProcessorWhiteBoxTests {
    
    private TransactionProcessor processor;
    
    @BeforeEach
    void setUp() {
        processor = new TransactionProcessor();
    }
    
    // ==========================================================
    // DEPOSIT METHOD - CONTROL FLOW GRAPH BRANCHES
    // 
    // CFG for deposit(Account, double):
    //   START -> [Branch1: account == null?]
    //            |YES -> return Result(false, "Account is null") (Branch1a)
    //            |NO  -> [call account.deposit(amount)]
    //                    [Branch2: success?]
    //                    |YES -> return Result(true, "Deposited") (Branch2a)
    //                    |NO  -> return Result(false, "Deposit failed") (Branch2b)
    // ==========================================================
    
    @Test
    @DisplayName("TP-D-Branch1a: account == null -> return failure")
    void deposit_Branch1a_NullAccount() {
        // This test covers: Branch1a (account == null is TRUE)
        Result result = processor.deposit(null, 100.0);
        assertFalse(result.isSuccess(), "Branch1a: Null account should return failure");
        assertEquals("Account is null", result.getMessage());
    }
    
    @Test
    @DisplayName("TP-D-Branch2a: deposit succeeds -> return success")
    void deposit_Branch2a_Success() {
        // This test covers: Branch1b (not null) + Branch2a (deposit succeeds)
        Account account = new Account("TP-D1", 100.0, Account.VERIFIED);
        Result result = processor.deposit(account, 50.0);
        assertTrue(result.isSuccess(), "Branch2a: Valid deposit should succeed");
        assertTrue(result.getMessage().contains("50"));
        assertEquals(150.0, account.getBalance());
    }
    
    @Test
    @DisplayName("TP-D-Branch2b: deposit fails -> return failure")
    void deposit_Branch2b_Failure() {
        // This test covers: Branch1b (not null) + Branch2b (deposit fails)
        Account account = new Account("TP-D2", 100.0, Account.CLOSED);
        Result result = processor.deposit(account, 50.0);
        assertFalse(result.isSuccess(), "Branch2b: Deposit on closed account should fail");
        assertEquals("Deposit failed", result.getMessage());
    }
    
    // ==========================================================
    // WITHDRAW METHOD - CONTROL FLOW GRAPH BRANCHES
    // 
    // CFG for withdraw(Account, double):
    //   START -> [Branch1: account == null?]
    //            |YES -> return Result(false, "Account is null") (Branch1a)
    //            |NO  -> [Branch2: amount > balance?]
    //                    |YES -> return Result(false, "Insufficient funds") (Branch2a)
    //                    |NO  -> [call account.withdraw(amount)]
    //                            [Branch3: success?]
    //                            |YES -> return Result(true, "Withdrew") (Branch3a)
    //                            |NO  -> return Result(false, "Withdrawal failed") (Branch3b)
    // ==========================================================
    
    @Test
    @DisplayName("TP-W-Branch1a: account == null -> return failure")
    void withdraw_Branch1a_NullAccount() {
        // This test covers: Branch1a (account == null is TRUE)
        Result result = processor.withdraw(null, 50.0);
        assertFalse(result.isSuccess(), "Branch1a: Null account should return failure");
        assertEquals("Account is null", result.getMessage());
    }
    
    @Test
    @DisplayName("TP-W-Branch2a: amount > balance -> insufficient funds")
    void withdraw_Branch2a_InsufficientFunds() {
        // This test covers: Branch1b (not null) + Branch2a (insufficient funds)
        Account account = new Account("TP-W1", 100.0, Account.VERIFIED);
        Result result = processor.withdraw(account, 500.0);
        assertFalse(result.isSuccess(), "Branch2a: Should fail for insufficient funds");
        assertEquals("Insufficient funds", result.getMessage());
    }
    
    @Test
    @DisplayName("TP-W-Branch3a: withdraw succeeds -> return success")
    void withdraw_Branch3a_Success() {
        // This test covers: Branch3a (withdraw succeeds)
        Account account = new Account("TP-W2", 100.0, Account.VERIFIED);
        Result result = processor.withdraw(account, 50.0);
        assertTrue(result.isSuccess(), "Branch3a: Valid withdrawal should succeed");
        assertTrue(result.getMessage().contains("50"));
        assertEquals(50.0, account.getBalance());
    }
    
    @Test
    @DisplayName("TP-W-Branch3b: withdraw fails (suspended) -> return failure")
    void withdraw_Branch3b_Failure() {
        // This test covers: Branch3b (withdraw fails - account suspended)
        Account account = new Account("TP-W3", 100.0, Account.SUSPENDED);
        Result result = processor.withdraw(account, 50.0);
        assertFalse(result.isSuccess(), "Branch3b: Withdrawal on suspended should fail");
        assertEquals("Withdrawal failed", result.getMessage());
    }
    
    // ==========================================================
    // TRANSFER METHOD - CONTROL FLOW GRAPH BRANCHES
    // 
    // CFG for transfer(Account, Account, double):
    //   START -> [Branch1: from == null || to == null?]
    //            |YES -> return Result(false, "Account not found") (Branch1a)
    //            |NO  -> [call from.transfer(to, amount)]
    //                    [Branch2: success?]
    //                    |YES -> return Result(true, "Transferred") (Branch2a)
    //                    |NO  -> return Result(false, "Transfer failed") (Branch2b)
    // ==========================================================
    
    @Test
    @DisplayName("TP-T-Branch1a-FromNull: from == null -> failure")
    void transfer_Branch1a_FromNull() {
        // This test covers: Branch1a (from is null)
        Account to = new Account("TP-T1", 100.0, Account.VERIFIED);
        Result result = processor.transfer(null, to, 50.0);
        assertFalse(result.isSuccess(), "Branch1a: Null 'from' should fail");
        assertEquals("Account not found", result.getMessage());
    }
    
    @Test
    @DisplayName("TP-T-Branch1a-ToNull: to == null -> failure")
    void transfer_Branch1a_ToNull() {
        // This test covers: Branch1a (to is null)
        Account from = new Account("TP-T2", 100.0, Account.VERIFIED);
        Result result = processor.transfer(from, null, 50.0);
        assertFalse(result.isSuccess(), "Branch1a: Null 'to' should fail");
        assertEquals("Account not found", result.getMessage());
    }
    
    @Test
    @DisplayName("TP-T-Branch2a: transfer succeeds -> return success")
    void transfer_Branch2a_Success() {
        // This test covers: Branch2a (transfer succeeds)
        Account from = new Account("TP-T3", 100.0, Account.VERIFIED);
        Account to = new Account("TP-T4", 50.0, Account.VERIFIED);
        Result result = processor.transfer(from, to, 30.0);
        assertTrue(result.isSuccess(), "Branch2a: Valid transfer should succeed");
        assertEquals(70.0, from.getBalance());
        assertEquals(80.0, to.getBalance());
    }
    
    @Test
    @DisplayName("TP-T-Branch2b: transfer fails -> return failure")
    void transfer_Branch2b_Failure() {
        // This test covers: Branch2b (transfer fails - sender not verified)
        Account from = new Account("TP-T5", 100.0, Account.UNVERIFIED);
        Account to = new Account("TP-T6", 50.0, Account.VERIFIED);
        Result result = processor.transfer(from, to, 30.0);
        assertFalse(result.isSuccess(), "Branch2b: Transfer from unverified should fail");
        assertEquals("Transfer failed", result.getMessage());
    }
    
    // ==========================================================
    // BRANCH COVERAGE SUMMARY FOR TRANSACTIONPROCESSOR
    // 
    // deposit() has 3 outcomes: Branch1a, Branch2a, Branch2b
    //   - Branch1a: Covered by deposit_Branch1a_NullAccount
    //   - Branch2a: Covered by deposit_Branch2a_Success
    //   - Branch2b: Covered by deposit_Branch2b_Failure
    //   -> 100% branch coverage for deposit()
    // 
    // withdraw() has 4 outcomes: Branch1a, Branch2a, Branch3a, Branch3b
    //   - Branch1a: Covered by withdraw_Branch1a_NullAccount
    //   - Branch2a: Covered by withdraw_Branch2a_InsufficientFunds
    //   - Branch3a: Covered by withdraw_Branch3a_Success
    //   - Branch3b: Covered by withdraw_Branch3b_Failure
    //   -> 100% branch coverage for withdraw()
    // 
    // transfer() has 3 outcomes: Branch1a, Branch2a, Branch2b
    //   - Branch1a: Covered by transfer_Branch1a_FromNull, transfer_Branch1a_ToNull
    //   - Branch2a: Covered by transfer_Branch2a_Success
    //   - Branch2b: Covered by transfer_Branch2b_Failure
    //   -> 100% branch coverage for transfer()
    // 
    // TOTAL: 100% branch coverage for TransactionProcessor
    // ==========================================================
}
