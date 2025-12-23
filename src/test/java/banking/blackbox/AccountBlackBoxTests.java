package banking.blackbox;

import banking.Account;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Black-Box Tests for Account class.
 * 
 * Testing is based on SPECIFICATIONS only, not internal implementation.
 * 
 * Techniques used:
 * - Equivalence Partitioning (EP)
 * - Boundary Value Analysis (BVA)
 */
@DisplayName("Black-Box Tests")
public class AccountBlackBoxTests {
    
    // ========== DEPOSIT - Equivalence Partitioning ==========
    
    @Test
    @DisplayName("EP1: Deposit negative amount -> rejected")
    void depositNegativeAmount() {
        Account account = new Account("A1", 100.0, Account.VERIFIED);
        assertFalse(account.deposit(-50.0));
        assertEquals(100.0, account.getBalance());
    }
    
    @Test
    @DisplayName("EP2: Deposit zero amount -> rejected")
    void depositZeroAmount() {
        Account account = new Account("A2", 100.0, Account.VERIFIED);
        assertFalse(account.deposit(0));
        assertEquals(100.0, account.getBalance());
    }
    
    @Test
    @DisplayName("EP3: Deposit positive amount -> accepted")
    void depositPositiveAmount() {
        Account account = new Account("A3", 100.0, Account.VERIFIED);
        assertTrue(account.deposit(50.0));
        assertEquals(150.0, account.getBalance());
    }
    
    // ========== DEPOSIT - Boundary Value Analysis ==========
    
    @Test
    @DisplayName("BVA1: Deposit -0.01 (just below zero) -> rejected")
    void depositJustBelowZero() {
        Account account = new Account("B1", 100.0, Account.VERIFIED);
        assertFalse(account.deposit(-0.01));
    }
    
    @Test
    @DisplayName("BVA2: Deposit 0.00 (at zero) -> rejected")
    void depositAtZero() {
        Account account = new Account("B2", 100.0, Account.VERIFIED);
        assertFalse(account.deposit(0.00));
    }
    
    @Test
    @DisplayName("BVA3: Deposit 0.01 (just above zero) -> accepted")
    void depositJustAboveZero() {
        Account account = new Account("B3", 100.0, Account.VERIFIED);
        assertTrue(account.deposit(0.01));
        assertEquals(100.01, account.getBalance(), 0.001);
    }
    
    // ========== WITHDRAW - Equivalence Partitioning ==========
    
    @Test
    @DisplayName("EP4: Withdraw negative amount -> rejected")
    void withdrawNegativeAmount() {
        Account account = new Account("W1", 100.0, Account.VERIFIED);
        assertFalse(account.withdraw(-50.0));
    }
    
    @Test
    @DisplayName("EP5: Withdraw zero amount -> rejected")
    void withdrawZeroAmount() {
        Account account = new Account("W2", 100.0, Account.VERIFIED);
        assertFalse(account.withdraw(0));
    }
    
    @Test
    @DisplayName("EP6: Withdraw valid amount (within balance) -> accepted")
    void withdrawValidAmount() {
        Account account = new Account("W3", 100.0, Account.VERIFIED);
        assertTrue(account.withdraw(50.0));
        assertEquals(50.0, account.getBalance());
    }
    
    @Test
    @DisplayName("EP7: Withdraw more than balance -> rejected")
    void withdrawMoreThanBalance() {
        Account account = new Account("W4", 100.0, Account.VERIFIED);
        assertFalse(account.withdraw(150.0));
        assertEquals(100.0, account.getBalance());
    }
    
    // ========== WITHDRAW - Boundary Value Analysis ==========
    
    @Test
    @DisplayName("BVA4: Withdraw exactly balance -> accepted")
    void withdrawExactBalance() {
        Account account = new Account("WB1", 100.0, Account.VERIFIED);
        assertTrue(account.withdraw(100.0));
        assertEquals(0.0, account.getBalance());
    }
    
    @Test
    @DisplayName("BVA5: Withdraw balance + 0.01 -> rejected")
    void withdrawJustOverBalance() {
        Account account = new Account("WB2", 100.0, Account.VERIFIED);
        assertFalse(account.withdraw(100.01));
        assertEquals(100.0, account.getBalance());
    }
    
    @Test
    @DisplayName("BVA6: Withdraw balance - 0.01 -> accepted")
    void withdrawJustUnderBalance() {
        Account account = new Account("WB3", 100.0, Account.VERIFIED);
        assertTrue(account.withdraw(99.99));
        assertEquals(0.01, account.getBalance(), 0.001);
    }
    
    // ========== TRANSFER - Equivalence Partitioning ==========
    
    @Test
    @DisplayName("EP8: Transfer valid amount between verified accounts -> accepted")
    void transferValidAmount() {
        Account from = new Account("T1", 100.0, Account.VERIFIED);
        Account to = new Account("T2", 50.0, Account.VERIFIED);
        assertTrue(from.transfer(to, 30.0));
        assertEquals(70.0, from.getBalance());
        assertEquals(80.0, to.getBalance());
    }
    
    @Test
    @DisplayName("EP9: Transfer more than balance -> rejected")
    void transferMoreThanBalance() {
        Account from = new Account("T3", 100.0, Account.VERIFIED);
        Account to = new Account("T4", 50.0, Account.VERIFIED);
        assertFalse(from.transfer(to, 150.0));
    }
    
    @Test
    @DisplayName("EP10: Transfer from unverified account -> rejected")
    void transferFromUnverified() {
        Account from = new Account("T5", 100.0, Account.UNVERIFIED);
        Account to = new Account("T6", 50.0, Account.VERIFIED);
        assertFalse(from.transfer(to, 30.0));
    }
}
