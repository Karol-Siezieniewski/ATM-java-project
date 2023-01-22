import lombok.Getter;
import org.mindrot.jbcrypt.BCrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;

@Getter
public class User {

    private Long id;
    private String name;
    private String lastName;
    private String hashedPassword;
    private ArrayList<Account> accountsList;

    /**
     * User object constructor
     * @param name User name
     * @param lastName User last name
     * @param password password used for
     * @param bank Current users bank
     */
    public User(String name, String lastName, String password, Bank bank) {
        this.name = name;
        this.lastName = lastName;
        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        this.accountsList = new ArrayList<Account>();
        this.id = bank.generateNewUserId();

        System.out.printf("New user (%s %s) was created with ID %d. \n", name, lastName, this.id);
    }
    
    /**
     * Validation of password input using Bcrypt
     * @param passwordToValidate Input password used for validation
     * @return boolean true if password is correct false if incorrect
     */
    public boolean validatePassword(String passwordToValidate) {
        return BCrypt.checkpw(passwordToValidate, hashedPassword);
    }

    /**
     * Add account to Users account list
     * @param account account used to add account to list
     */
    public void addAccount(Account account) {
        this.accountsList.add(account);
    }
    
    /**
     * Display information about all accounts of User
     */
    public void displayAccountsInfo() {
        System.out.printf("\nSummary for accounts of %s %s\n", this.name, this.lastName);
        for (Account account : accountsList) {
            System.out.println(account.toString());
        }
        System.out.println();
    }
}
