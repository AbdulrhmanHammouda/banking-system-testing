package banking.blackbox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import banking.Account;

/**
 * Black-Box Tests for Account class.
 * 
 * Testing based on SPECIFICATIONS only (no internal code knowledge).
 * 
 * Techniques:
 * - Equivalence Partitioning (EP)
 * - Boundary Value Analysis (BVA)
 * - Decision Tables
 * 
 * Reference: term_project_fall_2026
 */
@DisplayName("Black-Box Tests")
public class AccountBlackBoxTests {
    
    // ==========================================================
    // MANDATORY TEST CASES BB01-BB07 (from specification)
    // ==========================================================
    
    @Test
    @DisplayName("BB01: deposit(-100) -> false (invalid negative amount)")
    void BB01_depositNegativeAmount() {
        // EP: Negative amount partition
        Account account = new Account("BB01", 100.0, Account.VERIFIED);
        boolean result = account.deposit(-100);
        assertFalse(result, "BB01: Deposit of negative amount should return false");
        assertEquals(100.0, account.getBalance(), "Balance should remain unchanged");
    }
    
    @Test
    @DisplayName("BB02: deposit(0) -> false (boundary zero)")
    void BB02_depositZeroAmount() {
        // BVA: Zero boundary
        Account account = new Account("BB02", 100.0, Account.VERIFIED);
        boolean result = account.deposit(0);
        assertFalse(result, "BB02: Deposit of zero should return false");
        assertEquals(100.0, account.getBalance(), "Balance should remain unchanged");
    }
    
    @Test
    @DisplayName("BB03: deposit(1) -> true (valid minimum)")
    void BB03_depositValidMinimum() {
        // BVA: Just above zero boundary
        Account account = new Account("BB03", 100.0, Account.VERIFIED);
        boolean result = account.deposit(1);
        assertTrue(result, "BB03: Deposit of 1 should return true");
        assertEquals(101.0, account.getBalance(), "Balance should increase by 1");
    }
    
    @Test
    @DisplayName("BB04: withdraw(50) with balance 100 -> true (valid)")
    void BB04_withdrawValidAmount() {
        // EP: Valid withdrawal partition
        Account account = new Account("BB04", 100.0, Account.VERIFIED);
        boolean result = account.withdraw(50);
        assertTrue(result, "BB04: Withdraw within balance should return true");
        assertEquals(50.0, account.getBalance(), "Balance should decrease by 50");
    }
    
    @Test
    @DisplayName("BB05: withdraw(150) with balance 100 -> false (overdraft)")
    void BB05_withdrawOverdraft() {
        // EP: Overdraft partition
        Account account = new Account("BB05", 100.0, Account.VERIFIED);
        boolean result = account.withdraw(150);
        assertFalse(result, "BB05: Withdraw exceeding balance should return false");
        assertEquals(100.0, account.getBalance(), "Balance should remain unchanged");
    }
    
    @Test
    @DisplayName("BB06: withdraw(50) on Suspended account -> false")
    void BB06_withdrawOnSuspended() {
        // EP: Suspended status partition
        Account account = new Account("BB06", 100.0, Account.SUSPENDED);
        boolean result = account.withdraw(50);
        assertFalse(result, "BB06: Withdraw on Suspended account should return false");
        assertEquals(100.0, account.getBalance(), "Balance should remain unchanged");
    }
    
    @Test
    @DisplayName("BB07: deposit(50) on Closed account -> false")
    void BB07_depositOnClosed() {
        // EP: Closed status partition
        Account account = new Account("BB07", 100.0, Account.CLOSED);
        boolean result = account.deposit(50);
        assertFalse(result, "BB07: Deposit on Closed account should return false");
        assertEquals(100.0, account.getBalance(), "Balance should remain unchanged");
    }
    
    // ==========================================================
    // ADDITIONAL EQUIVALENCE PARTITIONING TESTS
    // ==========================================================
    
    @Test
    @DisplayName("EP-D1: Deposit large valid amount")
    void EP_D1_depositLargeAmount() {
        Account account = new Account("EP-D1", 100.0, Account.VERIFIED);
        assertTrue(account.deposit(10000.0));
        assertEquals(10100.0, account.getBalance());
    }
    
