import lombok.Getter;

import java.util.ArrayList;

@Getter
public class Bank {
    private final String name;
    private final ArrayList<User> usersList;
    private final ArrayList<Account> accountsList;
    private final static double MAX_USER_ID_VALUE = 999999;
    private final static double MAX_ACCOUNT_ID_VALUE = 999999999;
    private final static double MIN_USER_ID_VALUE = 100000;
    private final static double MIN_ACCOUNT_ID_VALUE = 100000000;

    public Bank(String name) {
        this.name = name;
        this.usersList = new ArrayList<>();
        this.accountsList = new ArrayList<>();
    }

    @Override
    public String toString() {
        return "Bank{" +
                "name ='" + name + '\'' +
                ", amount of users =" + usersList.size() +
                ", amount of accounts =" + accountsList.size() +
                '}';
    }


    /**
     *  This method generates a new user id, that has a length of 6 digits. Next it checks whether generated id is unique,
     *  by checking all of bank's users for a copy of this id.
     *
     * @return  new unique instance of Long id
     */
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

    /**
     *  This method generates a new account id, that has a length of 9 digits. Next it checks whether generated id is unique,
     *  by checking all of bank's accounts for a copy of this id.
     *
     * @return  new unique instance of Long id
     */
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

    /**
     *  Simple method for shortening code for adding new accounts to the list
     * @param account instance of account to be added to the accounts list
     */
    public void addAccount(Account account) {
        this.accountsList.add(account);
    }

    /**
     *  This method creates a new user based on provided parameters. It also ensures that user is assigned to
     *  the instance of bank, and also that instance of account is added to both user and bank.
     * @param name provided name of the user
     * @param lastName provided last name of the user
     * @param password provided password for the user
     * @return  a new instance of user with provided parameters
     */
    public User createUser(String name, String lastName, String password) {
        User user = new User(name, lastName, password, this);
        this.usersList.add(user);

        Account savingsAccount = new Account("Savings", user, this);
        user.addAccount(savingsAccount);
        this.addAccount(savingsAccount);

        return user;
    }

    /**
     *  this method authenticates the user by checking if there is a user with the same id on the list, and if there is,
     *  it checks if the given password matches the stored hashed password
     * @param userID provided name of the user
     * @param password provided password of the user
     * @return  authenticated instance of user that exists in the bank's user's list
     */
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
