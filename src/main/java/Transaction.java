import lombok.Getter;

import java.time.LocalDateTime;

@Getter
public class Transaction {
    private final double amount;
    private final LocalDateTime timestamp;
    private final String message;
    private final Account account;

    public Transaction(double amount, String message, Account account) {
        this.amount = amount;
        this.message = message;
        this.timestamp = LocalDateTime.now();
        this.account = account;
    }

    @Override
    public String toString() {
        if (this.amount >= 0) {
            return String.format("%s : +\u20AC%.02f : %s", this.timestamp.toString(), this.amount, this.message);
        } else {
            return String.format("%s : -\u20AC%.02f : %s", this.timestamp.toString(), -this.amount, this.message);
        }
    }
}
