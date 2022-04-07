package pl.adi.timeanalysistool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class FunctionNotFoundException extends RuntimeException{
    public FunctionNotFoundException() {
    }

    public FunctionNotFoundException(String message) {
        super(message);
    }

    public FunctionNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public FunctionNotFoundException(Throwable cause) {
        super(cause);
    }
}
