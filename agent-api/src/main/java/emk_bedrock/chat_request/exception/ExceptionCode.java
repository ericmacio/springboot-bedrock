package emk_bedrock.chat_request.exception;

public enum ExceptionCode {

    PARSING("PARSING_EXCEPTION"),
    NOT_FOUND("NOT_FOUND_EXCEPTION"),
    DYNAMODB("DYNAMODB"),
    CONVERSATION("CONVERSATION"),
    EVENTBRIDGE("EVENTBRIDGE"),
    PDF("PDF_READ_EXCEPTION"),
    BEDROCK("BEDROCK_EXCEPTION"),
    HTTP("HTTP_EXCEPTION"),
    ENV("ENV_EXCEPTION"),
    FILE_REPOSITORY("FILE_REPOSITORY_EXCEPTION"),
    BAD_REQUEST("BAD_REQUEST_EXCEPTION"),
    S3_READ("S3_READ_EXCEPTION"),
    S3_WRITE("S3_WRITE_EXCEPTION");

    private final String value;

    ExceptionCode(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
