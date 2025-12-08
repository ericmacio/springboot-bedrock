package emk_bedrock.chat_request.aws;

import emk_bedrock.chat_request.exception.CustomException;
import emk_bedrock.chat_request.exception.ExceptionCode;
import emk_bedrock.chat_request.helper.Utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.eventbridge.EventBridgeClient;
import software.amazon.awssdk.services.eventbridge.model.*;

import java.util.Optional;

@Service
public class EventBridge {

    private final static Logger LOGGER = LoggerFactory.getLogger(EventBridge.class);
    private final static String REGION = Optional.ofNullable(System.getenv("REGION")).orElse("eu-west-3");

    private final EventBridgeClient eventBridgeClient;

    public EventBridge() {
        this.eventBridgeClient = getEventBridgeClient();
    }

    public void sendEvent(String source, String detailType, String jsonDetail) {

        PutEventsRequestEntry entry = PutEventsRequestEntry.builder()
                .source(source)
                .detailType(detailType)
                .detail(jsonDetail)
                .build();

        PutEventsRequest eventsRequest = PutEventsRequest.builder()
                .entries(entry)
                .build();

        try {
            PutEventsResponse result = eventBridgeClient.putEvents(eventsRequest);
            for (PutEventsResultEntry resultEntry : result.entries()) {
                if (resultEntry.eventId() != null) {
                    LOGGER.info("Event Id: {}", resultEntry.eventId());
                } else {
                    String err = "Injection failed with Error Code: " + resultEntry.errorCode();
                    Utils.logErr(err);
                    throw new CustomException(err, ExceptionCode.EVENTBRIDGE);
                }
            }
            LOGGER.info("Event was successfully sent");
        } catch (EventBridgeException e) {
            String err = "Exception eventBridgeClient.putEvents: " + e.getMessage() ;
            Utils.logErr(err);
            throw new CustomException(err, ExceptionCode.EVENTBRIDGE);
        }
    }

    private EventBridgeClient getEventBridgeClient() {
        return EventBridgeClient.builder()
                .region(Region.of(REGION))
                .build();
    }

}
