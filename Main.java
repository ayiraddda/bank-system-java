package main;
import model.Account;
import model.Client;
import model.Transaction;
import service.BankService;
import java.util.List;
import java.util.Scanner;
public class Main {
    private static final BankService service = new BankService();
    private static final Scanner scanner = new Scanner(System.in);
    public static void main(String[] args) {
        System.out.println("Bank System");
        while (true) {
            printMenu();
            String choice = scanner.nextLine().trim();
            System.out.println();
            switch (choice) {
                case "1": addClient();          break;
                case "2": openAccount();        break;
                case "3": deposit();            break;
                case "4": withdraw();           break;
                case "5": transfer();           break;
                case "6": showBalance();        break;
                case "7": showTransactions();   break;
                case "8": analyticsMenu();      break;
                case "0":
                    System.out.println("Goodbye!");
                    return;
                default:
                    System.out.println("Unknown option. Enter a number from the menu");
            }
            System.out.println();
        }
    }
    private static void printMenu() {
        System.out.println(" 1. Add client");
        System.out.println(" 2. Open account");
        System.out.println(" 3. Deposit");
        System.out.println(" 4. Withdraw");
        System.out.println(" 5. Transfer");
        System.out.println(" 6. Show balance");
        System.out.println(" 7. Transaction history");
        System.out.println(" 8. Analytics");
        System.out.println(" 0. Exit");
        System.out.print("Choose: ");
    }
    private static void addClient() {
        int id = readInt("Client ID: ");
        if (id < 0) return;
        System.out.print("Full name: ");
        String fullName = scanner.nextLine().trim();
        if (fullName.isEmpty()) { System.out.println("Error: name cannot be empty"); return; }
        System.out.print("Phone: ");
        String phone = scanner.nextLine().trim();
        if (phone.isEmpty()) { System.out.println("Error: phone cannot be empty"); return; }
        Client client = new Client(id, fullName, phone);
        if (service.addClient(client)) {
            System.out.println("Added: " + client);
        }
    }
    private static void openAccount() {
        int clientId = readInt("Client ID: ");
        if (clientId < 0) return;
        System.out.println("Type: 1=DEBIT  2=CREDIT");
        System.out.print("Choose: ");
        String typeChoice = scanner.nextLine().trim();
        String type;
        if (typeChoice.equals("1")) {
            type = "DEBIT";
        } else if (typeChoice.equals("2")) {
            type = "CREDIT";
        } else {
            System.out.println("Error: invalid type. Enter 1 or 2");
            return;
        }
        service.createAccount(clientId, type);
    }
    private static void deposit() {
        System.out.print("Account number: ");
        String accountNumber = scanner.nextLine().trim();
        double amount = readDouble("Amount: ");
        if (amount < 0) return;
        if (service.deposit(accountNumber, amount)) {
            double balance = service.getBalance(accountNumber);
            System.out.printf("Success. New balance: %.2f%n", balance);
        }
    }
    private static void withdraw() {
        System.out.print("Account number: ");
        String accountNumber = scanner.nextLine().trim();
        double amount = readDouble("Amount: ");
        if (amount < 0) return;

        if (service.withdraw(accountNumber, amount)) {
            double balance = service.getBalance(accountNumber);
            System.out.printf("Success. New balance: %.2f%n", balance);
        }
    }
    private static void transfer() {
        System.out.print("From account: ");
        String fromAccount = scanner.nextLine().trim();
        System.out.print("To account: ");
        String toAccount = scanner.nextLine().trim();
        double amount = readDouble("Amount: ");
        if (amount < 0) return;
        if (service.transfer(fromAccount, toAccount, amount)) {
            System.out.printf("Transferred %.2f from %s to %s.%n", amount, fromAccount, toAccount);
        }
    }
    private static void showBalance() {
        System.out.print("Account number: ");
        String accountNumber = scanner.nextLine().trim();
        double balance = service.getBalance(accountNumber);
        if (balance >= 0) {
            Account account = service.findAccountByNumber(accountNumber);
            System.out.printf("Balance of %s [%s]: %.2f%n",
                    accountNumber, account.getType(), balance);
        }
    }
    private static void showTransactions() {
        System.out.print("Account number: ");
        String accountNumber = scanner.nextLine().trim();
        List<Transaction> history = service.getTransactionHistory(accountNumber);
        if (history.isEmpty()) {
            System.out.println("No transactions found for " + accountNumber);
        } else {
            System.out.println("Transaction history for " + accountNumber
                    + " (" + history.size() + " records):");
            for (Transaction t : history) {
                System.out.println("  " + t);
            }
        }
    }
    private static void analyticsMenu() {
        while (true) {
            System.out.println("Analytics");
            System.out.println(" 1. Total balance of client");
            System.out.println(" 2. Clients above balance threshold");
            System.out.println(" 3. Richest client");
            System.out.println(" 0. Back");
            System.out.print("Choose: ");
            String choice = scanner.nextLine().trim();
            System.out.println();
            switch (choice) {
                case "1": showClientTotalBalance();    break;
                case "2": showClientsAboveBalance();   break;
                case "3": showRichestClient();         break;
                case "0": return;
                default: System.out.println("Unknown option.");
            }
            System.out.println();
        }
    }
    private static void showClientTotalBalance() {
        int clientId = readInt("Client ID: ");
        if (clientId < 0) return;
        Client client = service.findClientById(clientId);
        if (client == null) {
            System.out.println("Error: client with ID " + clientId + " not found");
            return;
        }
        double total = service.getClientTotalBalance(clientId);
        List<Account> accounts = service.getAccountsByClient(clientId);
        System.out.printf("Total balance of %s: %.2f (%d account(s))%n",
                client.getFullName(), total, accounts.size());
        for (Account a : accounts) {
            System.out.println("  " + a);
        }
    }
    private static void showClientsAboveBalance() {
        double threshold = readDouble("Balance threshold: ");
        if (threshold < 0) return;
        List<Client> clients = service.getClientsAboveBalance(threshold);
        if (clients.isEmpty()) {
            System.out.printf("No clients with total balance above %.2f.%n", threshold);
        } else {
            System.out.printf("Clients with total balance above %.2f (%d found):%n",
                    threshold, clients.size());
            for (Client c : clients) {
                double balance = service.getClientTotalBalance(c.getId());
                System.out.printf("  %-25s → %.2f%n", c.getFullName(), balance);
            }
        }
    }
    private static void showRichestClient() {
        Client richest = service.getRichestClient();
        if (richest == null) {
            System.out.println("No clients found");
        } else {
            double balance = service.getClientTotalBalance(richest.getId());
            System.out.printf("Richest client: %s (total balance: %.2f)%n",
                    richest.getFullName(), balance);
        }
    }
    private static int readInt(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        try {
            return Integer.parseInt(line);
        } catch (NumberFormatException e) {
            System.out.println("Error: please enter a valid integer");
            return -1;
        }
    }
    private static double readDouble(String prompt) {
        System.out.print(prompt);
        String line = scanner.nextLine().trim();
        try {
            return Double.parseDouble(line);
        } catch (NumberFormatException e) {
            System.out.println("Error: please enter a valid number");
            return -1;
        }
    }
}