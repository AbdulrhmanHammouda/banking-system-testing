package banking.integration;

import banking.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration Tests for the Banking System.
 * 
 * Tests the complete flow:
 *   UI (Controller) -> TransactionProcessor -> Account
 */
@DisplayName("Integration Tests")
public class BankingIntegrationTests {
    
    private ClientController controller;
    private Account account;
    
    @BeforeEach
    void setUp() {
        controller = new ClientController();
        account = new Account("INT-001", 500.0, Account.VERIFIED);
        controller.selectAccount(account);
    }
    
    // ========== DEPOSIT FLOW ==========
    
    @Test
    @DisplayName("Integration: Deposit flow success")
    void depositFlow_Success() {
        controller.onDeposit(100.0);
        
        assertEquals(600.0, account.getBalance());
        assertNotNull(controller.getStatusMessage());
        assertNull(controller.getErrorMessage());
    }
    
    @Test
    @DisplayName("Integration: Deposit flow failure (invalid amount)")
    void depositFlow_Failure() {
        controller.onDeposit(-50.0);
        
        assertEquals(500.0, account.getBalance()); // unchanged
        assertNotNull(controller.getErrorMessage());
    }
    
    // ========== WITHDRAW FLOW ==========
    
    @Test
    @DisplayName("Integration: Withdraw flow success")
    void withdrawFlow_Success() {
        controller.onWithdraw(100.0);
        
        assertEquals(400.0, account.getBalance());
        assertNotNull(controller.getStatusMessage());
    }
    
    @Test
    @DisplayName("Integration: Withdraw flow failure (insufficient funds)")
    void withdrawFlow_InsufficientFunds() {
        controller.onWithdraw(1000.0);
        
        assertEquals(500.0, account.getBalance());
        assertNotNull(controller.getErrorMessage());
        assertTrue(controller.getErrorMessage().contains("Insufficient"));
    }
    
    // ========== TRANSFER FLOW ==========
    
    @Test
    @DisplayName("Integration: Transfer flow success")
    void transferFlow_Success() {
        Account target = new Account("INT-002", 100.0, Account.VERIFIED);
        
        controller.onTransfer(target, 200.0);
        
        assertEquals(300.0, account.getBalance());
        assertEquals(300.0, target.getBalance());
        assertNotNull(controller.getStatusMessage());
    }
    
    @Test
    @DisplayName("Integration: Transfer flow failure (null target)")
    void transferFlow_NullTarget() {
        controller.onTransfer(null, 100.0);
        
        assertEquals(500.0, account.getBalance()); // unchanged
        assertNotNull(controller.getErrorMessage());
    }
    
    // ========== STATE CHANGE FLOW ==========
    
    @Test
    @DisplayName("Integration: State change affects button states")
    void stateChange_AffectsButtons() {
        // Verified: all buttons enabled
        assertTrue(controller.isDepositEnabled());
        assertTrue(controller.isWithdrawEnabled());
        assertTrue(controller.isTransferEnabled());
        
        // Suspend the account
        controller.getProcessor().getAccountService().suspend(account);
        controller.selectAccount(account); // refresh
        
        // Suspended: deposit only
        assertTrue(controller.isDepositEnabled());
        assertFalse(controller.isWithdrawEnabled());
        assertFalse(controller.isTransferEnabled());
    }
    
    // ========== FULL LIFECYCLE ==========
    
    @Test
    @DisplayName("Integration: Full account lifecycle")
    void fullAccountLifecycle() {
        // Create new unverified account
        Account newAccount = new Account("LIFE-001", 0.0, Account.UNVERIFIED);
        controller.selectAccount(newAccount);
        
        // Deposit works on unverified
        controller.onDeposit(500.0);
        assertEquals(500.0, newAccount.getBalance());
        
        // Verify the account
        controller.getProcessor().getAccountService().verify(newAccount);
        controller.selectAccount(newAccount);
        
        // Now withdraw works
        controller.onWithdraw(100.0);
        assertEquals(400.0, newAccount.getBalance());
        
        // Suspend the account
        controller.getProcessor().getAccountService().suspend(newAccount);
        controller.selectAccount(newAccount);
        
        // Withdraw blocked
        controller.onWithdraw(100.0);
        assertEquals(400.0, newAccount.getBalance()); // unchanged
        
        // Close the account
        controller.getProcessor().getAccountService().close(newAccount);
        controller.selectAccount(newAccount);
        
        // All operations blocked
        assertFalse(controller.isDepositEnabled());
        assertFalse(controller.isWithdrawEnabled());
        assertFalse(controller.isTransferEnabled());
    }
}
