package top.moma.m78.framework.helper;

import java.util.Objects;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import top.moma.m78.framework.common.model.pojo.ResponseStatusInfo;
import top.moma.m78.framework.constants.ApiConstants;
import top.moma.m78.framework.constants.enumeration.ApiStatusCodeEnum;
import top.moma.m78.framework.constants.enumeration.HttpStatusCodeEnum;
import top.moma.m78.framework.response.domain.FailedResponse;
import top.moma.m78.framework.wrapper.ResponseWrapper;

/**
 * ResponseHelper
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:21 PM.
 */
public class ResponseHelper {

  public static void response(
      HttpServletRequest request,
      HttpServletResponse response,
      ApiStatusCodeEnum apiStatusCodeEnum,
      Exception exception) {
    response(request, response, apiStatusCodeEnum.transform(), exception);
  }

  public static void response(
      HttpServletRequest request,
      HttpServletResponse response,
      HttpStatusCodeEnum httpStatusCodeEnum,
      Exception exception) {
    response(request, response, httpStatusCodeEnum.transform(), exception);
  }

  public static void response(
      HttpServletRequest request,
      HttpServletResponse response,
      HttpStatusCodeEnum httpStatusCodeEnum) {
    response(request, response, httpStatusCodeEnum.transform(), null);
  }

  public static void response(
      HttpServletRequest request,
      HttpServletResponse response,
      ResponseStatusInfo responseStatusInfo) {
    response(request, response, responseStatusInfo, null);
  }

  public static void response(
      HttpServletRequest request,
      HttpServletResponse response,
      ResponseStatusInfo responseStatusInfo,
      Exception exception) {
    response(
        request,
        new ResponseWrapper(response, responseStatusInfo),
        FailedResponse.buildFailedResponse(request, exception, responseStatusInfo));
  }

  public static void response(HttpServletRequest request, ResponseWrapper response, Object result) {
    LogHelper.printRequestLogInfo(
        (String) request.getAttribute(ApiConstants.REQUEST_ID),
        request.getParameterMap(),
        RequestHelper.getRequestBody(request),
        (String) request.getAttribute(ApiConstants.REQUEST_URL),
        (String) request.getAttribute(ApiConstants.REQUEST_MEPPING),
        (String) request.getAttribute(ApiConstants.REQUEST_METHOD),
        result,
        (Long) request.getAttribute(ApiConstants.REQUEST_START_TIME),
        RequestHelper.getIpAddr(request),
        (String) request.getAttribute(ApiConstants.CLAIM_KEY));
    if (Objects.nonNull(response)) {
      response.writeJsonResponse(result);
    }
  }
}
