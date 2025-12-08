package emk_bedrock.chat_request.model;

import jakarta.validation.constraints.NotEmpty;

public class ChatRequest {
    private String requestId;
    private String status;
    private String app;
    @NotEmpty(message = "message cannot be null or empty")
    private String message;
    private String tool;
    private String sessionId;
    private String response;
    private long time;
    private String durationSec;

    public ChatRequest() {
    }

    public ChatRequest(String requestId, String status, String app, String message, String tool, String sessionId, long time) {
        this.requestId = requestId;
        this.status = status;
        this.app = app;
        this.message = message;
        this.tool = tool;
        this.sessionId = sessionId;
        this.time = time;
    }

    public ChatRequest(String requestId, String status, String app, String message, String tool, String sessionId, String durationSec, String response) {
        this.requestId = requestId;
        this.status = status;
        this.app = app;
        this.message = message;
        this.tool = tool;
        this.sessionId = sessionId;
        this.durationSec = durationSec;
        this.response = response;
    }

    public String getRequestId() {
        return requestId;
    }

    public String getDurationSec() {
        return durationSec;
    }

    public String getStatus() {
        return status;
    }

    public String getApp() {
        return app;
    }

    public String getMessage() {
        return message;
    }

    public String getTool() {
        return tool;
    }

    public String getResponse() {
        return response;
    }

    public String getSessionId() {
        return sessionId;
    }

    public long getTime() {
        return time;
    }

    public void setResponse(String response) {
        this.response = response;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public void setTime(long time) {
        this.time = time;
    }
}
