package top.moma.m78.framework.constants;

/**
 * HttpConstants
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:18 PM.
 */
public interface HttpConstants {
  /** Min Ip Length */
  Integer IP_MIN_LENGTH = 15;
  /** Proxy Header Char */
  String UNKNOWN = "unknown";
  /** Proxy Header Char */
  String X_REAL_IP = "X-Real-IP";
  /** Proxy Header Char */
  String X_FORWARDED_FOR = "X-Forwarded-For";
  /** Proxy Header Char */
  String PROXY_CLIENT_IP = "Proxy-Client-IP";
  /** Proxy Header Char */
  String WL_PROXY_CLIENT_IP = "WL-Proxy-Client-IP";
}
