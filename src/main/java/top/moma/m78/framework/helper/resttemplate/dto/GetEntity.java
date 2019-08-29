package top.moma.m78.framework.helper.resttemplate.dto;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import top.moma.m78.framework.constants.enumeration.JsonNamingStrategyEnum;

/**
 * GetEntity
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/17/19 - 10:43 AM.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GetEntity {
  private String url;
  private Object requestObject;
  private Map<String, String> headerMap;
  private Map<String, String> urlVariables;
  private Boolean formBody;
  private JsonNamingStrategyEnum jsonNamingStrategyEnum;
}
