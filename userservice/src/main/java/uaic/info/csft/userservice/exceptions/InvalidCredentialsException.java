package uaic.info.csft.userservice.exceptions;

public class InvalidCredentialsException extends RuntimeException {

    public InvalidCredentialsException()
    {
        super("Invalid username or password!");
    }
}
