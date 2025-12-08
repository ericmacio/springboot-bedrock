package emk_bedrock.chat_request.aws;

import emk_bedrock.chat_request.exception.CustomException;
import emk_bedrock.chat_request.exception.ExceptionCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.s3.model.*;
import software.amazon.awssdk.services.s3.S3Client;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.stream.Collectors;

@Service
public class S3 {

    private final static Logger LOGGER = LoggerFactory.getLogger(DynamoDb.class);
    private final String REGION = "eu-west-3";

    private final S3Client s3Client;

    public S3() {
        s3Client = getS3Client();
    }

    public String readDataFromS3(String bucketName, String key) {
        try {
            GetObjectRequest getObjectRequest = GetObjectRequest.builder()
                    .bucket(bucketName)
                    .key(key)
                    .build();
            ResponseInputStream<GetObjectResponse> s3objectResponse = s3Client.getObject(getObjectRequest);
            return new BufferedReader(new InputStreamReader(s3objectResponse))
                    .lines()
                    .collect(Collectors.joining());
        } catch (S3Exception e) {
            throw new CustomException(
                    "Failed to read data from S3: " + e.awsErrorDetails().errorMessage() + ", S3 key: " + key,
                    ExceptionCode.S3_READ);
        }
    }

    private S3Client getS3Client() {
        return S3Client.builder()
                .region(Region.of(REGION))
                .build();

    }

}
