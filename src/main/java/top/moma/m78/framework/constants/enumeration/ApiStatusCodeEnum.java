package top.moma.m78.framework.constants.enumeration;

import top.moma.m78.framework.common.model.pojo.ResponseStatusInfo;

/**
 * ApiStatusCodeEnum
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:10 PM.
 */
public enum ApiStatusCodeEnum {
  /** 996 */
  UNKNOWN(996, "未知错误"),
  /** 501 */
  UNKOWN_API_TYPE(501, "未知的接口类型"),
  /* 6XX Signature */
  /** 600 */
  UN_SIGN(600, "sign签名未传递"),
  /** 601 */
  SIGN_ERROR(601, "sign签名不正确"),
  /** 602 */
  UN_TIMESTAMP(602, "timestamp时间戳未传递"),
  /** 603 */
  UN_DATA(603, "data数据未传递"),
  /** 604 */
  UN_VALID_TIMESTAMP(604, "timestamp失效"),
  ;
  /** Code */
  private int code;
  /** Message */
  private String msg;

  ApiStatusCodeEnum(int code, String msg) {
    this.code = code;
    this.msg = msg;
  }

  public int code() {
    return this.code;
  }

  public String msg() {
    return this.msg;
  }

  public ResponseStatusInfo transform() {
    return ResponseStatusInfo.builder().code(code()).msg(msg()).build();
  }
}
