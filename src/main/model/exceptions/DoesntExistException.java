package model.exceptions;

public class DoesntExistException extends Exception {

    public DoesntExistException(String imaginaryName) {
        super("'" + imaginaryName + "' hasn't been created previously in App.");
    }
}
