package emk_bedrock.chat_request.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public record UserRequestResponse(
        @JsonProperty("request_id")
        String requestId,
        String status,
        String response,
        @JsonProperty("session_id")
        String sessionId,
        @JsonProperty("duration_sec")
        int durationSec,
        String err
) {
}

