package com.example.car_rental_service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import java.io.IOException;
import java.util.List;
import org.springframework.test.web.servlet.MvcResult;

public class JsonUtil {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private JsonUtil() {
  }

  public static String convertToJson(Object object)
      throws com.fasterxml.jackson.core.JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  public static <T> T parseResponseBody(MvcResult result, Class<T> responseType) throws Exception {
    return objectMapper.readValue(result.getResponse().getContentAsString(), responseType);
  }

  public static <T> List<T> parseResponseBodyToList(MvcResult result, Class<T> responseType)
      throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();
    return objectMapper.readValue(result.getResponse().getContentAsString(),
        objectMapper.getTypeFactory().constructCollectionType(List.class, responseType));
  }
}
