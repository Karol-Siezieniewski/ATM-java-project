import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        Bank bank = new Bank("PKO");
        User user = bank.createUser("Bob", "Builder", "Test123");
        Account account = new Account("Checking", user, bank);
        user.addAccount(account);
        bank.addAccount(account);

        User currentUser = displayLoginInterface(bank, scanner);

        displayUserInterface(currentUser, scanner);

    }

    public static User displayLoginInterface(Bank bank, Scanner scanner) {
        long userId;
        String password, userIdTemp;
        User authenticatedUser;

        do {
            System.out.printf("\nWelcome to the %s's ATM\n", bank.getName());
            System.out.println("Enter your credentials bellow:\n");
            System.out.println("User id:");
            userIdTemp = scanner.nextLine();
            userId = Long.parseLong(userIdTemp);
            System.out.println("Password:");
            password = scanner.nextLine();

            authenticatedUser = bank.login(userId, password);
            if (authenticatedUser == null) {
                System.out.println("Invalid credentials provided. Try again.");
            }
        } while (authenticatedUser == null);

        return authenticatedUser;
    }

    public static void displayUserInterface(User user, Scanner scanner) {
        int input = 0;
        do {
            System.out.printf("Welcome %s\n", user.getName());
            System.out.println("please choose one of the options below:");
            System.out.println("    1) Display account information");
            System.out.println("    2) Display account transaction history");
            System.out.println("    3) Deposit funds");
            System.out.println("    4) Withdraw funds");
            System.out.println("    5) Wire funds between own accounts");
            System.out.println("    6) Logout\n");
            System.out.println("Input desired option:");
            input = scanner.nextInt();

            switch (input) {
                case 1:
                    BankController.displayAccountInformation(user);
                    break;
                case 2:
                    BankController.displayAccountTransactionHistory(user, scanner);
                    break;
                case 3:
                    BankController.deposit(user, scanner);
                    break;
                case 4:
                    BankController.withdraw(user, scanner);
                    break;
                case 5:
                    BankController.transfer(user, scanner);
                    break;
                case 6:
                    break;
                default:
                    System.out.println("Invalid input, please try again!");
                    displayUserInterface(user, scanner);
            }
        } while (input != 6);
    }
}
