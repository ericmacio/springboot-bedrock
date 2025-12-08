package emk_bedrock.chat_request.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.NotEmpty;

public record UserRequest(
        @NotEmpty(message = "message cannot be null or empty")
        String message,
        @NotEmpty(message = "app cannot be null or empty")
        String app,
        String tool,
        @JsonProperty("session_id")
        String sessionId
) {
}
