package asset.spy.products.service.exception;

public class EntityNotSavedException extends RuntimeException {
    public EntityNotSavedException(String message, Throwable cause) {
        super(message, cause);
    }
}
