package banking.whitebox;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import banking.Account;
import banking.TransactionProcessor;
import banking.TransactionProcessor.Result;

/**
 * White-Box Tests
 * 
 * As per project specification:
 * "Analyze TransactionProcessor code for decision paths, loops and branches"
 * 
 * This file tests INTERNAL CODE STRUCTURE using Control Flow Graphs.
 * See docs/WhiteBoxAnalysis.md for CFG diagrams.
 */
@DisplayName("White-Box Tests")
public class WhiteBoxTests {
    
    private TransactionProcessor processor;
    
    @BeforeEach
    void setUp() {
        processor = new TransactionProcessor();
    }
    
    // ==========================================================
    // TRANSACTIONPROCESSOR.DEPOSIT() - CFG BRANCHES
    // ==========================================================
    
    @Nested
    @DisplayName("TransactionProcessor.deposit() Branches")
    class DepositBranches {
        
        @Test
        @DisplayName("Branch1: account == null -> failure")
        void branch1_NullAccount() {
            Result result = processor.deposit(null, 100.0);
            assertFalse(result.isSuccess());
            assertEquals("Account is null", result.getMessage());
        }
        
        @Test
        @DisplayName("Branch2a: deposit succeeds -> success message")
        void branch2a_Success() {
            Account account = new Account("WB1", 100.0, Account.VERIFIED);
            Result result = processor.deposit(account, 50.0);
            assertTrue(result.isSuccess());
            assertEquals(150.0, account.getBalance());
        }
        
        @Test
        @DisplayName("Branch2b: deposit fails (closed) -> failure message")
        void branch2b_Failure() {
            Account account = new Account("WB2", 100.0, Account.CLOSED);
            Result result = processor.deposit(account, 50.0);
            assertFalse(result.isSuccess());
            assertEquals("Deposit failed", result.getMessage());
        }
    }
    
    // ==========================================================
    // TRANSACTIONPROCESSOR.WITHDRAW() - CFG BRANCHES
    // ==========================================================
    
    @Nested
    @DisplayName("TransactionProcessor.withdraw() Branches")
    class WithdrawBranches {
        
        @Test
        @DisplayName("Branch1: account == null -> failure")
        void branch1_NullAccount() {
            Result result = processor.withdraw(null, 50.0);
            assertFalse(result.isSuccess());
            assertEquals("Account is null", result.getMessage());
        }
        
        @Test
        @DisplayName("Branch2: amount > balance -> insufficient funds")
        void branch2_InsufficientFunds() {
            Account account = new Account("WB3", 100.0, Account.VERIFIED);
            Result result = processor.withdraw(account, 500.0);
            assertFalse(result.isSuccess());
            assertEquals("Insufficient funds", result.getMessage());
        }
        
        @Test
        @DisplayName("Branch3a: withdraw succeeds -> success message")
        void branch3a_Success() {
            Account account = new Account("WB4", 100.0, Account.VERIFIED);
            Result result = processor.withdraw(account, 50.0);
            assertTrue(result.isSuccess());
            assertEquals(50.0, account.getBalance());
        }
        
        @Test
        @DisplayName("Branch3b: withdraw fails (suspended) -> failure")
        void branch3b_Failure() {
            Account account = new Account("WB5", 100.0, Account.SUSPENDED);
            Result result = processor.withdraw(account, 50.0);
            assertFalse(result.isSuccess());
            assertEquals("Withdrawal failed", result.getMessage());
        }
    }
    
    // ==========================================================
    // TRANSACTIONPROCESSOR.TRANSFER() - CFG BRANCHES
    // ==========================================================
    
    @Nested
    @DisplayName("TransactionProcessor.transfer() Branches")
    class TransferBranches {
        
        @Test
        @DisplayName("Branch1a: from == null -> account not found")
        void branch1a_FromNull() {
            Account to = new Account("WB6", 100.0, Account.VERIFIED);
            Result result = processor.transfer(null, to, 50.0);
            assertFalse(result.isSuccess());
            assertEquals("Account not found", result.getMessage());
        }
        
        @Test
        @DisplayName("Branch1b: to == null -> account not found")
        void branch1b_ToNull() {
            Account from = new Account("WB7", 100.0, Account.VERIFIED);
            Result result = processor.transfer(from, null, 50.0);
            assertFalse(result.isSuccess());
        }
        
        @Test
        @DisplayName("Branch2a: transfer succeeds -> success message")
        void branch2a_Success() {
            Account from = new Account("WB8", 100.0, Account.VERIFIED);
            Account to = new Account("WB9", 50.0, Account.VERIFIED);
            Result result = processor.transfer(from, to, 30.0);
            assertTrue(result.isSuccess());
            assertEquals(70.0, from.getBalance());
            assertEquals(80.0, to.getBalance());
        }
        
        @Test
        @DisplayName("Branch2b: transfer fails (not verified) -> failure")
        void branch2b_Failure() {
            Account from = new Account("WB10", 100.0, Account.UNVERIFIED);
            Account to = new Account("WB11", 50.0, Account.VERIFIED);
            Result result = processor.transfer(from, to, 30.0);
            assertFalse(result.isSuccess());
            assertEquals("Transfer failed", result.getMessage());
        }
    }
    
    // ==========================================================
    // BRANCH COVERAGE SUMMARY
    // 
    // deposit():  3 branches covered (null, success, fail)
    // withdraw(): 4 branches covered (null, insufficient, success, fail)
    // transfer(): 4 branches covered (from null, to null, success, fail)
    // 
    // TOTAL: 11 tests, 100% branch coverage for TransactionProcessor
    // ==========================================================
}
