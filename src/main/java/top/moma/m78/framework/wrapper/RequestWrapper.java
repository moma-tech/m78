package top.moma.m78.framework.wrapper;

import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.util.Objects;
import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import org.springframework.util.ObjectUtils;
import top.moma.m78.framework.helper.RequestHelper;

/**
 * RequestWrapper
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:57 PM.
 */
public class RequestWrapper extends HttpServletRequestWrapper {
  /** Copy Body Byte[] */
  private final byte[] requestBody;
  /**
   * @author Created by ivan on 3:37 PM 12/24/18.
   *     <p>//Wrapper
   * @param request :
   */
  public RequestWrapper(HttpServletRequest request) {
    super(request);
    requestBody = RequestHelper.getByteBody(request);
  }
  /**
   * @author Created by ivan on 3:38 PM 12/24/18.
   *     <p>//Get Request Reader
   * @return java.io.BufferedReader
   */
  @Override
  public BufferedReader getReader() {
    ServletInputStream inputStream = getInputStream();
    return Objects.isNull(inputStream)
        ? null
        : new BufferedReader(new InputStreamReader(inputStream));
  }
  /**
   * @author Created by ivan on 3:39 PM 12/24/18.
   *     <p>//Get Request Input Stream
   * @return javax.servlet.ServletInputStream
   */
  @Override
  public ServletInputStream getInputStream() {
    if (ObjectUtils.isEmpty(requestBody)) {
      return null;
    }
    final ByteArrayInputStream bais = new ByteArrayInputStream(requestBody);
    return new ServletInputStream() {

      @Override
      public boolean isFinished() {
        return false;
      }

      @Override
      public boolean isReady() {
        return false;
      }

      @Override
      @SuppressWarnings("EmptyMethod")
      public void setReadListener(ReadListener readListener) {}

      @Override
      public int read() {
        return bais.read();
      }
    };
  }
}
