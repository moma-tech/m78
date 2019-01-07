package top.moma.m78.framework.response.domain;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;
import top.moma.m78.framework.response.Response;

/**
 * SuccessResponse
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:35 PM.
 */
@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(callSuper = false)
public class SuccessResponse<T> extends Response<T> implements java.io.Serializable {

  private static final long serialVersionUID = -6642099619348076686L;

  /** Response Code */
  private Integer code;
  /** Response Message */
  private String msg;
  /** Response Object */
  private T result;
}
