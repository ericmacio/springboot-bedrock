package emk_bedrock.chat_request.controller;

import emk_bedrock.chat_request.aws.EventBridge;
import emk_bedrock.chat_request.model.*;
import emk_bedrock.chat_request.service.AiAgentService;
import emk_bedrock.chat_request.service.ChatService;
import emk_bedrock.chat_request.service.EventService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@RestController
@RequestMapping(value="/api/v1")
public class AgentApi {

    private final static Logger LOGGER = LoggerFactory.getLogger(AgentApi.class);

    private final ChatService chatService;
    private final AiAgentService aiAgentService;
    private final EventService eventService;

    public AgentApi(ChatService chatService, EventBridge eventBridge, AiAgentService aiAgentService, EventService eventService) {
        this.chatService = chatService;
        this.aiAgentService = aiAgentService;
        this.eventService = eventService;
    }

    @PostMapping(value="/chat", produces="application/json")
    ResponseEntity<UserRequestResponse> postChat(@RequestBody @Valid UserRequest userRequest) {

        // Send a chatRequest event
        ChatRequest chatRequest = chatService.createRequest(userRequest);
        eventService.sendChatRequestEvent(chatRequest);

        // Build and send response to the client
        UserRequestResponse userRequestResponse =
                new UserRequestResponse(chatRequest.getRequestId(), chatRequest.getStatus(),null, null, 0, null);

        return new ResponseEntity<>(userRequestResponse, HttpStatus.OK);

    }

    @GetMapping(value="/chat/request/{requestId}", produces="application/json")
    ResponseEntity<UserRequestResponse> getUserRequestResponse(@PathVariable String requestId) {
        return new ResponseEntity<>(chatService.getUserRequestResponse(requestId), HttpStatus.OK);
    }

    @GetMapping(value="/chat/session/{sessionId}", produces="application/json")
    ResponseEntity<List<ChatMessage>> getChatSession(@PathVariable String sessionId) {
        return new ResponseEntity<>(chatService.getChatSession(sessionId), HttpStatus.OK);
    }

    @GetMapping(value="/bedrock/models", produces="application/json")
    ResponseEntity<List<BedrockModel>> getBedrockModels() {
        return new ResponseEntity<>(aiAgentService.getBedrockModels(), HttpStatus.OK);
    }
}
