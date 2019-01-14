package top.moma.m78.framework.helper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.StreamUtils;
import top.moma.m78.framework.constants.ApiConstants;
import top.moma.m78.framework.constants.HttpConstants;
import top.moma.m78.framework.constants.M78Constants;
import top.moma.m78.framework.constants.enumeration.HttpMethodEnum;
import top.moma.m78.framework.helper.json.JacksonHelper;

/**
 * RequestHelper
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:27 PM.
 */
@Slf4j
public class RequestHelper {

  @SuppressWarnings("rawtypes")
  public static String getToken(HttpServletRequest httpRequest) {
    String token = (String) httpRequest.getAttribute(ApiConstants.ACCESS_TOKEN);
    if (StringUtils.isBlank(token)) {
      token = httpRequest.getHeader(M78Constants.AUTHORIZATION_HEADER);
    }
    if (StringUtils.isBlank(token)) {
      token = httpRequest.getParameter(ApiConstants.ACCESS_TOKEN);
    }
    // TODO Access in JSON Body
    if (StringUtils.isBlank(token)) {
      Optional<Map> bodyMap =
          Optional.ofNullable(JacksonHelper.readValue(getRequestBody(httpRequest), HashMap.class));
      token =
          TypeHelper.castToString(bodyMap.map(t -> t.get(ApiConstants.ACCESS_TOKEN)).orElse(null));
    }
    return StringUtils.isBlank(token) ? null : token.replaceFirst("Bearer", "");
  }

  public static String getRequestBody(HttpServletRequest request) {
    String requestBody = null;
    if (isContainsBody(request)) {
      try {
        requestBody = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
      } catch (IOException ignore) {
      }
    }
    return requestBody;
  }

  public static byte[] getByteBody(HttpServletRequest request) {
    byte[] body = new byte[0];
    try {
      body = StreamUtils.copyToByteArray(request.getInputStream());
    } catch (IOException e) {
      log.error("Error: Get RequestBody byte[] fail," + e);
    }
    return body;
  }

  public static boolean isContainsBody(HttpServletRequest request) {
    return isPost(request) || isPut(request) || isPatch(request);
  }

  public static boolean isPost(HttpServletRequest request) {
    return HttpMethodEnum.POST.toString().equalsIgnoreCase(request.getMethod());
  }

  public static boolean isPut(HttpServletRequest request) {
    return HttpMethodEnum.PUT.toString().equalsIgnoreCase(request.getMethod());
  }

  public static boolean isPatch(HttpServletRequest request) {
    return HttpMethodEnum.PATCH.toString().equalsIgnoreCase(request.getMethod());
  }

  public static boolean isGet(HttpServletRequest request) {
    return HttpMethodEnum.GET.toString().equalsIgnoreCase(request.getMethod());
  }

  public static boolean isDelete(HttpServletRequest request) {
    return HttpMethodEnum.DELETE.toString().equalsIgnoreCase(request.getMethod());
  }

  public static boolean isTrace(HttpServletRequest request) {
    return HttpMethodEnum.TRACE.toString().equalsIgnoreCase(request.getMethod());
  }

  public static boolean isHead(HttpServletRequest request) {
    return HttpMethodEnum.HEAD.toString().equalsIgnoreCase(request.getMethod());
  }

  public static boolean isOptions(HttpServletRequest request) {
    return HttpMethodEnum.OPTIONS.toString().equalsIgnoreCase(request.getMethod());
  }

  public static String getIpAddr(HttpServletRequest request) {
    // nginx代理获取的真实用户ip
    String ip = request.getHeader(HttpConstants.X_REAL_IP);
    if (StringUtils.isBlank(ip) || HttpConstants.UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader(HttpConstants.X_FORWARDED_FOR);
    }
    if (StringUtils.isBlank(ip) || HttpConstants.UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader(HttpConstants.PROXY_CLIENT_IP);
    }
    if (StringUtils.isBlank(ip) || HttpConstants.UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getHeader(HttpConstants.WL_PROXY_CLIENT_IP);
    }
    if (StringUtils.isBlank(ip) || HttpConstants.UNKNOWN.equalsIgnoreCase(ip)) {
      ip = request.getRemoteAddr();
    }

    if (null != ip && ip.length() > HttpConstants.IP_MIN_LENGTH) {
      if (ip.indexOf(M78Constants.GLOBE_SPLIT_COMMA) > 0) {
        ip = ip.substring(0, ip.indexOf(M78Constants.GLOBE_SPLIT_COMMA));
      }
    }
    return ip;
  }
}
