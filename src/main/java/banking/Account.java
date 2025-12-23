package banking;

/**
 * Represents a bank account with balance and status management.
 */
public class Account {
    
    // Status constantsD
    public static final String UNVERIFIED = "Unverified";
    public static final String VERIFIED = "Verified";
    public static final String SUSPENDED = "Suspended";
    public static final String CLOSED = "Closed";
    
    private final String accountId;
    private double balance;
    private String status;
    
    public Account(String accountId, double initialBalance) {
        this.accountId = accountId;
        this.balance = initialBalance;
        this.status = UNVERIFIED;
    }
    
    public Account(String accountId, double initialBalance, String status) {
        this.accountId = accountId;
        this.balance = initialBalance;
        this.status = status;
    }
    
    /**
     * Deposits money into the account.
     * 
     * Control Flow:
     *   1. Check if CLOSED -> return false
     *   2. Check if amount <= 0 -> return false
     *   3. Add to balance -> return true
     */
    public boolean deposit(double amount) {
        // Branch 1: Status check
        if (status.equals(CLOSED)) {
            return false;
        }
        // Branch 2: Amount validation
        if (amount <= 0) {
            return false;
        }
        // Success path
        balance += amount;
        return true;
    }
    
    /**
     * Withdraws money from the account.
     * 
     * Control Flow:
     *   1. Check if CLOSED -> return false
     *   2. Check if SUSPENDED -> return false
     *   3. Check if amount <= 0 -> return false
     *   4. Check if amount > balance -> return false
     *   5. Subtract from balance -> return true
     */
    public boolean withdraw(double amount) {
        // Branch 1: Closed check
        if (status.equals(CLOSED)) {
            return false;
        }
        // Branch 2: Suspended check
        if (status.equals(SUSPENDED)) {
            return false;
        }
        // Branch 3: Amount validation
        if (amount <= 0) {
            return false;
        }
        // Branch 4: Balance check
        if (amount > balance) {
            return false;
        }
        // Success path
        balance -= amount;
        return true;
    }
    
    /**
     * Transfers money to another account.
     */
    public boolean transfer(Account target, double amount) {
        if (target == null) {
            return false;
        }
        if (!status.equals(VERIFIED)) {
            return false;
        }
        if (amount <= 0 || amount > balance) {
            return false;
        }
        if (target.status.equals(CLOSED)) {
            return false;
        }
        balance -= amount;
        target.balance += amount;
        return true;
    }
    
    // Getters and setters
    public String getAccountId() { return accountId; }
    public double getBalance() { return balance; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
