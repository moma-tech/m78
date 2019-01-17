package top.moma.m78.framework.helper.resttemplate.dto;

import java.util.Map;
import top.moma.m78.framework.constants.enumeration.JsonNamingStrategyEnum;

/**
 * GetEntity
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/17/19 - 10:43 AM.
 */
public class GetEntity {
  final String url;
  final Object requestObject;
  final Map<String, String> headerMap;
  final Boolean formBody;
  final JsonNamingStrategyEnum jsonNamingStrategyEnum;

  public String getUrl() {
    return url;
  }

  public Object getRequestObject() {
    return requestObject;
  }

  public Map<String, String> getHeaderMap() {
    return headerMap;
  }

  public Boolean getFormBody() {
    return formBody;
  }

  public JsonNamingStrategyEnum getJsonNamingStrategyEnum() {
    return jsonNamingStrategyEnum;
  }

  public GetEntity(Builder builder) {
    this.url = builder.url;
    this.requestObject = builder.requestObject;
    this.headerMap = builder.headerMap;
    this.formBody = builder.formBody;
    this.jsonNamingStrategyEnum = builder.jsonNamingStrategyEnum;
  }

  public static final class Builder {
    String url;
    Object requestObject;
    Map<String, String> headerMap;
    Boolean formBody;
    JsonNamingStrategyEnum jsonNamingStrategyEnum;

    public Builder() {
      this.headerMap = null;
      this.formBody = false;
    }

    public Builder url(String url) {
      this.url = url;
      return this;
    }

    public Builder requestObject(Object requestObject) {
      this.requestObject = requestObject;
      return this;
    }

    public Builder headerMap(Map<String, String> headerMap) {
      this.headerMap = headerMap;
      return this;
    }

    public Builder isFormBody(Boolean formBody) {
      this.formBody = formBody;
      return this;
    }

    public Builder jsonNamingStrategyEnum(JsonNamingStrategyEnum jsonNamingStrategyEnum) {
      this.jsonNamingStrategyEnum = jsonNamingStrategyEnum;
      return this;
    }

    public GetEntity build() {
      return new GetEntity(this);
    }
  }
}
