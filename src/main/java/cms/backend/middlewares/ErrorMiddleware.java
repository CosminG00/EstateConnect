package cms.backend.middlewares;

import cms.backend.constants.ErrorConstants;
import cms.backend.exceptions.ConflictException;
import cms.backend.exceptions.NotFoundException;
import cms.backend.exceptions.UnauthorizedException;
import cms.backend.responses.ErrorResponsePayload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class ErrorMiddleware {
    private final Logger logger = LoggerFactory.getLogger(ErrorMiddleware.class);

    @ExceptionHandler(NotFoundException.class)
    public final ResponseEntity<ErrorResponsePayload> handleNotFoundException(NotFoundException e) {
        this.logger.error("Not found error", e);
        ErrorResponsePayload errorResponsePayload = new ErrorResponsePayload(e.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(errorResponsePayload);
    }

    @ExceptionHandler(UnauthorizedException.class)
    public final ResponseEntity<ErrorResponsePayload> handleUnauthorizedException(UnauthorizedException e) {
        this.logger.error("Unauthorized error", e);
        ErrorResponsePayload errorResponsePayload = new ErrorResponsePayload(e.getMessage());
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(errorResponsePayload);
    }

    @ExceptionHandler(ConflictException.class)
    public final ResponseEntity<ErrorResponsePayload> handleConflictException(ConflictException e) {
        this.logger.error("Conflict error", e);
        ErrorResponsePayload errorResponsePayload = new ErrorResponsePayload(e.getMessage());
        return ResponseEntity.status(HttpStatus.CONFLICT).body(errorResponsePayload);
    }

    @ExceptionHandler(Exception.class)
    public final ResponseEntity<ErrorResponsePayload> handleAllExceptions(Exception e) {
        this.logger.error("Internal server error", e);
        ErrorResponsePayload errorResponsePayload = new ErrorResponsePayload(ErrorConstants.INTERNAL_SERVER_ERROR);
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(errorResponsePayload);
    }
}
