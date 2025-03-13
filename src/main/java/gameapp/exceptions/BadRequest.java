package gameapp.exceptions;

public class BadRequest extends IllegalArgumentException{
    public BadRequest() {
        super();
    }

    public BadRequest(String message) {
        super(message);
    }
}
