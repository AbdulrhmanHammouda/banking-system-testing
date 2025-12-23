package banking;

/**
 * Credit Score Checker - Developed using TDD.
 * 
 * Rule: Score >= 650 is eligible for credit.
 * 
 * Tiers:
 *   Excellent: 750+
 *   Good: 650-749
 *   Fair: 550-649
 *   Poor: < 550
 */
public class CreditScoreChecker {
    
    public static final int THRESHOLD_ELIGIBLE = 650;
    public static final int THRESHOLD_EXCELLENT = 750;
    public static final int THRESHOLD_GOOD = 650;
    public static final int THRESHOLD_FAIR = 550;
    
    public boolean isEligible(int score) {
        return score >= THRESHOLD_ELIGIBLE;
    }
    
    public boolean isEligible(Client client) {
        if (client == null) return false;
        return isEligible(client.getCreditScore());
    }
    
    public String getTier(int score) {
        if (score >= THRESHOLD_EXCELLENT) return "Excellent";
        if (score >= THRESHOLD_GOOD) return "Good";
        if (score >= THRESHOLD_FAIR) return "Fair";
        return "Poor";
    }
    
    public double getMaxCredit(int score) {
        if (score >= THRESHOLD_EXCELLENT) return 50000;
        if (score >= THRESHOLD_GOOD) return 25000;
        if (score >= THRESHOLD_FAIR) return 10000;
        return 0;
    }
}
