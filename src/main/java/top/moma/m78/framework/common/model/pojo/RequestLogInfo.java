package top.moma.m78.framework.common.model.pojo;

import java.util.Map;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

/**
 * RequestLogInfo
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:08 PM.
 */
@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class RequestLogInfo {
  /** Request Id */
  private String requestId;
  /** Request Parameter */
  private Map<String, String[]> parameterMap;
  /** Request Body */
  private Object requestBody;
  /** Request URL */
  private String url;
  /** Mapping Controller */
  private String mapping;
  /** Mapping Method */
  private String method;
  /** Response Content */
  private Object result;
  /** Escape Time */
  private String runTime;
  /** Income/User IP */
  private String ip;
  /** Session/User ID/Company ID */
  private String appid;

  public RequestLogInfo(
      String requestId,
      Map<String, String[]> parameterMap,
      Object requestBody,
      String url,
      String mapping,
      String method,
      Object result,
      String runTime,
      String ip,
      String appid) {
    this.requestId = requestId;
    this.parameterMap = parameterMap;
    this.requestBody = requestBody;
    this.url = url;
    this.mapping = mapping;
    this.method = method;
    this.result = result;
    this.runTime = runTime;
    this.ip = ip;
    this.appid = appid;
  }
}
