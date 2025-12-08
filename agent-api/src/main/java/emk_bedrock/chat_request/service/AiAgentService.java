package emk_bedrock.chat_request.service;

import emk_bedrock.chat_request.aws.Bedrock;
import emk_bedrock.chat_request.model.BedrockModel;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.bedrock.model.FoundationModelSummary;

import java.util.List;

@Service
public class AiAgentService {

    private final Bedrock bedrock;

    public AiAgentService(Bedrock bedrock) {
        this.bedrock = bedrock;
    }

    public List<BedrockModel> getBedrockModels() {

        List<FoundationModelSummary> foundationModelSummaries = bedrock.getFoundationModels();

        // Create model list for serialization

        return foundationModelSummaries.stream()
                .map(fms -> new BedrockModel(fms.modelId(), fms.providerName(), fms.modelName()))
                .toList();
    }
}
