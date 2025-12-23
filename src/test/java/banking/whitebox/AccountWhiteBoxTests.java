package banking.whitebox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import banking.Account;

/**
 * White-Box Tests for Account class.
 * 
 * Per project spec: "Test coverage of deposit() and withdraw() paths.
 * Create a Control Flow Graph for branching logic.
 * Aim for 100% branch coverage."
 * 
 * See docs/WhiteBoxAnalysis.md for Control Flow Graphs.
 */
@DisplayName("White-Box Tests - Account")
public class AccountWhiteBoxTests {
    
    // ==========================================================
    // DEPOSIT() - CONTROL FLOW GRAPH BRANCHES
    // 
    // Code:
    //   if (status.equals("Closed") || amount <= 0) return false;
    //   balance += amount;
    //   return true;
    // 
    // Branches:
    //   Branch 1: status == "Closed" -> TRUE -> return false
    //   Branch 2: amount <= 0 -> TRUE -> return false
    //   Branch 3: Both false -> add to balance, return true
    // ==========================================================
    
    @Nested
    @DisplayName("deposit() Branches")
    class DepositBranches {
        
        @Test
        @DisplayName("Branch 1: status=Closed -> return false")
        void branch1_StatusClosed() {
            Account account = new Account("WB-D1", 100.0, Account.CLOSED);
            boolean result = account.deposit(50.0);
            assertFalse(result, "Deposit should fail when status is Closed");
            assertEquals(100.0, account.getBalance(), "Balance unchanged");
        }
        
        @Test
        @DisplayName("Branch 2a: amount=0 -> return false")
        void branch2a_AmountZero() {
            Account account = new Account("WB-D2", 100.0, Account.VERIFIED);
            boolean result = account.deposit(0);
            assertFalse(result, "Deposit should fail when amount is zero");
            assertEquals(100.0, account.getBalance());
        }
        
        @Test
        @DisplayName("Branch 2b: amount<0 -> return false")
        void branch2b_AmountNegative() {
            Account account = new Account("WB-D3", 100.0, Account.VERIFIED);
            boolean result = account.deposit(-50.0);
            assertFalse(result, "Deposit should fail when amount is negative");
            assertEquals(100.0, account.getBalance());
        }
        
        @Test
        @DisplayName("Branch 3: Valid deposit -> return true")
        void branch3_ValidDeposit() {
            Account account = new Account("WB-D4", 100.0, Account.VERIFIED);
            boolean result = account.deposit(50.0);
            assertTrue(result, "Valid deposit should succeed");
            assertEquals(150.0, account.getBalance());
        }
    }
    
    // ==========================================================
    // WITHDRAW() - CONTROL FLOW GRAPH BRANCHES
    // 
    // Code:
    //   if (status.equals("Closed") || status.equals("Suspended")) return false;
    //   if (amount <= 0) return false;
    //   if (amount > balance) return false;
    //   balance -= amount;
    //   return true;
    // 
    // Branches:
    //   Branch 1: status == "Closed" -> return false
    //   Branch 2: status == "Suspended" -> return false
    //   Branch 3: amount <= 0 -> return false
    //   Branch 4: amount > balance -> return false
    //   Branch 5: Valid -> subtract and return true
    // ==========================================================
    
    @Nested
    @DisplayName("withdraw() Branches")
    class WithdrawBranches {
        
        @Test
        @DisplayName("Branch 1: status=Closed -> return false")
        void branch1_StatusClosed() {
            Account account = new Account("WB-W1", 100.0, Account.CLOSED);
            boolean result = account.withdraw(50.0);
            assertFalse(result, "Withdraw should fail when Closed");
            assertEquals(100.0, account.getBalance());
        }
        
        @Test
        @DisplayName("Branch 2: status=Suspended -> return false")
        void branch2_StatusSuspended() {
            Account account = new Account("WB-W2", 100.0, Account.SUSPENDED);
            boolean result = account.withdraw(50.0);
            assertFalse(result, "Withdraw should fail when Suspended");
            assertEquals(100.0, account.getBalance());
        }
        
        @Test
        @DisplayName("Branch 3a: amount=0 -> return false")
        void branch3a_AmountZero() {
            Account account = new Account("WB-W3", 100.0, Account.VERIFIED);
            boolean result = account.withdraw(0);
            assertFalse(result, "Withdraw should fail for zero amount");
            assertEquals(100.0, account.getBalance());
        }
        
        @Test
        @DisplayName("Branch 3b: amount<0 -> return false")
        void branch3b_AmountNegative() {
            Account account = new Account("WB-W4", 100.0, Account.VERIFIED);
            boolean result = account.withdraw(-50.0);
            assertFalse(result, "Withdraw should fail for negative amount");
            assertEquals(100.0, account.getBalance());
        }
        
        @Test
        @DisplayName("Branch 4: amount > balance -> return false")
        void branch4_Overdraft() {
            Account account = new Account("WB-W5", 100.0, Account.VERIFIED);
            boolean result = account.withdraw(150.0);
            assertFalse(result, "Withdraw should fail for overdraft");
            assertEquals(100.0, account.getBalance());
        }
        
        @Test
        @DisplayName("Branch 5: Valid withdraw -> return true")
        void branch5_ValidWithdraw() {
            Account account = new Account("WB-W6", 100.0, Account.VERIFIED);
            boolean result = account.withdraw(50.0);
            assertTrue(result, "Valid withdraw should succeed");
            assertEquals(50.0, account.getBalance());
        }
    }
    
    // ==========================================================
    // BRANCH COVERAGE SUMMARY
    // 
    // deposit():  4 tests covering all 3 branches (100%)
    // withdraw(): 6 tests covering all 5 branches (100%)
    // 
    // TOTAL: 10 tests, 100% branch coverage for Account
    // ==========================================================
}
