package top.moma.m78.framework.common.model.entity;

import top.moma.m78.framework.helper.json.JacksonHelper;
import top.moma.m78.framework.helper.modelmapper.BeanHelper;

/**
 * SuperModel
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:01 PM.
 */
public class SuperModel implements java.io.Serializable {

  private static final long serialVersionUID = -788526133425981376L;

  public <T> T beanToBean(Class<T> targetClass) {
    return BeanHelper.beanToBean(this, targetClass);
  }

  public String presentJsonData() {
    return JacksonHelper.toJson(this);
  }
}
