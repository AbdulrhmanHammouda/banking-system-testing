package banking.whitebox;

import banking.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * White-Box Tests for Account class.
 * 
 * Testing is based on INTERNAL CODE STRUCTURE.
 * Each test explicitly covers a specific branch in the control flow.
 * 
 * Coverage is demonstrated by TEST DESIGN, not by automated tools.
 * 
 * See docs/WhiteBoxAnalysis.md for Control Flow Graphs.
 */
@DisplayName("White-Box Tests")
public class AccountWhiteBoxTests {
    
    // ==========================================================
    // DEPOSIT METHOD - BRANCH COVERAGE
    // 
    // Control Flow:
    //   Branch 1: status == CLOSED ? -> return false
    //   Branch 2: amount <= 0 ? -> return false
    //   Success: add to balance, return true
    // ==========================================================
    
    @Test
    @DisplayName("deposit: Branch 1 TRUE - account is CLOSED")
    void deposit_Branch1_True_Closed() {
        // This test covers: Branch 1 = TRUE (status is CLOSED)
        Account account = new Account("D1", 100.0, Account.CLOSED);
        assertFalse(account.deposit(50.0));
        assertEquals(100.0, account.getBalance()); // unchanged
    }
    
    @Test
    @DisplayName("deposit: Branch 1 FALSE - account is NOT closed")
    void deposit_Branch1_False_NotClosed() {
        // This test covers: Branch 1 = FALSE (continues to Branch 2)
        Account account = new Account("D2", 100.0, Account.VERIFIED);
        assertTrue(account.deposit(50.0));
    }
    
    @Test
    @DisplayName("deposit: Branch 2 TRUE - amount is zero")
    void deposit_Branch2_True_Zero() {
        // This test covers: Branch 2 = TRUE (amount <= 0)
        Account account = new Account("D3", 100.0, Account.VERIFIED);
        assertFalse(account.deposit(0));
    }
    
    @Test
    @DisplayName("deposit: Branch 2 TRUE - amount is negative")
    void deposit_Branch2_True_Negative() {
        // This test covers: Branch 2 = TRUE (amount <= 0)
        Account account = new Account("D4", 100.0, Account.VERIFIED);
        assertFalse(account.deposit(-10.0));
    }
    
    @Test
    @DisplayName("deposit: Branch 2 FALSE - amount is positive (SUCCESS PATH)")
    void deposit_Branch2_False_Positive() {
        // This test covers: Branch 2 = FALSE -> Success path
        Account account = new Account("D5", 100.0, Account.VERIFIED);
        assertTrue(account.deposit(50.0));
        assertEquals(150.0, account.getBalance());
    }
    
    // ==========================================================
    // WITHDRAW METHOD - BRANCH COVERAGE
    // 
    // Control Flow:
    //   Branch 1: status == CLOSED ? -> return false
    //   Branch 2: status == SUSPENDED ? -> return false
    //   Branch 3: amount <= 0 ? -> return false
    //   Branch 4: amount > balance ? -> return false
    //   Success: subtract from balance, return true
    // ==========================================================
    
    @Test
    @DisplayName("withdraw: Branch 1 TRUE - account is CLOSED")
    void withdraw_Branch1_True_Closed() {
        // This test covers: Branch 1 = TRUE
        Account account = new Account("W1", 100.0, Account.CLOSED);
        assertFalse(account.withdraw(50.0));
    }
    
    @Test
    @DisplayName("withdraw: Branch 2 TRUE - account is SUSPENDED")
    void withdraw_Branch2_True_Suspended() {
        // This test covers: Branch 1 = FALSE, Branch 2 = TRUE
        Account account = new Account("W2", 100.0, Account.SUSPENDED);
        assertFalse(account.withdraw(50.0));
    }
    
    @Test
    @DisplayName("withdraw: Branch 3 TRUE - amount is zero")
    void withdraw_Branch3_True_Zero() {
        // This test covers: Branch 1 = FALSE, Branch 2 = FALSE, Branch 3 = TRUE
        Account account = new Account("W3", 100.0, Account.VERIFIED);
        assertFalse(account.withdraw(0));
    }
    
    @Test
    @DisplayName("withdraw: Branch 3 TRUE - amount is negative")
    void withdraw_Branch3_True_Negative() {
        // This test covers: Branch 3 = TRUE (amount <= 0)
        Account account = new Account("W4", 100.0, Account.VERIFIED);
        assertFalse(account.withdraw(-20.0));
    }
    
    @Test
    @DisplayName("withdraw: Branch 4 TRUE - amount exceeds balance")
    void withdraw_Branch4_True_Overdraft() {
        // This test covers: Branch 4 = TRUE (amount > balance)
        Account account = new Account("W5", 100.0, Account.VERIFIED);
        assertFalse(account.withdraw(150.0));
        assertEquals(100.0, account.getBalance());
    }
    
    @Test
    @DisplayName("withdraw: All branches FALSE - SUCCESS PATH")
    void withdraw_AllFalse_Success() {
        // This test covers: All branches FALSE -> Success
        Account account = new Account("W6", 100.0, Account.VERIFIED);
        assertTrue(account.withdraw(50.0));
        assertEquals(50.0, account.getBalance());
    }
    
    // ==========================================================
    // BRANCH COVERAGE SUMMARY
    // 
    // deposit():
    //   Branch 1 TRUE: deposit_Branch1_True_Closed
    //   Branch 1 FALSE: deposit_Branch1_False_NotClosed
    //   Branch 2 TRUE: deposit_Branch2_True_Zero, deposit_Branch2_True_Negative
    //   Branch 2 FALSE: deposit_Branch2_False_Positive
    //   -> 100% branch coverage achieved by test design
    // 
    // withdraw():
    //   Branch 1 TRUE: withdraw_Branch1_True_Closed
    //   Branch 2 TRUE: withdraw_Branch2_True_Suspended
    //   Branch 3 TRUE: withdraw_Branch3_True_Zero, withdraw_Branch3_True_Negative
    //   Branch 4 TRUE: withdraw_Branch4_True_Overdraft
    //   All FALSE: withdraw_AllFalse_Success
    //   -> 100% branch coverage achieved by test design
    // ==========================================================
}
