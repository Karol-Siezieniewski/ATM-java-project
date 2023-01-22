import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Bank {
    private String name;
    private ArrayList<User> usersList;
    private ArrayList<Account> accountsList;
    private static double MAX_USER_ID_VALUE = 999999;
    private static double MAX_ACCOUNT_ID_VALUE = 999999999;
    private static double MIN_USER_ID_VALUE = 100000;
    private static double MIN_ACCOUNT_ID_VALUE = 100000000;

    public Bank(String name) {
        this.name = name;
        this.usersList = new ArrayList<User>();
        this.accountsList = new ArrayList<Account>();
    }

    @Override
    public String toString() {
        return "Bank{" +
                "name ='" + name + '\'' +
                ", amount of users =" + usersList.size() +
                ", amount of accounts =" + accountsList.size() +
                '}';
    }

    public Long generateNewUserId() {
        Long id = 0L;
        boolean unique = false;

        do {
            id = Math.round(Math.random() * (MAX_USER_ID_VALUE - MIN_USER_ID_VALUE) + MIN_USER_ID_VALUE);
            Long finalId = id;
            unique = usersList.stream()
                    .filter(user -> user.getId().compareTo(finalId) == 0)
                    .findAny()
                    .isEmpty();
        } while (!unique);

        return id;
    }

    public Long generateNewAccountId() {
        Long id = 0L;
        boolean unique = false;

        do {
            id = Math.round(Math.random() * (MAX_ACCOUNT_ID_VALUE - MIN_ACCOUNT_ID_VALUE) + MIN_ACCOUNT_ID_VALUE);
            Long finalId = id;
            unique = accountsList.stream()
                    .filter(account -> account.getId().compareTo(finalId) == 0)
                    .findAny()
                    .isEmpty();
        } while (!unique);

        return id;
    }

    public void addAccount(Account account) {
        this.accountsList.add(account);
    }

    public User createUser(String name, String lastName, String password) {
        User user = new User(name, lastName, password, this);
        this.usersList.add(user);

        Account savingsAccount = new Account("Savings", user, this);
        user.addAccount(savingsAccount);
        this.addAccount(savingsAccount);

        return user;
    }

    public User login(Long userID, String password) {
        for (User user : usersList) {
            if (user.getId().longValue() == userID) {
                if (user.validatePassword(password)) {
                    return user;
                }
            }
        }
        return null;
    }
}
