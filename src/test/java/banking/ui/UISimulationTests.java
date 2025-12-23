package banking.ui;

import banking.Account;
import banking.ClientController;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * UI Simulation Tests.
 * 
 * Tests UI behavior without a real GUI - uses ClientController to simulate.
 * 
 * Tests:
 * - Button enable/disable logic
 * - Status messages
 * - Error messages
 */
@DisplayName("UI Simulation Tests")
public class UISimulationTests {
    
    private ClientController controller;
    
    @BeforeEach
    void setUp() {
        controller = new ClientController();
    }
    
    // ========== BUTTON STATE TESTS ==========
    
    @Test
    @DisplayName("UI: No account selected - all buttons disabled")
    void noAccountSelected_AllDisabled() {
        assertFalse(controller.isDepositEnabled());
        assertFalse(controller.isWithdrawEnabled());
        assertFalse(controller.isTransferEnabled());
    }
    
    @Test
    @DisplayName("UI: Verified account - all buttons enabled")
    void verifiedAccount_AllEnabled() {
        Account account = new Account("UI1", 100.0, Account.VERIFIED);
        controller.selectAccount(account);
        
        assertTrue(controller.isDepositEnabled());
        assertTrue(controller.isWithdrawEnabled());
        assertTrue(controller.isTransferEnabled());
    }
    
    @Test
    @DisplayName("UI: Unverified account - only deposit enabled")
    void unverifiedAccount_OnlyDeposit() {
        Account account = new Account("UI2", 100.0, Account.UNVERIFIED);
        controller.selectAccount(account);
        
        assertTrue(controller.isDepositEnabled());
        assertFalse(controller.isWithdrawEnabled());
        assertFalse(controller.isTransferEnabled());
    }
    
    @Test
    @DisplayName("UI: Suspended account - only deposit enabled")
    void suspendedAccount_OnlyDeposit() {
        Account account = new Account("UI3", 100.0, Account.SUSPENDED);
        controller.selectAccount(account);
        
        assertTrue(controller.isDepositEnabled());
        assertFalse(controller.isWithdrawEnabled());
        assertFalse(controller.isTransferEnabled());
    }
    
    @Test
    @DisplayName("UI: Closed account - all buttons disabled")
    void closedAccount_AllDisabled() {
        Account account = new Account("UI4", 100.0, Account.CLOSED);
        controller.selectAccount(account);
        
        assertFalse(controller.isDepositEnabled());
        assertFalse(controller.isWithdrawEnabled());
        assertFalse(controller.isTransferEnabled());
    }
    
    // ========== STATUS MESSAGE TESTS ==========
    
    @Test
    @DisplayName("UI: Successful deposit shows status message")
    void successfulDeposit_ShowsStatus() {
        Account account = new Account("UI5", 100.0, Account.VERIFIED);
        controller.selectAccount(account);
        
        controller.onDeposit(50.0);
        
        assertNotNull(controller.getStatusMessage());
        assertTrue(controller.getStatusMessage().contains("50"));
        assertNull(controller.getErrorMessage());
    }
    
    @Test
    @DisplayName("UI: Successful withdraw shows status message")
    void successfulWithdraw_ShowsStatus() {
        Account account = new Account("UI6", 100.0, Account.VERIFIED);
        controller.selectAccount(account);
        
        controller.onWithdraw(30.0);
        
        assertNotNull(controller.getStatusMessage());
        assertTrue(controller.getStatusMessage().contains("30"));
    }
    
    // ========== ERROR MESSAGE TESTS ==========
    
    @Test
    @DisplayName("UI: Failed deposit shows error message")
    void failedDeposit_ShowsError() {
        Account account = new Account("UI7", 100.0, Account.CLOSED);
        controller.selectAccount(account);
        
        controller.onDeposit(50.0);
        
        assertNotNull(controller.getErrorMessage());
        assertNull(controller.getStatusMessage());
    }
    
    @Test
    @DisplayName("UI: Insufficient funds shows error message")
    void insufficientFunds_ShowsError() {
        Account account = new Account("UI8", 100.0, Account.VERIFIED);
        controller.selectAccount(account);
        
        controller.onWithdraw(500.0);
        
        assertNotNull(controller.getErrorMessage());
        assertTrue(controller.getErrorMessage().contains("Insufficient"));
    }
    
    @Test
    @DisplayName("UI: No account selected shows error")
    void noAccount_ShowsError() {
        controller.onDeposit(50.0);
        
        assertNotNull(controller.getErrorMessage());
        assertTrue(controller.getErrorMessage().contains("No account"));
    }
    
    // ========== BUTTON STATE UPDATE TESTS ==========
    
    @Test
    @DisplayName("UI: Button state updates after status change")
    void buttonStateUpdates_AfterStatusChange() {
        Account account = new Account("UI9", 100.0, Account.VERIFIED);
        controller.selectAccount(account);
        
        assertTrue(controller.isWithdrawEnabled());
        
        // Change status to suspended
        account.setStatus(Account.SUSPENDED);
        controller.selectAccount(account); // refresh
        
        assertFalse(controller.isWithdrawEnabled());
    }
}
