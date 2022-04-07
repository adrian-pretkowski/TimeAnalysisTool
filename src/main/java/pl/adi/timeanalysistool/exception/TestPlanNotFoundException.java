package pl.adi.timeanalysistool.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value = HttpStatus.NOT_FOUND)
public class TestPlanNotFoundException extends RuntimeException {
    public TestPlanNotFoundException() {
        super();
    }

    public TestPlanNotFoundException(String message) {
        super(message);
    }

    public TestPlanNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public TestPlanNotFoundException(Throwable cause) {
        super(cause);
    }
}
