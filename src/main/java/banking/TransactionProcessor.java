package banking;

/**
 * Processes transactions and returns results.
 */
public class TransactionProcessor {
    
    private final AccountService accountService;
    
    public TransactionProcessor() {
        this.accountService = new AccountService();
    }
    
    public TransactionProcessor(AccountService accountService) {
        this.accountService = accountService;
    }
    
    public Result deposit(Account account, double amount) {
        if (account == null) {
            return new Result(false, "Account is null");
        }
        boolean success = account.deposit(amount);
        if (success) {
            return new Result(true, "Deposited $" + amount);
        } else {
            return new Result(false, "Deposit failed");
        }
    }
    
    public Result withdraw(Account account, double amount) {
        if (account == null) {
            return new Result(false, "Account is null");
        }
        if (amount > account.getBalance()) {
            return new Result(false, "Insufficient funds");
        }
        boolean success = account.withdraw(amount);
        if (success) {
            return new Result(true, "Withdrew $" + amount);
        } else {
            return new Result(false, "Withdrawal failed");
        }
    }
    
    public Result transfer(Account from, Account to, double amount) {
        if (from == null || to == null) {
            return new Result(false, "Account not found");
        }
        boolean success = from.transfer(to, amount);
        if (success) {
            return new Result(true, "Transferred $" + amount);
        } else {
            return new Result(false, "Transfer failed");
        }
    }
    
    public AccountService getAccountService() {
        return accountService;
    }
    
    /**
     * Simple result class.
     */
    public static class Result {
        private final boolean success;
        private final String message;
        
        public Result(boolean success, String message) {
            this.success = success;
            this.message = message;
        }
        
        public boolean isSuccess() { return success; }
        public String getMessage() { return message; }
    }
}
