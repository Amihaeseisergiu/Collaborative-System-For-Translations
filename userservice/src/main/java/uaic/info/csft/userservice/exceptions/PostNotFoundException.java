package uaic.info.csft.userservice.exceptions;

public class PostNotFoundException extends RuntimeException {

    public PostNotFoundException(Long id) {
        super(String.format("Post with id %s not found", id));
    }

}
