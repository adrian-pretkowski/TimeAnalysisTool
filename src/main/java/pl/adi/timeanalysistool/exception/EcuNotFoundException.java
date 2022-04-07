package pl.adi.timeanalysistool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class EcuNotFoundException extends RuntimeException {
    public EcuNotFoundException() {
    }

    public EcuNotFoundException(String message) {
        super(message);
    }

    public EcuNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public EcuNotFoundException(Throwable cause) {
        super(cause);
    }
}
