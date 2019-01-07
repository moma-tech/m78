package top.moma.m78.framework.helper.springcontext;

import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

/**
 * ApplicationContextRegister
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:26 PM.
 */
public class ApplicationContextRegister implements ApplicationContextAware {
  private static ApplicationContext APPLICATION_CONTEXT;

  static ApplicationContext getApplicationContext() {
    return APPLICATION_CONTEXT;
  }

  /**
   * 设置spring上下文
   *
   * @param applicationContext spring上下文
   */
  @Override
  public void setApplicationContext(ApplicationContext applicationContext) {
    APPLICATION_CONTEXT = applicationContext;
  }
}
