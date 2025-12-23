package banking.whitebox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import banking.Account;

/**
 * White-Box Tests for Account class.
 * 
 * Testing based on INTERNAL CODE STRUCTURE (Control Flow).
 * Each test explicitly maps to a branch in the CFG.
 * 
 * Coverage is proven by TEST DESIGN (branch→test mapping).
 * See docs/WhiteBoxAnalysis.md for Control Flow Graphs.
 * See docs/branch-coverage-matrix.csv for mapping.
 * 
 * Reference: term_project_fall_2026
 */
@DisplayName("White-Box Tests")
public class AccountWhiteBoxTests {
    
    // ==========================================================
    // DEPOSIT METHOD - CONTROL FLOW GRAPH BRANCHES
    // 
    // CFG for deposit():
    //   START -> [Branch1: status==CLOSED?]
    //            |YES -> return false (Branch1a)
    //            |NO  -> [Branch2: amount<=0?]
    //                    |YES -> return false (Branch2a)
    //                    |NO  -> balance+=amount; return true (Branch2b)
    // ==========================================================
    
    @Test
    @DisplayName("WB-D-Branch1a: status==CLOSED -> return false")
    void deposit_Branch1a_StatusClosed() {
        // This test covers: Branch1a (status == CLOSED is TRUE)
        Account account = new Account("WB-D1A", 100.0, Account.CLOSED);
        boolean result = account.deposit(50.0);
        assertFalse(result, "Branch1a: Deposit should fail when account is CLOSED");
        assertEquals(100.0, account.getBalance(), "Balance unchanged after failed deposit");
    }
    
    @Test
    @DisplayName("WB-D-Branch1b: status!=CLOSED -> continue to Branch2")
    void deposit_Branch1b_StatusNotClosed() {
        // This test covers: Branch1b (status == CLOSED is FALSE)
        Account account = new Account("WB-D1B", 100.0, Account.VERIFIED);
        boolean result = account.deposit(50.0);
        assertTrue(result, "Branch1b: Deposit should succeed when account is NOT CLOSED");
    }
    
    @Test
    @DisplayName("WB-D-Branch2a-Zero: amount<=0 (zero) -> return false")
    void deposit_Branch2a_AmountZero() {
        // This test covers: Branch2a (amount <= 0 is TRUE, with zero)
        Account account = new Account("WB-D2AZ", 100.0, Account.VERIFIED);
        boolean result = account.deposit(0);
        assertFalse(result, "Branch2a: Deposit of zero should return false");
        assertEquals(100.0, account.getBalance());
    }
    
    @Test
    @DisplayName("WB-D-Branch2a-Negative: amount<=0 (negative) -> return false")
    void deposit_Branch2a_AmountNegative() {
        // This test covers: Branch2a (amount <= 0 is TRUE, with negative)
        Account account = new Account("WB-D2AN", 100.0, Account.VERIFIED);
        boolean result = account.deposit(-50.0);
        assertFalse(result, "Branch2a: Deposit of negative should return false");
        assertEquals(100.0, account.getBalance());
    }
    
    @Test
    @DisplayName("WB-D-Branch2b: amount>0 -> add to balance, return true")
    void deposit_Branch2b_AmountPositive() {
        // This test covers: Branch2b (amount <= 0 is FALSE -> SUCCESS PATH)
        Account account = new Account("WB-D2B", 100.0, Account.VERIFIED);
        boolean result = account.deposit(50.0);
        assertTrue(result, "Branch2b: Deposit of positive amount should succeed");
        assertEquals(150.0, account.getBalance(), "Balance should increase by deposit amount");
    }
    
    // ==========================================================
    // WITHDRAW METHOD - CONTROL FLOW GRAPH BRANCHES
    // 
    // CFG for withdraw():
    //   START -> [Branch1: status==CLOSED?]
    //            |YES -> return false (Branch1a)
    //            |NO  -> [Branch2: status==SUSPENDED?]
    //                    |YES -> return false (Branch2a)
    //                    |NO  -> [Branch3: amount<=0?]
    //                            |YES -> return false (Branch3a)
    //                            |NO  -> [Branch4: amount>balance?]
    //                                    |YES -> return false (Branch4a)
    //                                    |NO  -> balance-=amount; return true (Branch4b)
    // ==========================================================
    