    @Test
    @DisplayName("EP-W1: Withdraw exact balance")
    void EP_W1_withdrawExactBalance() {
        Account account = new Account("EP-W1", 100.0, Account.VERIFIED);
        assertTrue(account.withdraw(100.0));
        assertEquals(0.0, account.getBalance());
    }
    
    @Test
    @DisplayName("EP-W2: Withdraw negative amount")
    void EP_W2_withdrawNegative() {
        Account account = new Account("EP-W2", 100.0, Account.VERIFIED);
        assertFalse(account.withdraw(-50.0));
    }
    
    // ==========================================================
    // BOUNDARY VALUE ANALYSIS TESTS
    // ==========================================================
    
    @Test
    @DisplayName("BVA-D1: Deposit 0.01 (just above zero)")
    void BVA_D1_depositJustAboveZero() {
        Account account = new Account("BVA-D1", 100.0, Account.VERIFIED);
        assertTrue(account.deposit(0.01));
        assertEquals(100.01, account.getBalance(), 0.001);
    }
    
    @Test
    @DisplayName("BVA-D2: Deposit -0.01 (just below zero)")
    void BVA_D2_depositJustBelowZero() {
        Account account = new Account("BVA-D2", 100.0, Account.VERIFIED);
        assertFalse(account.deposit(-0.01));
    }
    
    @Test
    @DisplayName("BVA-W1: Withdraw balance + 0.01 (just over limit)")
    void BVA_W1_withdrawJustOverBalance() {
        Account account = new Account("BVA-W1", 100.0, Account.VERIFIED);
        assertFalse(account.withdraw(100.01));
    }
    
    @Test
    @DisplayName("BVA-W2: Withdraw balance - 0.01 (just under limit)")
    void BVA_W2_withdrawJustUnderBalance() {
        Account account = new Account("BVA-W2", 100.0, Account.VERIFIED);
        assertTrue(account.withdraw(99.99));
        assertEquals(0.01, account.getBalance(), 0.001);
    }
    
    // ==========================================================
    // DECISION TABLE TESTS - TRANSFER
    // ==========================================================
    
    @Test
    @DisplayName("DT-T1: Transfer valid - sender Verified, target Verified, sufficient funds")
    void DT_T1_transferValid() {
        Account sender = new Account("DT-S1", 500.0, Account.VERIFIED);
        Account target = new Account("DT-T1", 100.0, Account.VERIFIED);
        assertTrue(sender.transfer(target, 200.0));
        assertEquals(300.0, sender.getBalance());
        assertEquals(300.0, target.getBalance());
    }
    
    @Test
    @DisplayName("DT-T2: Transfer invalid - sender NOT Verified")
    void DT_T2_transferSenderNotVerified() {
        Account sender = new Account("DT-S2", 500.0, Account.UNVERIFIED);
        Account target = new Account("DT-T2", 100.0, Account.VERIFIED);
        assertFalse(sender.transfer(target, 200.0));
        assertEquals(500.0, sender.getBalance());
        assertEquals(100.0, target.getBalance());
    }
    
    @Test
    @DisplayName("DT-T3: Transfer invalid - target Closed")
    void DT_T3_transferTargetClosed() {
        Account sender = new Account("DT-S3", 500.0, Account.VERIFIED);
        Account target = new Account("DT-T3", 100.0, Account.CLOSED);
        assertFalse(sender.transfer(target, 200.0));
    }
    
    @Test
    @DisplayName("DT-T4: Transfer invalid - insufficient funds")
    void DT_T4_transferInsufficientFunds() {
        Account sender = new Account("DT-S4", 100.0, Account.VERIFIED);
        Account target = new Account("DT-T4", 100.0, Account.VERIFIED);
        assertFalse(sender.transfer(target, 500.0));
    }
    
    @Test
    @DisplayName("DT-T5: Transfer invalid - null target")
    void DT_T5_transferNullTarget() {
        Account sender = new Account("DT-S5", 500.0, Account.VERIFIED);
        assertFalse(sender.transfer(null, 200.0));
    }
    
    @Test
    @DisplayName("DT-T6: Transfer invalid - amount <= 0")
    void DT_T6_transferZeroAmount() {
        Account sender = new Account("DT-S6", 500.0, Account.VERIFIED);
        Account target = new Account("DT-T6", 100.0, Account.VERIFIED);
        assertFalse(sender.transfer(target, 0));
        assertFalse(sender.transfer(target, -100.0));
    }
}
