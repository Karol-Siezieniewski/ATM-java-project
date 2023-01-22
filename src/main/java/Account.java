import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Account {
    private final Long id;
    private final String type;
    private final User owner;
    private final ArrayList<Transaction> transactionsList;
    private double balance;

    /**
     *  public constructor for Account class, it ensures that empty list of transactions is generated and it calls
     *  bank's method to generate new unique account id
     * @param type  provided type/name of the account
     * @param owner provided user object that account belongs to
     * @param bank  provided bank object that account belongs to
     */
    public Account(String type, User owner, Bank bank) {
        this.type = type;
        this.owner = owner;
        this.transactionsList = new ArrayList<>();
        this.id = bank.generateNewAccountId();
        this.balance = 0;
    }

    /**
     *  This method adds a new transaction object to the account's transaction list and also changes the current
     *  balance of the account
     * @param amount provided amount of money for transaction
     * @param message provided message/notes for transaction
     */
    public void addTransaction(double amount, String message) {
        this.transactionsList.add(new Transaction(amount, message, this));
        this.balance += amount;
    }

    /**
     * This method displays all of the transactions from this object transaction's list
     */
    public void showTransactionHistory() {
        System.out.printf("\nTransaction history for the account with id = %d\n", this.id);
        for (Transaction transaction : transactionsList) {
            System.out.println(transaction.toString());
        }
        System.out.println();
    }

    /**
     * This method verifies whether the given account balance equals with the value calculated from all the
     * transactions assigned to the account
     * @return whether the validation was positive or negative
     */
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
