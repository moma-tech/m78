package top.moma.m78.framework.common.model.pojo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * ResponseStatusInfo
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:11 PM.
 */
@Data
@AllArgsConstructor
@Builder
public class ResponseStatusInfo {
  /** Code */
  private int code;
  /** Message */
  private String msg;
}
