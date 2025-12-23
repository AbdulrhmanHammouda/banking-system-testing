package banking;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents a bank client.
 */
public class Client {
    
    private final String clientId;
    private final String name;
    private final List<Account> accounts;
    private int creditScore;
    
    public Client(String clientId, String name) {
        this.clientId = clientId;
        this.name = name;
        this.accounts = new ArrayList<>();
        this.creditScore = 0;
    }
    
    public void addAccount(Account account) {
        accounts.add(account);
    }
    
    public Account getAccount(String accountId) {
        for (Account account : accounts) {
            if (account.getAccountId().equals(accountId)) {
                return account;
            }
        }
        return null;
    }
    
    // Getters
    public String getClientId() { return clientId; }
    public String getName() { return name; }
    public List<Account> getAccounts() { return accounts; }
    public int getCreditScore() { return creditScore; }
    public void setCreditScore(int creditScore) { this.creditScore = creditScore; }
}
