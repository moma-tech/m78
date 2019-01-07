package top.moma.m78.framework.constants;

/**
 * ApiConstants
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:17 PM.
 */
public interface ApiConstants {
  /* Token Ref. */
  /** Claim Key */
  String CLAIM_KEY = "claimKey";
  /** Access Token */
  String ACCESS_TOKEN = "access_token";
  /** Token RSA Key */
  String TOKEN_SECRET = "1s6U65P4bAay14bMDgHWgtqaTHNTZPZNMDJu3k";

  /* Request Ref. */
  /** Request Start Time */
  String REQUEST_START_TIME = "startTime";
  /** Request Id */
  String REQUEST_ID = "requestId";
  /** Requset URL */
  String REQUEST_URL = "url";
  /** Request Method */
  String REQUEST_METHOD = "method";
  /** Request Mapping */
  String REQUEST_MEPPING = "mapping";

  /* Reponse Ref. */
  /** Service Response Code Success */
  Integer SUCCESS = 0;
}
