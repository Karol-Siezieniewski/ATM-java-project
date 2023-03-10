import java.util.Scanner;

public class BankController {

    /**
     * This method displays summary for all the accounts of provided user instance
     * @param user provided user instance
     */
    public static void displayAccountInformation(User user) {
        user.displayAccountsInfo();
    }

    /**
     * This method calls a display of transaction history for a given account which is determined by user's choice
     * @param user  provided user instance, who's accounts are eligible for display
     * @param scanner provided scanner instance for use in whichAccount()
     */
    public static void displayAccountTransactionHistory(User user, Scanner scanner) {
        int accountIndex = whichAccount(user, scanner);

        user.getAccountsList()
                .get(accountIndex)
                .showTransactionHistory();
    }

    /**
     * This method allows to deposit a certain amount (greater than zero) and then adds the corresponding transaction
     * to the adequate account
     * @param user provided user instance, who's accounts are eligible for deposit
     * @param scanner provided scanner instance
     */
    public static void deposit(User user, Scanner scanner) {
        int accountIndex = whichAccount(user, scanner);
        double amount;
        String message;
        do {
            System.out.println("Enter the amount to deposit on your account: ");
            System.out.println("(minimal value of (0.01\u20AC)");
            amount = scanner.nextDouble();
            if (amount <= 0) {
                System.out.println("Invalid amount! Amount must be greater than 0!");
            }
        } while (amount <= 0);
        scanner.nextLine();
        System.out.println("Please provide transaction message/title: ");
        message = scanner.nextLine();

        user.getAccountsList()
                .get(accountIndex)
                .addTransaction(amount, message);
    }

    /**
     * This method allows to withdraw a certain amount (greater than zero and smaller than current balance of the account)
     * and then adds the corresponding transaction to the adequate account
     * @param user provided user instance, who's accounts are eligible for withdraw
     * @param scanner provided scanner instance
     */
    public static void withdraw(User user, Scanner scanner) {
        int accountIndex = whichAccount(user, scanner);
        final double MAX_AMOUNT = user.getAccountsList().get(accountIndex).getBalance();
        double amount;
        String message;
        boolean valid;
        do {
            System.out.println("Enter the amount to withdraw from your account: ");
            System.out.printf("(maximum value of %.02f\u20AC possible)\n", MAX_AMOUNT);
            amount = scanner.nextDouble();
            if (amount > MAX_AMOUNT) {
                System.out.printf("Insufficient funds! Entered value is greater than the current account balance of %.02f\n", MAX_AMOUNT);
                valid = false;
                continue;
            } else if (amount < 0) {
                System.out.println("Entered value must be greater than 0!");
                valid = false;
                continue;
            }
            valid = true;
        } while (!valid);

        scanner.nextLine();
        System.out.println("Please provide transaction message/title: ");
        message = scanner.nextLine();

        user.getAccountsList()
                .get(accountIndex)
                .addTransaction(-1 * amount, message);
    }

    /**
     * This method allows to transfer a certain amount (greater than zero and smaller than current balance of the
     * selected account) between two accounts of the same user and then adds the corresponding transactions
     * to the adequate accounts
     * @param user provided user instance, who's accounts are eligible for transfer
     * @param scanner provided scanner instance
     */
    public static void transfer(User user, Scanner scanner) {
        int fromAccountIndex, toAccountIndex;
        double amount;
        boolean valid = false;
        do {
            System.out.println("Please provide which account you want to transfer from: ");
            fromAccountIndex = whichAccount(user, scanner);
            System.out.println("Please provide which account you want to transfer to: ");
            toAccountIndex = whichAccount(user, scanner);
            if (fromAccountIndex == toAccountIndex) {
                System.out.println("Invalid account indexes, you can't transfer funds between only one account");
                continue;
            }
            valid = true;
        } while (!valid);

        valid = false;
        final double MAX_AMOUNT = user.getAccountsList().get(fromAccountIndex).getBalance();
        do {
            System.out.println("Enter the amount to transfer from your account: ");
            System.out.printf("(maximum value of %.02f\u20AC possible)\n", MAX_AMOUNT);
            amount = scanner.nextDouble();
            if (amount > MAX_AMOUNT) {
                System.out.printf("Insufficient funds! Entered value is greater than the current account balance of %.02f\n", MAX_AMOUNT);
                valid = false;
                continue;
            } else if (amount < 0) {
                System.out.println("Entered value must be greater than 0!");
                valid = false;
                continue;
            }
            valid = true;
        } while (!valid);

        user.getAccountsList()
                .get(fromAccountIndex)
                .addTransaction(-1 * amount, String.format("Transferred from this account to account %s", user.getAccountsList().get(toAccountIndex).getType()));
        user.getAccountsList()
                .get(toAccountIndex)
                .addTransaction(amount, String.format("Transferred from account %s to this account", user.getAccountsList().get(fromAccountIndex).getType()));
    }

    /**
     * This method allows you to select the account index to which the operation is applied
     * @param user provided user instance, who's accounts are eligible for choosing
     * @param scanner provided scanner instance
     * @return account's index which was chosen by the user to apply a certain operation
     */
    public static int whichAccount(User user, Scanner scanner) {
        int accountIndex;
        boolean valid = false;
        do {
            System.out.printf("Which account you want to use? (possible options 1-%d)\n", user.getAccountsList().size());
            accountIndex = scanner.nextInt() - 1;
            if (accountIndex < 0 || accountIndex >= user.getAccountsList().size()) {
                System.out.println("Invalid account index, please try again");
                continue;
            }
            valid = true;
        } while (!valid);
        return accountIndex;
    }
}
