package emk_bedrock.chat_request.helper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.TypeFactory;
import emk_bedrock.chat_request.exception.CustomException;
import emk_bedrock.chat_request.exception.ExceptionCode;
import emk_bedrock.chat_request.model.AwsEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.io.InputStream;

public class DataMapping {

    private final static Logger LOGGER = LoggerFactory.getLogger(DataMapping.class);

    static ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T parseObjectStr(String objStr, Class<T> outPutClass) {
        try {
            return objectMapper.readValue(objStr, outPutClass);
        } catch (JsonProcessingException e) {
            String err = "Exception objectMapper.readValue: " + e.getMessage() ;
            Utils.logErr(err);
            throw new CustomException(err, ExceptionCode.PARSING);
        }
    }

    public static <T> AwsEvent<T> parseAwsEventStr(InputStream input, Class<T> outPutClass) {
        final TypeFactory typeFactory = objectMapper.getTypeFactory();
        try {
            return objectMapper.readValue(input, typeFactory.constructParametricType(AwsEvent.class, outPutClass));
        } catch (IOException e) {
            String err = "Exception objectMapper.readValue: " + e.getMessage() ;
            Utils.logErr(err);
            throw new CustomException(err, ExceptionCode.PARSING);
        }
    }

    public static <T> String writeObjAsStr(T obj) {
        String objStr;
        try {
            objStr = objectMapper.writeValueAsString(obj);
        } catch (JsonProcessingException e) {
            LOGGER.error("ERROR: writeObjAsStr exception: objectMapper.writeValueAsString");
            throw new CustomException(e.getMessage(), ExceptionCode.PARSING);
        }
        return objStr;
    }

}

