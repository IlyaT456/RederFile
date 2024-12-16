package readerFile;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.File;

public class JsonHelper {
    private static final ObjectMapper objectMapper = new ObjectMapper();

    public static <T> T fromJson(String jsonPath, Class<T> out) {
        try {
            return objectMapper.readValue(new File(jsonPath), out);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public static String toJson(Object object) {
        try {
            return objectMapper.writeValueAsString(object);
        } catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
