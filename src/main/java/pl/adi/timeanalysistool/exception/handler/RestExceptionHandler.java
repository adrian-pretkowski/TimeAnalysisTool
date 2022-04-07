package pl.adi.timeanalysistool.exception.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pl.adi.timeanalysistool.exception.EcuNotFoundException;
import pl.adi.timeanalysistool.exception.FunctionNotFoundException;
import pl.adi.timeanalysistool.exception.TestPlanNotFoundException;
import pl.adi.timeanalysistool.exception.VehicleNotFoundException;

import java.time.ZoneId;
import java.time.ZonedDateTime;

@ControllerAdvice
public class RestExceptionHandler {

    private final HttpStatus notFound = HttpStatus.NOT_FOUND;

    @ExceptionHandler(TestPlanNotFoundException.class)
    public ResponseEntity<Object> handleTestPlanNotFound(TestPlanNotFoundException ex) {
        RestException restException = new RestException(
                ex.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("UTC+2"))
        );

        return new ResponseEntity<>(restException, notFound);
    }

    @ExceptionHandler(VehicleNotFoundException.class)
    public ResponseEntity<Object> handleVehicleNotFound(VehicleNotFoundException ex) {
        RestException restException = new RestException(
                ex.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("UTC+2"))
        );

        return new ResponseEntity<>(restException, notFound);
    }

    @ExceptionHandler(EcuNotFoundException.class)
    public ResponseEntity<Object> handleEcuNotFound(EcuNotFoundException ex) {
        RestException restException = new RestException(
                ex.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("UTC+2"))
        );

        return new ResponseEntity<>(restException, notFound);
    }

    @ExceptionHandler(FunctionNotFoundException.class)
    public ResponseEntity<Object> handleFunctionNotFound(FunctionNotFoundException ex) {
        RestException restException = new RestException(
                ex.getMessage(),
                notFound,
                ZonedDateTime.now(ZoneId.of("UTC+2"))
        );

        return new ResponseEntity<>(restException, notFound);
    }
}
