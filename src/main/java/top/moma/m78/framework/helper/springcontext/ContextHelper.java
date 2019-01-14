package top.moma.m78.framework.helper.springcontext;

import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * ContextHelper
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:26 PM.
 */
public class ContextHelper {
  private static final ApplicationContext APPLICATION_CONTEXT =
      ApplicationContextRegister.getApplicationContext();

  public static Object getBeanObject(String beanName) {
    if (APPLICATION_CONTEXT.containsBean(beanName)) {
      return APPLICATION_CONTEXT.getBean(beanName);
    }
    return null;
  }

  @SuppressWarnings("unchecked")
public static <T> T getBean(String beanName) {
    if (APPLICATION_CONTEXT.containsBean(beanName)) {
      Class<T> beanType = (Class<T>) APPLICATION_CONTEXT.getType(beanName);
      if (Objects.nonNull(beanType)) {
        return APPLICATION_CONTEXT.getBean(beanName, beanType);
      }
    }
    return null;
  }

  public static <T> T getBean(Class<T> beanType) {
    return APPLICATION_CONTEXT.getBean(beanType);
  }

  public static HttpServletRequest getRequest() {
    Optional<ServletRequestAttributes> servletRequestAttributes =
        Optional.ofNullable((ServletRequestAttributes) RequestContextHolder.getRequestAttributes());
    return servletRequestAttributes.map(ServletRequestAttributes::getRequest).orElse(null);
  }
}
