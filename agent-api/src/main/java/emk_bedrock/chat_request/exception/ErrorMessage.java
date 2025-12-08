package emk_bedrock.chat_request.exception;

import com.fasterxml.jackson.annotation.JsonProperty;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;

public class ErrorMessage {

    private final HttpStatus httpStatus;
    @JsonProperty("status_code")
    private final int statusCode;
    @JsonProperty("status_reason")
    private final String statusReason;
    private final String path;
    private final String message;
    private final ZonedDateTime timestamp;

    public ErrorMessage() {
        this.statusCode = 0;
        this.statusReason = null;
        this.httpStatus = null;
        this.path = null;
        this.message = null;
        this.timestamp = null;
    }

    public ErrorMessage(HttpStatus httpStatus, String path, String message) {
        this.httpStatus = httpStatus;
        this.statusCode = httpStatus.value();
        this.statusReason = httpStatus.getReasonPhrase();
        this.path = path;
        this.message = message;
        this.timestamp = ZonedDateTime.now();
    }

    public int getStatusCode() {
        return statusCode;
    }

    public String getStatusReason() {
        return statusReason;
    }

    public String getPath() {
        return path;
    }

    public String getMessage() {
        return message;
    }

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }





}

