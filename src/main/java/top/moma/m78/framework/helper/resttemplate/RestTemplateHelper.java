package top.moma.m78.framework.helper.resttemplate;

import java.time.Duration;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import top.moma.m78.framework.constants.HttpConstants;
import top.moma.m78.framework.constants.enumeration.JsonNamingStrategyEnum;
import top.moma.m78.framework.helper.TypeHelper;
import top.moma.m78.framework.helper.json.JacksonHelper;
import top.moma.m78.framework.helper.modelmapper.BeanHelper;
import top.moma.m78.framework.helper.resttemplate.dto.GetEntity;
import top.moma.m78.framework.helper.resttemplate.dto.PostEntity;
import top.moma.m78.framework.helper.resttemplate.handler.CustomErrorHandler;
import top.moma.m78.framework.helper.resttemplate.handler.CustomLogHandler;

/**
 * RestTemplateHelper
 *
 * <p>TODO doPut,doDelete
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/15/19 - 4:35 PM.
 */
@Slf4j
public class RestTemplateHelper {

  public static String doGet(final GetEntity getEntity) {
    return doGet(getEntity, String.class);
  }

  public static <T> T doGet(final GetEntity getEntity, final Class<T> responseType) {
    TypeHelper.notNull(getEntity.getUrl(), "GET URL");
    TypeHelper.notNull(responseType, "Response Type");
    RestTemplate restTemplate =
        new RestTemplateBuilder()
            .additionalInterceptors(new CustomLogHandler())
            .errorHandler(new CustomErrorHandler())
            .setConnectTimeout(Duration.ofSeconds(HttpConstants.CONNECTION_TIMEOUT))
            .setReadTimeout(Duration.ofSeconds(HttpConstants.READ_TIMEOUT))
            .build();
    HttpHeaders httpHeaders = formHeader(getEntity.getHeaderMap(), false);
    String getUrl =
        expandUrl(
            getEntity.getUrl(),
            getEntity.getRequestObject(),
            getEntity.getJsonNamingStrategyEnum());
    return exchange(
        getUrl,
        HttpMethod.GET,
        responseType,
        httpHeaders,
        null,
        new HashMap<>(0),
        false,
        getEntity.getJsonNamingStrategyEnum());
  }

  public static String doPost(final PostEntity postEntity) {
    return doPost(postEntity, String.class);
  }

  public static <T> T doPost(final PostEntity postEntity, final Class<T> responseType) {
    TypeHelper.notNull(postEntity.getUrl(), "POST URL");
    TypeHelper.notNull(responseType, "Response Type");
    RestTemplate restTemplate =
        new RestTemplateBuilder()
            .additionalInterceptors(new CustomLogHandler())
            .errorHandler(new CustomErrorHandler())
            .setConnectTimeout(Duration.ofSeconds(HttpConstants.CONNECTION_TIMEOUT))
            .setReadTimeout(Duration.ofSeconds(HttpConstants.READ_TIMEOUT))
            .build();
    HttpHeaders httpHeaders = formHeader(postEntity.getHeaderMap(), postEntity.getFormBody());
    if (postEntity.getFormBody()) {
      HttpEntity<LinkedMultiValueMap<String, String>> formEntity =
          buildFormEntity(
              postEntity.getRequestObject(), httpHeaders, postEntity.getJsonNamingStrategyEnum());
      return restTemplate.postForObject(postEntity.getUrl(), formEntity, responseType);
    } else {
      HttpEntity<String> jsonEntity =
          buildJsonEntity(
              postEntity.getRequestObject(), httpHeaders, postEntity.getJsonNamingStrategyEnum());
      return restTemplate.postForObject(postEntity.getUrl(), jsonEntity, responseType);
    }
  }

  private static <T> T exchange(
      String url,
      HttpMethod method,
      Class<T> responseClass,
      HttpHeaders httpHeaders,
      Object requestObject,
      Map<String, String> urlVariables,
      Boolean formBody,
      JsonNamingStrategyEnum jsonNameEnum) {
    RestTemplate restTemplate =
        new RestTemplateBuilder()
            .additionalInterceptors(new CustomLogHandler())
            .errorHandler(new CustomErrorHandler())
            .setConnectTimeout(Duration.ofSeconds(HttpConstants.CONNECTION_TIMEOUT))
            .setReadTimeout(Duration.ofSeconds(HttpConstants.READ_TIMEOUT))
            .build();
    if (formBody) {
      HttpEntity<LinkedMultiValueMap<String, String>> formEntity =
          buildFormEntity(requestObject, httpHeaders, jsonNameEnum);
      ResponseEntity<T> resultEntity =
          restTemplate.exchange(url, method, formEntity, responseClass, urlVariables);
      return resultEntity.getBody();
    } else {
      HttpEntity<String> requestEntity = buildJsonEntity(requestObject, httpHeaders, jsonNameEnum);
      ResponseEntity<T> resultEntity =
          restTemplate.exchange(url, method, requestEntity, responseClass, urlVariables);
      return resultEntity.getBody();
    }
  }

