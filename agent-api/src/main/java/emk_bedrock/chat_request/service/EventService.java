package emk_bedrock.chat_request.service;

import emk_bedrock.chat_request.aws.EventBridge;
import emk_bedrock.chat_request.helper.DataMapping;
import emk_bedrock.chat_request.model.ChatRequest;
import emk_bedrock.chat_request.model.ChatRequestEvent;
import org.springframework.stereotype.Service;

@Service
public class EventService {

    private final EventBridge eventBridge;


    public EventService(EventBridge eventBridge) {
        this.eventBridge = eventBridge;
    }

    public void sendChatRequestEvent(ChatRequest chatRequest) {

        ChatRequestEvent chatRequestEvent = new ChatRequestEvent(
                chatRequest.getRequestId(),
                chatRequest.getStatus(),
                chatRequest.getTime(),
                chatRequest);

        eventBridge.sendEvent(
                this.getClass().getName(),
                chatRequest.getApp(),
                DataMapping.writeObjAsStr(chatRequestEvent));
    }
}
