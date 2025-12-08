package emk_bedrock.chat_request.model;

public enum RequestStatus {

    INIT("INIT"),
    RUNNING("RUNNING"),
    COMPLETED("COMPLETED"),
    ERROR("ERROR");

    private final String value;

    RequestStatus(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}

