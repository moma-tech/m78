package top.moma.m78.framework.interception.filter;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import org.bson.types.ObjectId;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.util.UrlPathHelper;
import top.moma.m78.framework.constants.ApiConstants;
import top.moma.m78.framework.wrapper.RequestWrapper;

/**
 * RequestFilter
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:56 PM.
 */
public class RequestFilter implements Filter {
  @Autowired private UrlPathHelper urlPathHelper;

  @Override
  public void init(FilterConfig filterConfig) throws ServletException {}

  @Override
  public void doFilter(
      ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
      throws IOException, ServletException {

    servletRequest = new RequestWrapper((HttpServletRequest) servletRequest);

    HttpServletRequest httpRequest = (HttpServletRequest) (servletRequest);
    String requestId = ObjectId.get().toString();
    MDC.put(ApiConstants.REQUEST_ID, requestId);
    servletRequest.setAttribute(ApiConstants.REQUEST_ID, requestId);
    servletRequest.setAttribute(ApiConstants.REQUEST_START_TIME, System.currentTimeMillis());
    String method = httpRequest.getMethod();
    String requestUri = urlPathHelper.getOriginatingRequestUri(httpRequest);
    servletRequest.setAttribute(ApiConstants.REQUEST_URL, requestUri);
    servletRequest.setAttribute(ApiConstants.REQUEST_METHOD, method);

    filterChain.doFilter(servletRequest, servletResponse);
  }

  @Override
  public void destroy() {
    MDC.remove(ApiConstants.REQUEST_ID);
  }
}
