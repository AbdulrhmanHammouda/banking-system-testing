package banking.tdd;

import banking.Client;
import banking.CreditScoreChecker;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * TDD Tests for CreditScoreChecker.
 * 
 * These tests were written FIRST (before implementation).
 * 
 * TDD Process:
 *   1. Write failing test (RED)
 *   2. Write minimum code to pass (GREEN)
 *   3. Refactor if needed
 * 
 * See docs/TDDSummary.md for TDD documentation.
 */
@DisplayName("TDD Tests - Credit Score Checker")
public class CreditScoreCheckerTest {
    
    private CreditScoreChecker checker;
    
    @BeforeEach
    void setUp() {
        checker = new CreditScoreChecker();
    }
    
    // ========== TEST 1: Eligibility Threshold ==========
    // Written FIRST - defines the core requirement
    
    @Test
    @DisplayName("TDD1: Score >= 650 is eligible")
    void score650_IsEligible() {
        assertTrue(checker.isEligible(650));
        assertTrue(checker.isEligible(700));
        assertTrue(checker.isEligible(850));
    }
    
    @Test
    @DisplayName("TDD2: Score < 650 is NOT eligible")
    void scoreBelow650_NotEligible() {
        assertFalse(checker.isEligible(649));
        assertFalse(checker.isEligible(500));
        assertFalse(checker.isEligible(300));
    }
    
    // ========== TEST 2: Boundary Values ==========
    
    @Test
    @DisplayName("TDD3: Boundary - exactly 650")
    void boundary_Exactly650() {
        assertTrue(checker.isEligible(650));
    }
    
    @Test
    @DisplayName("TDD4: Boundary - exactly 649")
    void boundary_Exactly649() {
        assertFalse(checker.isEligible(649));
    }
    
    // ========== TEST 3: Credit Tiers ==========
    
    @Test
    @DisplayName("TDD5: Tier - Excellent (750+)")
    void tier_Excellent() {
        assertEquals("Excellent", checker.getTier(750));
        assertEquals("Excellent", checker.getTier(850));
    }
    
    @Test
    @DisplayName("TDD6: Tier - Good (650-749)")
    void tier_Good() {
        assertEquals("Good", checker.getTier(650));
        assertEquals("Good", checker.getTier(749));
    }
    
    @Test
    @DisplayName("TDD7: Tier - Fair (550-649)")
    void tier_Fair() {
        assertEquals("Fair", checker.getTier(550));
        assertEquals("Fair", checker.getTier(649));
    }
    
    @Test
    @DisplayName("TDD8: Tier - Poor (<550)")
    void tier_Poor() {
        assertEquals("Poor", checker.getTier(549));
        assertEquals("Poor", checker.getTier(300));
    }
    
    // ========== TEST 4: Max Credit Limit ==========
    
    @Test
    @DisplayName("TDD9: Excellent tier gets $50,000")
    void maxCredit_Excellent() {
        assertEquals(50000, checker.getMaxCredit(750));
    }
    
    @Test
    @DisplayName("TDD10: Good tier gets $25,000")
    void maxCredit_Good() {
        assertEquals(25000, checker.getMaxCredit(650));
    }
    
    @Test
    @DisplayName("TDD11: Fair tier gets $10,000")
    void maxCredit_Fair() {
        assertEquals(10000, checker.getMaxCredit(550));
    }
    
    @Test
    @DisplayName("TDD12: Poor tier gets $0")
    void maxCredit_Poor() {
        assertEquals(0, checker.getMaxCredit(400));
    }
    
    // ========== TEST 5: Client Integration ==========
    
    @Test
    @DisplayName("TDD13: Check eligibility for Client object")
    void clientEligibility() {
        Client goodClient = new Client("C1", "John");
        goodClient.setCreditScore(700);
        assertTrue(checker.isEligible(goodClient));
        
        Client poorClient = new Client("C2", "Jane");
        poorClient.setCreditScore(500);
        assertFalse(checker.isEligible(poorClient));
    }
    
    @Test
    @DisplayName("TDD14: Null client is not eligible")
    void nullClient_NotEligible() {
        assertFalse(checker.isEligible((Client) null));
    }
}
