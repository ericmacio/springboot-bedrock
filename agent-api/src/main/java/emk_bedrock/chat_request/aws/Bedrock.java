package emk_bedrock.chat_request.aws;

import emk_bedrock.chat_request.exception.CustomException;
import emk_bedrock.chat_request.exception.ExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.exception.SdkClientException;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.bedrock.BedrockClient;
import software.amazon.awssdk.services.bedrock.model.FoundationModelSummary;
import software.amazon.awssdk.services.bedrock.model.ListFoundationModelsResponse;

import java.util.List;

@Service
public class Bedrock {

    private final static Logger LOGGER = LoggerFactory.getLogger(Bedrock.class);
    private final static String REGION = "eu-west-3";

    private BedrockClient bedrockClient;

    public Bedrock() {
        bedrockClient = getBedrockClient();
    }

    public List<FoundationModelSummary> getFoundationModels() {

        try {
            ListFoundationModelsResponse response = bedrockClient.listFoundationModels(r -> {});

            List<FoundationModelSummary> models = response.modelSummaries();

            if (models.isEmpty()) {
                LOGGER.warn("WARNING: No available foundation models in {}", REGION);
            } else {
                for (FoundationModelSummary model : models) {
                    LOGGER.info("Model ID: {}", model.modelId());
                    LOGGER.info("Provider: {}", model.providerName());
                    LOGGER.info("Name:     {}", model.modelName());
                }
            }

            return models;

        } catch (SdkClientException e) {
            LOGGER.error("ERROR: Exception bedrockClient.listFoundationModels: {}", e.getMessage());
            throw new CustomException(e.getMessage(), ExceptionCode.BEDROCK);
        }

    }


    private BedrockClient getBedrockClient() {
        if (bedrockClient == null) {
            bedrockClient =  BedrockClient.builder()
                    .region(Region.of(REGION))
                    .build();
        }
        return bedrockClient;
    }
}
