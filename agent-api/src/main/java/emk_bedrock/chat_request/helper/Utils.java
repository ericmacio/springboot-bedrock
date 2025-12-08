package emk_bedrock.chat_request.helper;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;

public class Utils {

    private final static Logger LOGGER = LoggerFactory.getLogger(Utils.class);

    public static String getLocalDateTimeStr() {
        return Instant.now()
                .atZone(ZoneId.of("Europe/Paris"))
                .toLocalDateTime().toString();
    }

    public static String getLocalDateTimeStr(Long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.of("Europe/Paris"))
                .toLocalDateTime().toString();
    }

    public static LocalDateTime getLocalDateTime(Long timestamp) {
        return Instant.ofEpochMilli(timestamp)
                .atZone(ZoneId.of("Europe/Paris"))
                .toLocalDateTime();
    }

    public static LocalDateTime convertLocalDateTime(Instant instant) {
        return instant
                .atZone(ZoneId.of("Europe/Paris"))
                .toLocalDateTime();
    }

    public static LocalDateTime getCurrentLocalDateTime() {
        return Instant.now()
                .atZone(ZoneId.of("Europe/Paris"))
                .toLocalDateTime();
    }

    public static void logErr(String err) {
        LOGGER.error("ERROR: {}", err);
    }

}
