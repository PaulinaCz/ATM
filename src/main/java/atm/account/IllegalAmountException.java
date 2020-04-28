package atm.account;

public class IllegalAmountException extends Exception {
    public IllegalAmountException(String message) {
        super(message);
    }
}
