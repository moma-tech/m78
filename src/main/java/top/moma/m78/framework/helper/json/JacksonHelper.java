package top.moma.m78.framework.helper.json;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonParser;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategy;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.google.gson.Gson;
import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;
import top.moma.m78.framework.customizer.exception.exceptions.M78Exception;

/**
 * JacksonHelper
 *
 * <p>Jackson Helper
 *
 * @author ivan
 * @version 1.0 Created by ivan on 12/13/18 - 4:52 PM.
 */
public class JacksonHelper {

  private static ObjectMapper objectMapper;

  static {
    objectMapper = getObjectMapper(new ObjectMapper());
  }

  public static ObjectMapper getObjectMapper(ObjectMapper objectMapper) {
    if (Objects.isNull(objectMapper)) {
      objectMapper = new ObjectMapper();
    }
    return createObjectMapper(objectMapper);
  }

  static ObjectMapper createObjectMapper(ObjectMapper objectMapper) {
    objectMapper.setPropertyNamingStrategy(PropertyNamingStrategy.SNAKE_CASE);
    // Set Feature
    objectMapper.disable(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS);
    objectMapper.disable(SerializationFeature.FAIL_ON_EMPTY_BEANS);
    objectMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

    objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
    objectMapper.configure(MapperFeature.PROPAGATE_TRANSIENT_MARKER, true);
    objectMapper.configure(JsonParser.Feature.ALLOW_BACKSLASH_ESCAPING_ANY_CHARACTER, true);

    // Handle Java Time
    objectMapper.registerModule(new JavaTimeModule());

    // Handle JSR310 Time
    SimpleModule simpleModule = new SimpleModule();
    simpleModule.addSerializer(
        LocalDateTime.class,
        new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
    simpleModule.addSerializer(
        LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
    simpleModule.addSerializer(
        LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));
    // Handle long
    simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
    // Handle XSS
    simpleModule.addSerializer(new XssStringJsonSerializer());
    objectMapper.registerModule(simpleModule);
    return objectMapper;
  }

  public static Object parse(String jsonString) {
    Object object = null;
    try {
      object = getObjectMapper().readValue(jsonString, Object.class);
    } catch (Exception ignore) {
    }
    return object;
  }

  public static <T> T readValue(String json, Class<T> clazz) {
    T t = null;
    try {
      t = getObjectMapper().readValue(json, clazz);
    } catch (Exception ignore) {
    }
    return t;
  }

  public static <T> T readValue(String json, TypeReference valueTypeRef) {
    T t = null;
    try {
      t = getObjectMapper().readValue(json, valueTypeRef);
    } catch (Exception ignore) {
    }
    return t;
  }

  public static String toJson(Object object) {
    if (Objects.nonNull(object) && CharSequence.class.isAssignableFrom(object.getClass())) {
      return object.toString();
    }
    try {
      return getObjectMapper().writeValueAsString(object);
    } catch (JsonProcessingException e) {
      // TODO maybe ignored
      throw new M78Exception(e);
    }
  }

  public static ObjectMapper getObjectMapper() {
    return objectMapper;
  }

  static class XssStringJsonSerializer extends JsonSerializer<String> {
    @Override
    public Class<String> handledType() {
      return String.class;
    }

    @Override
    public void serialize(
        String value, JsonGenerator jsonGenerator, SerializerProvider serializerProvider)
        throws IOException {
      if (value != null) {
        // TODO 暂时不进行XSS处理
        // String encodedValue = HtmlUtils.htmlEscape(value);
        // jsonGenerator.writeString(encodedValue);
        jsonGenerator.writeString(value);
      }
    }
  }

  /**
   * @author Created by ivan on 7:46 PM 12/26/18.
   *     <p>To Camel Json with Gson
   * @param object :
   * @return java.lang.String
   */
  public static String toCamelJson(Object object) {
    Gson gson = new Gson();
    if (Objects.nonNull(object)) {
      return gson.toJson(object);
    } else {
      return new String();
    }
  }
}
