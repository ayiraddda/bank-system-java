package model;
public class Account {
    private String accountNumber;
    private int clientId;
    private double balance;
    private String type;
    public Account(String accountNumber, int clientId, double balance, String type) {
        this.accountNumber = accountNumber;
        this.clientId = clientId;
        this.balance = balance;
        this.type = type;
    }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public int getClientId() { return clientId; }
    public void setClientId(int clientId) { this.clientId = clientId; }
    public double getBalance() { return balance; }
    public void setBalance(double balance) { this.balance = balance; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    @Override
    public String toString() {
        return "Account{number='" + accountNumber
                + "', clientId=" + clientId
                + ", type=" + type
                + ", balance=" + String.format("%.2f", balance) + "}";
    }
}