package emk_bedrock.chat_request.exception;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;
import java.util.NoSuchElementException;

import static org.springframework.http.HttpStatus.BAD_REQUEST;
import static org.springframework.http.HttpStatus.NOT_FOUND;

@RestControllerAdvice //Just need to return a java body object instead of a ResponseEntity
class GlobalExceptionHandler {

    private static final Logger LOG = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler({CustomException.class})
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ErrorMessage handleCustomException(HttpServletRequest request, Exception e) {
        LOG.warn("handleCustomException. Build HTTP BAD_REQUEST response");
        return createErrorMessage(BAD_REQUEST, request, e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ErrorMessage handleNotValidException(HttpServletRequest request, MethodArgumentNotValidException e) {
        List<String> errors = getErrorsMsgList(e);
        LOG.warn("handleNotValidException. Build HTTP BAD_REQUEST response");
        return createErrorMessage(BAD_REQUEST, request, errors.toString());
    }

    @ExceptionHandler(ConstraintViolationException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ErrorMessage handleConstraintViolationException(HttpServletRequest request, ConstraintViolationException e) {
        LOG.warn("handleConstraintViolationException. Build HTTP BAD_REQUEST response");
        return createErrorMessage(BAD_REQUEST, request, e.getMessage());
    }

    @ExceptionHandler({NoSuchElementException.class, NumberFormatException.class})
    @ResponseStatus(NOT_FOUND)
    public @ResponseBody ErrorMessage handleNotFoundException(HttpServletRequest request, Exception e) {
        LOG.warn("handleNotFoundException. Build HTTP NOT_FOUND response");
        return createErrorMessage(NOT_FOUND, request, e.getMessage());
    }

    @ExceptionHandler({HttpMessageNotReadableException.class, HttpRequestMethodNotSupportedException.class})
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ErrorMessage handleNotReadableException(HttpServletRequest request, Exception e) {
        LOG.warn("handleNotReadableException. Build HTTP BAD_REQUEST response");
        return createErrorMessage(BAD_REQUEST, request, e.getMessage());
    }

    @ExceptionHandler(IllegalArgumentException.class)
    @ResponseStatus(BAD_REQUEST)
    public @ResponseBody ErrorMessage handleIllegalArgumentException(HttpServletRequest request, Exception e) {
        LOG.warn("handleIllegalArgumentException. Build HTTP BAD_REQUEST response");
        return createErrorMessage(BAD_REQUEST, request, e.getMessage());
    }

    private ErrorMessage createErrorMessage(HttpStatus httpStatus, HttpServletRequest request, String message) {
        final String path = request.getServletPath();
        LOG.warn("Returning HTTP status: {} for path: {}, message: {}", httpStatus, path, message);
        return new ErrorMessage(httpStatus, path, message);
    }

    private static List<String> getErrorsMsgList(MethodArgumentNotValidException e) {
        return e.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(DefaultMessageSourceResolvable::getDefaultMessage)
                .toList();
    }
}

