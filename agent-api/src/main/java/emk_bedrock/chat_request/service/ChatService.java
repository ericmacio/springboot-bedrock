package emk_bedrock.chat_request.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import emk_bedrock.chat_request.aws.DynamoDb;
import emk_bedrock.chat_request.aws.S3;
import emk_bedrock.chat_request.exception.CustomException;
import emk_bedrock.chat_request.exception.ExceptionCode;
import emk_bedrock.chat_request.model.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class ChatService {

    private final static Logger LOGGER = LoggerFactory.getLogger(ChatService.class);
    private final static String DYNAMODB_TABLE = "bedrock-api-dev-chat-requests";
    private final static String S3_BUCKET = "bedrock-api-dev-memory-481160876852";

    private final DynamoDb dynamoDb;
    private final S3 s3;

    public ChatService(DynamoDb dynamoDb, S3 s3) {
        this.dynamoDb = dynamoDb;
        this.s3 = s3;
    }

    public UserRequestResponse getUserRequestResponse(String requestId) {

        LOGGER.info("requestId: {}", requestId);

        ChatRequest chatRequest = dynamoDb.getChatRequestFromTable(DYNAMODB_TABLE, requestId);

        int durationSec = chatRequest.getDurationSec() != null ? Integer.parseInt(chatRequest.getDurationSec()) : 0;
        return new UserRequestResponse(requestId, chatRequest.getStatus(), chatRequest.getResponse(),
                chatRequest.getSessionId(), durationSec,null);
    }

    public List<ChatMessage> getChatSession(String sessionId) {

        LOGGER.info("sessionId: {}", sessionId);

        // Download conversation from S3
        String chatMessagesStr = s3.readDataFromS3(S3_BUCKET, getS3Key(sessionId));

        byte[] jsonData = chatMessagesStr.getBytes(StandardCharsets.UTF_8);
        List<ChatMessage> chatMessageList;
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            chatMessageList = objectMapper.readValue(jsonData, new TypeReference<>() {});
        } catch (IOException e) {
            LOGGER.error("ERROR: getChatMessagesFromS3 exception: objectMapper.readValue");
            throw new CustomException("Cannot map data to ChatMessage class. Exception: " + e.getMessage(), ExceptionCode.PARSING);
        }

        return chatMessageList;
    }

    public ChatRequest createRequest(UserRequest userRequest) {

        Instant instant = Instant.now();
        long time = instant.toEpochMilli();
        UUID uuid = UUID.randomUUID();
        String requestId = uuid.toString();
        LOGGER.info("Create requestId: {}", requestId);
        ChatRequest chatRequest = new ChatRequest(
                requestId,
                RequestStatus.INIT.getValue(),
                userRequest.app(),
                userRequest.message(),
                userRequest.tool(),
                userRequest.sessionId(),
                time);

        // Store API request info in database
        dynamoDb.putChatRequestInTable(DYNAMODB_TABLE, chatRequest);

        return chatRequest;
    }
    private static String getS3Key(String sessionId) {
        return sessionId + ".json";
    }

}
