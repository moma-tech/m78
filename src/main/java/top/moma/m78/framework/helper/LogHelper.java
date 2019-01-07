package top.moma.m78.framework.helper;

import java.util.Map;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import top.moma.m78.framework.common.model.pojo.RequestLogInfo;
import top.moma.m78.framework.helper.json.JacksonHelper;
import top.moma.m78.framework.helper.springcontext.ContextHelper;

/**
 * LogHelper
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:25 PM.
 */
@Slf4j
public class LogHelper {
  public static void printRequestLogInfo(
      String requestId,
      Map<String, String[]> parameterMap,
      String requestBody,
      String url,
      String mapping,
      String method,
      Object result,
      Long runTime,
      String ip,
      String appid) {

    RequestLogInfo requestLogInfo =
        RequestLogInfo.builder()
            .requestId(requestId)
            .ip(ip)
            .mapping(mapping)
            .method(method)
            .parameterMap(parameterMap)
            .requestBody(Optional.ofNullable(JacksonHelper.parse(requestBody)).orElse(requestBody))
            .result(result)
            .runTime((null != runTime ? System.currentTimeMillis() - runTime : 0) + " ms")
            .url(url)
            .appid(appid)
            .build();

    log.info(JacksonHelper.toJson(requestLogInfo));
  }

  public static void doAfterReturning(Object result) {
    ResponseHelper.response(ContextHelper.getRequest(), null, result);
  }
}
