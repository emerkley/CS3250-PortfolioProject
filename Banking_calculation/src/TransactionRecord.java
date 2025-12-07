import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TransactionRecord {
    private double amount;
    private String description;
    private LocalDateTime dateTime;
    private String accountType; 
    private String transactionType;
    
    
    private static final DateTimeFormatter DISPLAY_FORMAT = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public TransactionRecord(double amount, String description, LocalDateTime dateTime,
                             String accountType, String transactionType) {
        this.amount = amount;
        this.description = description;
        this.dateTime = dateTime;
        this.accountType = accountType;
        this.transactionType = transactionType;
    }

    public LocalDateTime getDateTime() {
        return dateTime;
    }

    public String getFormattedDateTime() {
        return dateTime.format(DISPLAY_FORMAT);
    }

    public double getAmount() { return amount; }
    public String getDescription() { return description; }
    public String getAccountType() { return accountType; }
    public String getTransactionType() { return transactionType; }

    @Override
    public String toString() {
        return accountType + " | " + transactionType + " | " 
               + dateTime.format(DISPLAY_FORMAT) + " | " 
               + description + " | $" + amount;
    }
}