  private static HttpHeaders formHeader(Map<String, String> headerMap, Boolean isFormBody) {
    HttpHeaders httpHeaders = new HttpHeaders();
    if (headerMap != null) {
      Set<String> keys = headerMap.keySet();
      for (String key : keys) {
        httpHeaders.add(key, headerMap.get(key));
      }
    }
    if (isFormBody) {
      httpHeaders.setContentType(MediaType.APPLICATION_FORM_URLENCODED);
    } else {
      httpHeaders.setContentType(MediaType.APPLICATION_JSON_UTF8);
    }
    httpHeaders.add("Accept", MediaType.APPLICATION_JSON.toString());
    return httpHeaders;
  }

  private static HttpEntity<String> buildJsonEntity(
      Object requestObject,
      HttpHeaders httpHeaders,
      JsonNamingStrategyEnum jsonNamingStrategyEnum) {
    String jsonData = "";
    if (Objects.nonNull(requestObject)) {
      if (jsonNamingStrategyEnum.equals(JsonNamingStrategyEnum.LOWER_CAMEL_CASE)) {
        jsonData = JacksonHelper.toCamelJson(requestObject);
      } else if (jsonNamingStrategyEnum.equals(JsonNamingStrategyEnum.SNAKE_CASE)) {
        jsonData = JacksonHelper.toSnakeJson(requestObject);
      } else {
        jsonData = JacksonHelper.toJson(requestObject);
      }
    }
    return new HttpEntity<>(jsonData, httpHeaders);
  }

  @SuppressWarnings(value = "unchecked")
  private static HttpEntity<LinkedMultiValueMap<String, String>> buildFormEntity(
      Object requestObject, HttpHeaders httpHeaders, JsonNamingStrategyEnum jsonNameEnum) {
    LinkedMultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
    if (Objects.nonNull(requestObject)) {
      if (JsonNamingStrategyEnum.LOWER_CAMEL_CASE.equals(jsonNameEnum)) {
        Map<String, String> requestMap =
            JacksonHelper.readValue(JacksonHelper.toCamelJson(requestObject), Map.class);
        requestParams.setAll(requestMap);
      } else if (JsonNamingStrategyEnum.SNAKE_CASE.equals(jsonNameEnum)) {
        Map<String, String> requestMap =
            JacksonHelper.readValue(JacksonHelper.toSnakeJson(requestObject), Map.class);
        requestParams.setAll(requestMap);
      } else {
        Map<String, String> requestMap = BeanHelper.beanToStringMap(requestObject);
        requestParams.setAll(requestMap);
      }
    }
    return new HttpEntity<>(requestParams, httpHeaders);
  }

  @SuppressWarnings(value = "unchecked")
  private static String expandUrl(
      String url, Object requestObject, JsonNamingStrategyEnum jsonNameEnum) {
    MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
    if (Objects.nonNull(requestObject)) {
      if (JsonNamingStrategyEnum.LOWER_CAMEL_CASE.equals(jsonNameEnum)) {
        Map<String, String> urlParameters =
            JacksonHelper.readValue(JacksonHelper.toCamelJson(requestObject), Map.class);
        params.setAll(urlParameters);
      } else if (JsonNamingStrategyEnum.SNAKE_CASE.equals(jsonNameEnum)) {
        Map<String, String> urlParameters =
            JacksonHelper.readValue(JacksonHelper.toSnakeJson(requestObject), Map.class);
        params.setAll(urlParameters);
      } else {
        Map<String, String> urlParameters = BeanHelper.beanToStringMap(requestObject);
        params.setAll(urlParameters);
      }
    }
    url = UriComponentsBuilder.fromHttpUrl(url).queryParams(params).build().toUriString();
    return url;
  }
}