    @Test
    @DisplayName("WB-W-Branch1a: status==CLOSED -> return false")
    void withdraw_Branch1a_StatusClosed() {
        // This test covers: Branch1a (status == CLOSED is TRUE)
        Account account = new Account("WB-W1A", 100.0, Account.CLOSED);
        boolean result = account.withdraw(50.0);
        assertFalse(result, "Branch1a: Withdraw should fail when account is CLOSED");
        assertEquals(100.0, account.getBalance());
    }
    
    @Test
    @DisplayName("WB-W-Branch2a: status==SUSPENDED -> return false")
    void withdraw_Branch2a_StatusSuspended() {
        // This test covers: Branch1b (not CLOSED) + Branch2a (status == SUSPENDED is TRUE)
        Account account = new Account("WB-W2A", 100.0, Account.SUSPENDED);
        boolean result = account.withdraw(50.0);
        assertFalse(result, "Branch2a: Withdraw should fail when account is SUSPENDED");
        assertEquals(100.0, account.getBalance());
    }
    
    @Test
    @DisplayName("WB-W-Branch3a-Zero: amount<=0 (zero) -> return false")
    void withdraw_Branch3a_AmountZero() {
        // This test covers: Branch3a (amount <= 0 is TRUE, with zero)
        Account account = new Account("WB-W3AZ", 100.0, Account.VERIFIED);
        boolean result = account.withdraw(0);
        assertFalse(result, "Branch3a: Withdraw of zero should return false");
        assertEquals(100.0, account.getBalance());
    }
    
    @Test
    @DisplayName("WB-W-Branch3a-Negative: amount<=0 (negative) -> return false")
    void withdraw_Branch3a_AmountNegative() {
        // This test covers: Branch3a (amount <= 0 is TRUE, with negative)
        Account account = new Account("WB-W3AN", 100.0, Account.VERIFIED);
        boolean result = account.withdraw(-50.0);
        assertFalse(result, "Branch3a: Withdraw of negative should return false");
        assertEquals(100.0, account.getBalance());
    }
    
    @Test
    @DisplayName("WB-W-Branch4a: amount>balance -> return false")
    void withdraw_Branch4a_InsufficientBalance() {
        // This test covers: Branch4a (amount > balance is TRUE)
        Account account = new Account("WB-W4A", 100.0, Account.VERIFIED);
        boolean result = account.withdraw(150.0);
        assertFalse(result, "Branch4a: Withdraw should fail when amount exceeds balance");
        assertEquals(100.0, account.getBalance());
    }
    
    @Test
    @DisplayName("WB-W-Branch4b: amount<=balance -> subtract, return true (SUCCESS)")
    void withdraw_Branch4b_SufficientBalance() {
        // This test covers: Branch4b (amount > balance is FALSE -> SUCCESS PATH)
        Account account = new Account("WB-W4B", 100.0, Account.VERIFIED);
        boolean result = account.withdraw(50.0);
        assertTrue(result, "Branch4b: Withdraw should succeed when amount <= balance");
        assertEquals(50.0, account.getBalance(), "Balance should decrease by withdraw amount");
    }
    
    // ==========================================================
    // BRANCH COVERAGE SUMMARY (Manual Calculation)
    // 
    // deposit() has 3 branches: Branch1a, Branch2a, Branch2b
    //   - Branch1a: Covered by deposit_Branch1a_StatusClosed
    //   - Branch1b: Covered by deposit_Branch1b_StatusNotClosed
    //   - Branch2a: Covered by deposit_Branch2a_AmountZero, deposit_Branch2a_AmountNegative
    //   - Branch2b: Covered by deposit_Branch2b_AmountPositive
    //   -> 100% branch coverage for deposit()
    // 
    // withdraw() has 5 branches: Branch1a, Branch2a, Branch3a, Branch4a, Branch4b
    //   - Branch1a: Covered by withdraw_Branch1a_StatusClosed
    //   - Branch2a: Covered by withdraw_Branch2a_StatusSuspended
    //   - Branch3a: Covered by withdraw_Branch3a_AmountZero, withdraw_Branch3a_AmountNegative
    //   - Branch4a: Covered by withdraw_Branch4a_InsufficientBalance
    //   - Branch4b: Covered by withdraw_Branch4b_SufficientBalance
    //   -> 100% branch coverage for withdraw()
    // 
    // TOTAL: 100% branch coverage achieved by test design
    // ==========================================================
}
