import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Account {
    private final Long id;
    private final String type;
    private final User owner;
    private final ArrayList<Transaction> transactionsList;
    private double balance;

    public Account(String type, User owner, Bank bank) {
        this.type = type;
        this.owner = owner;
        this.transactionsList = new ArrayList<Transaction>();
        this.id = bank.generateNewAccountId();
        this.balance = 0;
    }

    public void addTransaction(double amount, String message) {
        this.transactionsList.add(new Transaction(amount, message, this));
        this.balance += amount;
    }

    public void showTransactionHistory() {
        System.out.printf("\nTransaction history for the account with id = %d\n", this.id);
        for (Transaction transaction : transactionsList) {
            System.out.println(transaction.toString());
        }
        System.out.println();
    }

    public boolean validateBalance() {
        double transactionsBalance = 0;
        for (Transaction transaction : transactionsList) {
            transactionsBalance += transaction.getAmount();
        }
        return (this.balance == transactionsBalance);
    }

    @Override
    public String toString() {
        if (balance >= 0) {
            return String.format("%s : \u20AC%.02f : %s", this.id, balance, this.type);
        } else {
            return String.format("%s : \u20AC(%.02f) : %s", this.id, balance, this.type);
        }
    }


}
