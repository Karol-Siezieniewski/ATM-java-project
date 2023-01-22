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


    public User(String name, String lastName, String password, Bank bank) {
        this.name = name;
        this.lastName = lastName;
        this.hashedPassword = BCrypt.hashpw(password, BCrypt.gensalt());
        this.accountsList = new ArrayList<Account>();
        this.id = bank.generateNewUserId();

        System.out.printf("New user (%s %s) was created with ID %d. \n", name, lastName, this.id);
    }

    public boolean validatePassword(String passwordToValidate) {
        return BCrypt.checkpw(passwordToValidate, hashedPassword);
    }

    public void addAccount(Account account) {
        this.accountsList.add(account);
    }

    public void displayAccountsInfo() {
        System.out.printf("\nSummary for accounts of %s %s\n", this.name, this.lastName);
        for (Account account : accountsList) {
            System.out.println(account.toString());
        }
        System.out.println();
    }

    private byte[] generatePassword(String password) throws NoSuchAlgorithmException {
        MessageDigest messageDigest = MessageDigest.getInstance("MD5");
        return messageDigest.digest(password.getBytes());
    }

}
