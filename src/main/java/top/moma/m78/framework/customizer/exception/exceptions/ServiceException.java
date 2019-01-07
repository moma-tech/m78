package top.moma.m78.framework.customizer.exception.exceptions;

import top.moma.m78.framework.common.model.pojo.ResponseStatusInfo;
import top.moma.m78.framework.constants.enumeration.ApiStatusCodeEnum;

/**
 * ServiceException
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:45 PM.
 */
public class ServiceException extends RuntimeException {

  private static final long serialVersionUID = 3244443525199380669L;

  private final ResponseStatusInfo responseStatusInfo;

  public ServiceException(ApiStatusCodeEnum errorCodeEnum) {
    super(errorCodeEnum.msg());
    this.responseStatusInfo = errorCodeEnum.transform();
  }

  public ServiceException(ResponseStatusInfo responseStatusInfo) {
    super(responseStatusInfo.getMsg());
    this.responseStatusInfo = responseStatusInfo;
  }

  public ResponseStatusInfo getResponseStatusInfo() {
    return responseStatusInfo;
  }
}
