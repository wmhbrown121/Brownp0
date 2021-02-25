package revature.brown.entities;

public class Account{

    private int accountId;
    private double accountBalance;
    private int client_Id;

    public Account() {}

    public Account(int accountId, double accountBalance, int clientId) {
        this.accountId = accountId;
        this.accountBalance = accountBalance;
        this.client_Id = clientId;
    }

    public int getAccountId() {
        return accountId;
    }

    public void setAccountId(int accountId) {
        this.accountId = accountId;
    }

    public double getAccountBalance() {
        return accountBalance;
    }

    public void setAccountBalance(double accountBalance) {
        this.accountBalance = accountBalance;
    }

    public int getClient_Id() {
        return client_Id;
    }

    public void setClient_Id(int clientId) {
        this.client_Id = clientId;
    }

    @Override
    public String toString() {
        return "Account{" +
                "accountId=" + accountId +
                ", accountBalance=" + accountBalance +
                ", client_Id=" + client_Id +
                '}';
    }
}
