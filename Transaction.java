package model;
public class Transaction {
    private int id;
    private String accountNumber;
    private String type;          
    private double amount;
    private String description;
    public Transaction(int id, String accountNumber, String type, double amount, String description) {
        this.id = id;
        this.accountNumber = accountNumber;
        this.type = type;
        this.amount = amount;
        this.description = description;
    }
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getAccountNumber() { return accountNumber; }
    public void setAccountNumber(String accountNumber) { this.accountNumber = accountNumber; }
    public String getType() { return type; }
    public void setType(String type) { this.type = type; }
    public double getAmount() { return amount; }
    public void setAmount(double amount) { this.amount = amount; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    @Override
    public String toString() {
        return "Transaction{id=" + id
                + ", account='" + accountNumber + "'"
                + ", type=" + type
                + ", amount=" + String.format("%.2f", amount)
                + ", description='" + description + "'}";
    }
}