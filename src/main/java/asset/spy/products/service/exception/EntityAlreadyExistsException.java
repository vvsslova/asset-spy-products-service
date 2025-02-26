package asset.spy.products.service.exception;

public class EntityAlreadyExistsException extends RuntimeException {
    public EntityAlreadyExistsException(String message, Throwable cause) {
        super(message, cause);
    }
}