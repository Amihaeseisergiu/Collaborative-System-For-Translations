package uaic.info.csft.userservice.exceptions;

public class UsernameExistsException extends RuntimeException {

    public UsernameExistsException()
    {
        super("Username already exists!");
    }
}
