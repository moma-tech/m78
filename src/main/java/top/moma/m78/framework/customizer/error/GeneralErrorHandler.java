package top.moma.m78.framework.customizer.error;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.web.bind.annotation.RequestMapping;
import top.moma.m78.framework.constants.enumeration.HttpStatusCodeEnum;
import top.moma.m78.framework.helper.ResponseHelper;

/**
 * GeneralErrorHandler
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:20 PM.
 */
public class GeneralErrorHandler implements ErrorController {
  @Override
  public String getErrorPath() {
    return "error";
  }

  @RequestMapping
  public void error(HttpServletRequest request, HttpServletResponse response) {
    HttpStatusCodeEnum errorCode;
    switch (response.getStatus()) {
      case HttpServletResponse.SC_BAD_REQUEST:
        errorCode = HttpStatusCodeEnum.BAD_REQUEST;
        break;
      case HttpServletResponse.SC_UNAUTHORIZED:
        errorCode = HttpStatusCodeEnum.UNAUTHORIZED;
        break;
      case HttpServletResponse.SC_FORBIDDEN:
        errorCode = HttpStatusCodeEnum.FORBIDDEN;
        break;
      case HttpServletResponse.SC_NOT_FOUND:
        errorCode = HttpStatusCodeEnum.NOT_FOUND;
        break;
      case HttpServletResponse.SC_METHOD_NOT_ALLOWED:
        errorCode = HttpStatusCodeEnum.METHOD_NOT_ALLOWED;
        break;
      case HttpServletResponse.SC_NOT_ACCEPTABLE:
        errorCode = HttpStatusCodeEnum.NOT_ACCEPTABLE;
        break;
      case HttpServletResponse.SC_LENGTH_REQUIRED:
        errorCode = HttpStatusCodeEnum.LENGTH_REQUIRED;
        break;
      case HttpServletResponse.SC_UNSUPPORTED_MEDIA_TYPE:
        errorCode = HttpStatusCodeEnum.UNSUPPORTED_MEDIA_TYPE;
        break;
      case HttpServletResponse.SC_REQUESTED_RANGE_NOT_SATISFIABLE:
        errorCode = HttpStatusCodeEnum.REQUESTED_RANGE_NOT_SATISFIABLE;
        break;
      case HttpServletResponse.SC_SERVICE_UNAVAILABLE:
        errorCode = HttpStatusCodeEnum.SERVICE_UNAVAILABLE;
        break;
      default:
        errorCode = HttpStatusCodeEnum.INTERNAL_SERVER_ERROR;
    }
    ResponseHelper.response(request, response, errorCode);
  }
}
