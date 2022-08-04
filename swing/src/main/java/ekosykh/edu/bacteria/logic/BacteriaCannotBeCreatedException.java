package ekosykh.edu.bacteria.logic;

public class BacteriaCannotBeCreatedException extends RuntimeException {
    public BacteriaCannotBeCreatedException(String message) {
        super("A new bacteria cannot be created: " + message);
    }
}
