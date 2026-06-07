package service;
import model.Account;
import model.Client;
import model.Transaction;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
public class BankService {
    private final ArrayList<Client> clientList           = new ArrayList<>();
    private final ArrayList<Account> accountList         = new ArrayList<>();
    private final ArrayList<Transaction> transactionList = new ArrayList<>();
    private final HashMap<Integer, Client> clientMap   = new HashMap<>();
    private final HashMap<String, Account> accountMap  = new HashMap<>();
    private int nextTransactionId = 1;
    private int nextAccountSuffix = 1001;
    public boolean addClient(Client client) {
        if (clientMap.containsKey(client.getId())) {
            System.out.println("Error: client with ID " + client.getId() + " already exists");
            return false;
        }
        clientList.add(client);
        clientMap.put(client.getId(), client);
        return true;
    }
    public boolean createAccount(int clientId, String type) {
        if (!clientMap.containsKey(clientId)) {
            System.out.println("Error: client with ID " + clientId + " not found");
            return false;
        }
        if (!type.equals("DEBIT") && !type.equals("CREDIT")) {
            System.out.println("Error: account type must be DEBIT or CREDIT");
            return false;
        }
        String accountNumber = "ACC-" + nextAccountSuffix++;
        Account account = new Account(accountNumber, clientId, 0.0, type);
        accountList.add(account);
        accountMap.put(accountNumber, account);
        System.out.println("Account created: " + account);
        return true;
    }
    public boolean deposit(String accountNumber, double amount) {
        Account account = accountMap.get(accountNumber);
        if (account == null) {
            System.out.println("Error: account " + accountNumber + " not found");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Error: deposit amount must be greater than zero");
            return false;
        }
        account.setBalance(account.getBalance() + amount);
        recordTransaction(accountNumber, "DEPOSIT", amount, "Deposit to " + accountNumber);
        return true;
    }
    public boolean withdraw(String accountNumber, double amount) {
        Account account = accountMap.get(accountNumber);
        if (account == null) {
            System.out.println("Error: account " + accountNumber + " not found");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Error: withdrawal amount must be greater than zero");
            return false;
        }
        if (account.getBalance() < amount) {
            System.out.println("Error: insufficient balance. Available: "
                    + String.format("%.2f", account.getBalance()));
            return false;
        }
        account.setBalance(account.getBalance() - amount);
        recordTransaction(accountNumber, "WITHDRAW", amount, "Withdrawal from " + accountNumber);
        return true;
    }
    public boolean transfer(String fromAccountNumber, String toAccountNumber, double amount) {
        Account fromAccount = accountMap.get(fromAccountNumber);
        if (fromAccount == null) {
            System.out.println("Error: source account " + fromAccountNumber + " not found");
            return false;
        }
        Account toAccount = accountMap.get(toAccountNumber);
        if (toAccount == null) {
            System.out.println("Error: destination account " + toAccountNumber + " not found");
            return false;
        }
        if (fromAccountNumber.equals(toAccountNumber)) {
            System.out.println("Error: source and destination accounts must be different");
            return false;
        }
        if (amount <= 0) {
            System.out.println("Error: transfer amount must be greater than zero");
            return false;
        }
        if (fromAccount.getBalance() < amount) {
            System.out.println("Error: insufficient balance. Available: "
                    + String.format("%.2f", fromAccount.getBalance()));
            return false;
        }
        fromAccount.setBalance(fromAccount.getBalance() - amount);
        toAccount.setBalance(toAccount.getBalance() + amount);
        recordTransaction(fromAccountNumber, "TRANSFER", amount,
                "Transfer to " + toAccountNumber);
        recordTransaction(toAccountNumber, "TRANSFER", amount,
                "Transfer from " + fromAccountNumber);
        return true;
    }
    public double getBalance(String accountNumber) {
        Account account = accountMap.get(accountNumber);
        if (account == null) {
            System.out.println("Error: account " + accountNumber + " not found");
            return -1;
        }
        return account.getBalance();
    }
    public List<Transaction> getTransactionHistory(String accountNumber) {
        if (!accountMap.containsKey(accountNumber)) {
            System.out.println("Error: account " + accountNumber + " not found");
            return new ArrayList<>();
        }
        List<Transaction> result = new ArrayList<>();
        for (Transaction t : transactionList) {
            if (t.getAccountNumber().equals(accountNumber)) {
                result.add(t);
            }
        }
        return result;
    }
    public double getClientTotalBalance(int clientId) {
        if (!clientMap.containsKey(clientId)) {
            System.out.println("Error: client with ID " + clientId + " not found");
            return -1;
        }
        double total = 0;
        for (Account a : accountList) {
            if (a.getClientId() == clientId) {
                total += a.getBalance();
            }
        }
        return total;
    }
    public List<Client> getClientsAboveBalance(double threshold) {
        List<Client> result = new ArrayList<>();
        for (Client c : clientList) {
            if (getClientTotalBalance(c.getId()) > threshold) {
                result.add(c);
            }
        }
        return result;
    }
    public Client getRichestClient() {
        Client richest = null;
        double maxBalance = -1;
        for (Client c : clientList) {
            double balance = getClientTotalBalance(c.getId());
            if (balance > maxBalance) {
                maxBalance = balance;
                richest = c;
            }
        }
        return richest;
    }
    public Client findClientById(int id) {
        return clientMap.get(id);
    }
    public Account findAccountByNumber(String accountNumber) {
        return accountMap.get(accountNumber);
    }
    public List<Account> getAccountsByClient(int clientId) {
        List<Account> result = new ArrayList<>();
        for (Account a : accountList) {
            if (a.getClientId() == clientId) {
                result.add(a);
            }
        }
        return result;
    }
    private void recordTransaction(String accountNumber, String type, double amount, String description) {
        transactionList.add(new Transaction(nextTransactionId++, accountNumber, type, amount, description));
    }
}