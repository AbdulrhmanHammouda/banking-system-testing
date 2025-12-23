package banking.state;

import banking.Account;
import banking.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * State-Based Tests for Account status transitions.
 * 
 * State Machine:
 *   Unverified -> Verified (verify)
 *   Verified -> Suspended (suspend)
 *   Suspended -> Verified (reinstate)
 *   Any state -> Closed (close)
 * 
 * See docs/StateDiagram.md for the state diagram.
 */
@DisplayName("State-Based Tests")
public class AccountStateTests {
    
    private AccountService service;
    
    @BeforeEach
    void setUp() {
        service = new AccountService();
    }
    
    // ========== VALID TRANSITIONS ==========
    
    @Test
    @DisplayName("Valid: Unverified -> Verified")
    void validTransition_UnverifiedToVerified() {
        Account account = new Account("S1", 100.0, Account.UNVERIFIED);
        assertTrue(service.verify(account));
        assertEquals(Account.VERIFIED, account.getStatus());
    }
    
    @Test
    @DisplayName("Valid: Verified -> Suspended")
    void validTransition_VerifiedToSuspended() {
        Account account = new Account("S2", 100.0, Account.VERIFIED);
        assertTrue(service.suspend(account));
        assertEquals(Account.SUSPENDED, account.getStatus());
    }
    
    @Test
    @DisplayName("Valid: Suspended -> Verified (reinstate)")
    void validTransition_SuspendedToVerified() {
        Account account = new Account("S3", 100.0, Account.SUSPENDED);
        assertTrue(service.reinstate(account));
        assertEquals(Account.VERIFIED, account.getStatus());
    }
    
    @Test
    @DisplayName("Valid: Unverified -> Closed")
    void validTransition_UnverifiedToClosed() {
        Account account = new Account("S4", 100.0, Account.UNVERIFIED);
        assertTrue(service.close(account));
        assertEquals(Account.CLOSED, account.getStatus());
    }
    
    @Test
    @DisplayName("Valid: Verified -> Closed")
    void validTransition_VerifiedToClosed() {
        Account account = new Account("S5", 100.0, Account.VERIFIED);
        assertTrue(service.close(account));
        assertEquals(Account.CLOSED, account.getStatus());
    }
    
    @Test
    @DisplayName("Valid: Suspended -> Closed")
    void validTransition_SuspendedToClosed() {
        Account account = new Account("S6", 100.0, Account.SUSPENDED);
        assertTrue(service.close(account));
        assertEquals(Account.CLOSED, account.getStatus());
    }
    
    // ========== INVALID TRANSITIONS ==========
    
    @Test
    @DisplayName("Invalid: Verified -> Verified (already verified)")
    void invalidTransition_VerifiedToVerified() {
        Account account = new Account("I1", 100.0, Account.VERIFIED);
        assertFalse(service.verify(account));
        assertEquals(Account.VERIFIED, account.getStatus()); // unchanged
    }
    
    @Test
    @DisplayName("Invalid: Unverified -> Suspended (must verify first)")
    void invalidTransition_UnverifiedToSuspended() {
        Account account = new Account("I2", 100.0, Account.UNVERIFIED);
        assertFalse(service.suspend(account));
        assertEquals(Account.UNVERIFIED, account.getStatus());
    }
    
    @Test
    @DisplayName("Invalid: Closed -> Verified (closed is terminal)")
    void invalidTransition_ClosedToVerified() {
        Account account = new Account("I3", 100.0, Account.CLOSED);
        assertFalse(service.verify(account));
        assertEquals(Account.CLOSED, account.getStatus());
    }
    
    @Test
    @DisplayName("Invalid: Closed -> Closed (already closed)")
    void invalidTransition_ClosedToClosed() {
        Account account = new Account("I4", 100.0, Account.CLOSED);
        assertFalse(service.close(account));
        assertEquals(Account.CLOSED, account.getStatus());
    }
    
    // ========== STATE-DEPENDENT OPERATIONS ==========
    
    @Test
    @DisplayName("Operation: Deposit allowed when Verified")
    void operation_DepositAllowed_Verified() {
        Account account = new Account("O1", 100.0, Account.VERIFIED);
        assertTrue(account.deposit(50.0));
    }
    
    @Test
    @DisplayName("Operation: Deposit allowed when Suspended")
    void operation_DepositAllowed_Suspended() {
        Account account = new Account("O2", 100.0, Account.SUSPENDED);
        assertTrue(account.deposit(50.0));
    }
    
    @Test
    @DisplayName("Operation: Deposit NOT allowed when Closed")
    void operation_DepositNotAllowed_Closed() {
        Account account = new Account("O3", 100.0, Account.CLOSED);
        assertFalse(account.deposit(50.0));
    }
    
    @Test
    @DisplayName("Operation: Withdraw allowed when Verified")
    void operation_WithdrawAllowed_Verified() {
        Account account = new Account("O4", 100.0, Account.VERIFIED);
        assertTrue(account.withdraw(50.0));
    }
    
    @Test
    @DisplayName("Operation: Withdraw NOT allowed when Suspended")
    void operation_WithdrawNotAllowed_Suspended() {
        Account account = new Account("O5", 100.0, Account.SUSPENDED);
        assertFalse(account.withdraw(50.0));
    }
    
    @Test
    @DisplayName("Operation: Withdraw NOT allowed when Closed")
    void operation_WithdrawNotAllowed_Closed() {
        Account account = new Account("O6", 100.0, Account.CLOSED);
        assertFalse(account.withdraw(50.0));
    }
    
    @Test
    @DisplayName("Operation: Transfer only allowed when Verified")
    void operation_TransferOnlyVerified() {
        Account from = new Account("O7", 100.0, Account.VERIFIED);
        Account to = new Account("O8", 50.0, Account.VERIFIED);
        assertTrue(from.transfer(to, 30.0));
        
        Account unverifiedFrom = new Account("O9", 100.0, Account.UNVERIFIED);
        assertFalse(unverifiedFrom.transfer(to, 30.0));
    }
}
