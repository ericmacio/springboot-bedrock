package emk_bedrock.chat_request.model;

public class ChatRequestEvent {
    private String requestId = null;
    private String status = null;
    private long time = 0;
    private ChatRequest chatRequest;

    public ChatRequestEvent() {
    }

    public ChatRequestEvent(String requestId, String status, Long time, ChatRequest chatRequest) {
        this.requestId = requestId;
        this.status = status;
        this.time = time;
        this.chatRequest = chatRequest;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getStatus() {
        return status;
    }

    public long getTime() {
        return time;
    }

    public ChatRequest getChatRequest() {
        return chatRequest;
    }
}
