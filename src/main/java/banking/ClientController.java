package banking;

/**
 * Simulates UI controller - handles user actions.
 */
public class ClientController {
    
    private final TransactionProcessor processor;
    private Account currentAccount;
    private String statusMessage;
    private String errorMessage;
    
    // Button states
    private boolean depositEnabled;
    private boolean withdrawEnabled;
    private boolean transferEnabled;
    
    public ClientController() {
        this.processor = new TransactionProcessor();
    }
    
    public ClientController(TransactionProcessor processor) {
        this.processor = processor;
    }
    
    public void selectAccount(Account account) {
        this.currentAccount = account;
        updateButtonStates();
    }
    
    public void onDeposit(double amount) {
        if (currentAccount == null) {
            errorMessage = "No account selected";
            return;
        }
        TransactionProcessor.Result result = processor.deposit(currentAccount, amount);
        if (result.isSuccess()) {
            statusMessage = result.getMessage();
            errorMessage = null;
        } else {
            errorMessage = result.getMessage();
            statusMessage = null;
        }
        updateButtonStates();
    }
    
    public void onWithdraw(double amount) {
        if (currentAccount == null) {
            errorMessage = "No account selected";
            return;
        }
        TransactionProcessor.Result result = processor.withdraw(currentAccount, amount);
        if (result.isSuccess()) {
            statusMessage = result.getMessage();
            errorMessage = null;
        } else {
            errorMessage = result.getMessage();
            statusMessage = null;
        }
        updateButtonStates();
    }
    
    public void onTransfer(Account target, double amount) {
        if (currentAccount == null) {
            errorMessage = "No account selected";
            return;
        }
        TransactionProcessor.Result result = processor.transfer(currentAccount, target, amount);
        if (result.isSuccess()) {
            statusMessage = result.getMessage();
            errorMessage = null;
        } else {
            errorMessage = result.getMessage();
            statusMessage = null;
        }
        updateButtonStates();
    }
    
    private void updateButtonStates() {
        if (currentAccount == null) {
            depositEnabled = false;
            withdrawEnabled = false;
            transferEnabled = false;
            return;
        }
        
        String status = currentAccount.getStatus();
        switch (status) {
            case Account.VERIFIED:
                depositEnabled = true;
                withdrawEnabled = true;
                transferEnabled = true;
                break;
            case Account.UNVERIFIED:
            case Account.SUSPENDED:
                depositEnabled = true;
                withdrawEnabled = false;
                transferEnabled = false;
                break;
            case Account.CLOSED:
                depositEnabled = false;
                withdrawEnabled = false;
                transferEnabled = false;
                break;
        }
    }
    
    // Getters for UI state
    public boolean isDepositEnabled() { return depositEnabled; }
    public boolean isWithdrawEnabled() { return withdrawEnabled; }
    public boolean isTransferEnabled() { return transferEnabled; }
    public String getStatusMessage() { return statusMessage; }
    public String getErrorMessage() { return errorMessage; }
    public Account getCurrentAccount() { return currentAccount; }
    public TransactionProcessor getProcessor() { return processor; }
}
