package top.moma.m78.framework.customizer.exception.exceptions;

import top.moma.m78.framework.common.model.pojo.ResponseStatusInfo;
import top.moma.m78.framework.constants.enumeration.HttpStatusCodeEnum;

/**
 * ApiException
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:42 PM.
 */
public class ApiException extends RuntimeException {

  private static final long serialVersionUID = -8032242502958555660L;

  private final ResponseStatusInfo responseStatusInfo;

  public ApiException(HttpStatusCodeEnum errorCodeEnum) {
    super(errorCodeEnum.msg());
    this.responseStatusInfo = errorCodeEnum.transform();
  }

  public ApiException(ResponseStatusInfo responseStatusInfo) {
    super(responseStatusInfo.getMsg());
    this.responseStatusInfo = responseStatusInfo;
  }

  public ResponseStatusInfo getResponseStatusInfo() {
    return responseStatusInfo;
  }
}
