package top.moma.m78.framework.helper.resttemplate.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.moma.m78.framework.constants.enumeration.JsonNamingStrategyEnum;

/**
 * PostEntity
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/16/19 - 2:05 PM.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PostEntity {
  private String url;
  private Object requestObject;
  private Map<String, String> headerMap;
  private Boolean formBody;
  private JsonNamingStrategyEnum jsonNamingStrategyEnum;
}
