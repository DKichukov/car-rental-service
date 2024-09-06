package com.example.car_rental_service.utils;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.test.web.servlet.MvcResult;
import org.testcontainers.shaded.com.fasterxml.jackson.core.JsonProcessingException;

public class JsonUtil {

  private static final ObjectMapper objectMapper = new ObjectMapper();

  private JsonUtil() {
  }

  public static String convertToJson(Object object)
      throws JsonProcessingException, com.fasterxml.jackson.core.JsonProcessingException {
    return objectMapper.writeValueAsString(object);
  }

  public static <T> T parseResponseBody(MvcResult result, Class<T> responseType) throws Exception {
    return objectMapper.readValue(result.getResponse().getContentAsString(), responseType);
  }
}
