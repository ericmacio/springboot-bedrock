package emk_bedrock.chat_request.model;

public record ChatMessage(
        String role,
        String text,
        String timestamp
) {}
