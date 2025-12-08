package emk_bedrock.chat_request.exception;

public class CustomException extends RuntimeException {

    private String errorCode = "Unknown_Exception";

    public CustomException(String message, ExceptionCode errorCode) {
        super(message);
        this.errorCode = errorCode.getValue();
    }

    public String getErrorCode() {
        return errorCode;
    }
}
