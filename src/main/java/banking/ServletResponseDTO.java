package banking;

public class ServletResponseDTO {

    public String statusMessage;
    public String errorMessage;
    public double balance;

    public boolean depositEnabled;
    public boolean withdrawEnabled;
    public boolean transferEnabled;

    public ServletResponseDTO(
            String statusMessage,
            String errorMessage,
            double balance,
            boolean depositEnabled,
            boolean withdrawEnabled,
            boolean transferEnabled) {

        this.statusMessage = statusMessage;
        this.errorMessage = errorMessage;
        this.balance = balance;
        this.depositEnabled = depositEnabled;
        this.withdrawEnabled = withdrawEnabled;
        this.transferEnabled = transferEnabled;
    }
}
