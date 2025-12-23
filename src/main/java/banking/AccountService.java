package banking;

/**
 * Manages account state transitions.
 * 
 * State Machine:
 *   Unverified -> Verified (verify)
 *   Verified -> Suspended (suspend)
 *   Suspended -> Verified (reinstate)
 *   Any -> Closed (close)
 */
public class AccountService {
    
    public boolean verify(Account account) {
        if (account == null) return false;
        if (!account.getStatus().equals(Account.UNVERIFIED)) return false;
        account.setStatus(Account.VERIFIED);
        return true;
    }
    
    public boolean suspend(Account account) {
        if (account == null) return false;
        if (!account.getStatus().equals(Account.VERIFIED)) return false;
        account.setStatus(Account.SUSPENDED);
        return true;
    }
    
    public boolean reinstate(Account account) {
        if (account == null) return false;
        if (!account.getStatus().equals(Account.SUSPENDED)) return false;
        account.setStatus(Account.VERIFIED);
        return true;
    }
    
    public boolean close(Account account) {
        if (account == null) return false;
        if (account.getStatus().equals(Account.CLOSED)) return false;
        account.setStatus(Account.CLOSED);
        return true;
    }
}
