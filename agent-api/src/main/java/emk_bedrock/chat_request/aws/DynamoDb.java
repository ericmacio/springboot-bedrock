package emk_bedrock.chat_request.aws;

import emk_bedrock.chat_request.exception.CustomException;
import emk_bedrock.chat_request.exception.ExceptionCode;
import emk_bedrock.chat_request.helper.Utils;
import emk_bedrock.chat_request.model.ChatRequest;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.dynamodb.DynamoDbClient;
import software.amazon.awssdk.services.dynamodb.model.AttributeValue;
import software.amazon.awssdk.services.dynamodb.model.DynamoDbException;
import software.amazon.awssdk.services.dynamodb.model.PutItemRequest;
import software.amazon.awssdk.services.dynamodb.model.ResourceNotFoundException;
import software.amazon.awssdk.services.dynamodb.model.GetItemRequest;

import java.util.HashMap;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

@Service
public class DynamoDb {

    private final static Logger LOGGER = LoggerFactory.getLogger(DynamoDb.class);
    private final String REGION = "eu-west-3";

    private final DynamoDbClient dynamoDbClient;

    public DynamoDb() {
        dynamoDbClient = getDynamoDbClient();
    }

    public void putChatRequestInTable(String tableName, ChatRequest chatRequest) {

        HashMap<String, AttributeValue> itemValues = new HashMap<>();
        itemValues.put("requestId", AttributeValue.builder().s(chatRequest.getRequestId()).build());
        itemValues.put("app", AttributeValue.builder().s(chatRequest.getApp()).build());
        if (chatRequest.getTool() != null) {
            itemValues.put("tool", AttributeValue.builder().s(chatRequest.getTool()).build());
        }
        if (chatRequest.getSessionId() != null) {
            itemValues.put("sessionId", AttributeValue.builder().s(chatRequest.getSessionId()).build());
        }
        itemValues.put("message", AttributeValue.builder().s(chatRequest.getMessage()).build());
        itemValues.put("status", AttributeValue.builder().s(chatRequest.getStatus()).build());
        itemValues.put("date", AttributeValue.builder().s(Utils.getLocalDateTimeStr(chatRequest.getTime())).build());

        PutItemRequest request = PutItemRequest.builder()
                .tableName(tableName)
                .item(itemValues)
                .build();

        try {
            dynamoDbClient.putItem(request);
            LOGGER.info("{} was successfully updated", tableName);
        } catch(ResourceNotFoundException e){
            String err = "The DynamoDB table can't be found" + tableName;
            Utils.logErr(err);
            throw new CustomException(err, ExceptionCode.DYNAMODB);
        } catch(DynamoDbException e){
            String err = e.getMessage();
            Utils.logErr(err);
            throw new CustomException("DynamoDbException: " + err, ExceptionCode.DYNAMODB);
        }

    }
    public ChatRequest getChatRequestFromTable(String tableName, String requestId) {

        HashMap<String, AttributeValue> keyToGet = new HashMap<>();
        keyToGet.put("requestId", AttributeValue.builder().s(requestId).build());

        GetItemRequest request = GetItemRequest.builder()
                .key(keyToGet)
                .tableName(tableName)
                .build();

        try {
            // If there is no matching item, GetItem does not return any data.
            Map<String, AttributeValue> returnedItem = dynamoDbClient.getItem(request).item();
            if (returnedItem.isEmpty()) {
                String err = "No item found with requestId: " + requestId;
                Utils.logErr(err);
                throw new CustomException("DynamoDbException: " + err, ExceptionCode.DYNAMODB);
            } else {
                Set<String> keys = returnedItem.keySet();
                LOGGER.info("DynamoDB table attributes: ");
                for (String key1 : keys) {
                    if(returnedItem.get(key1) != null) {
                        LOGGER.info("{}: {}", key1, returnedItem.get(key1).s());
                    }
                }
                return new ChatRequest(
                        returnedItem.get("requestId").s(),
                        returnedItem.get("status").s(),
                        returnedItem.get("app").s(),
                        returnedItem.get("message").s(),
                        returnedItem.get("tool") != null ? returnedItem.get("tool").s() : null,
                        returnedItem.get("sessionId") != null ? returnedItem.get("sessionId").s() : null,
                        returnedItem.get("durationSec") != null ? returnedItem.get("durationSec").s() : null,
                        returnedItem.get("response") != null ? returnedItem.get("response").s() : null
                );
            }
        } catch (DynamoDbException e) {
            String err = e.getMessage();
            Utils.logErr(err);
            throw new CustomException("DynamoDbException: " + err, ExceptionCode.DYNAMODB);
        }
    }



    private DynamoDbClient getDynamoDbClient() {
        return DynamoDbClient.builder()
                .region(Region.of(REGION))
                .build();
    }
}
