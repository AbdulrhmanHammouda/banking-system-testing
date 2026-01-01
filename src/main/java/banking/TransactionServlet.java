package banking;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.google.gson.Gson;
public class TransactionServlet extends HttpServlet {

    private ClientController controller = new ClientController(); // initialized here

    private final Account account = new Account("ACC-12345", 1000.0, Account.VERIFIED);

    @Override
    public void init() {
        controller.selectAccount(account);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String action = req.getParameter("action");
        String amountParam = req.getParameter("amount");
        double amount = 0;

        try {
            if (amountParam != null && !amountParam.isEmpty()) {
                amount = Double.parseDouble(amountParam);
            }
        } catch (NumberFormatException e) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendJsonResponse(resp, "Invalid amount");
            return;
        }

        if (action == null) {
            resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            sendJsonResponse(resp, "Action is missing");
            return;
        }

        switch (action) {
            case "deposit":
                controller.onDeposit(amount);
                break;
            case "withdraw":
                controller.onWithdraw(amount);
                break;
            case "transfer":
                Account target = new Account("ACC-99999", 500.0, Account.VERIFIED);
                controller.onTransfer(target, amount);
                break;
            default:
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                sendJsonResponse(resp, "Invalid action");
                return;
        }

        sendJsonResponse(resp, null); // success
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        sendJsonResponse(resp, null);
    }

    // modified to allow sending an error message
    private void sendJsonResponse(HttpServletResponse resp, String errorMessage) throws IOException {
        double balance = 0.0;
        boolean depositEnabled = false;
        boolean withdrawEnabled = false;
        boolean transferEnabled = false;
        
        if (controller.getCurrentAccount() != null) {
            balance = controller.getCurrentAccount().getBalance();
            depositEnabled = controller.isDepositEnabled();
            withdrawEnabled = controller.isWithdrawEnabled();
            transferEnabled = controller.isTransferEnabled();
        }
        
        ServletResponseDTO dto = new ServletResponseDTO(
                controller.getStatusMessage(),
                errorMessage != null ? errorMessage : controller.getErrorMessage(),
                balance,
                depositEnabled,
                withdrawEnabled,
                transferEnabled
        );

        resp.setContentType("application/json");
        resp.getWriter().write(new Gson().toJson(dto));
    }
}

