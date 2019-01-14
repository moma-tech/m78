package top.moma.m78.framework.response;

import top.moma.m78.framework.common.model.pojo.ResponseStatusInfo;
import top.moma.m78.framework.constants.enumeration.HttpStatusCodeEnum;
import top.moma.m78.framework.response.domain.FailedResponse;
import top.moma.m78.framework.response.domain.SuccessResponse;

/**
 * Response
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:30 PM.
 */
public class Response<T> implements java.io.Serializable {

  private static final long serialVersionUID = 8754120025010526679L;

  public static <T> Response<T> success(T result) {
    return SuccessResponse.<T>builder()
        .code(HttpStatusCodeEnum.OK.code())
        .msg(HttpStatusCodeEnum.OK.msg())
        .result(result)
        .build();
  }

  public static Response<?> fail(Exception exception, ResponseStatusInfo responseStatusInfo) {
    return FailedResponse.buildFailedResponse(null, exception, responseStatusInfo);
  }
}
