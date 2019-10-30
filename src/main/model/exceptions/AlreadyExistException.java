package model.exceptions;

public class AlreadyExistException extends Exception {

    public AlreadyExistException(String duplicateName) {
        super("'" + duplicateName + "' has already been created previously in App.");
    }
}
