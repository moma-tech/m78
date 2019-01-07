package top.moma.m78.framework.wrapper;

import com.google.common.base.Throwables;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.MimeTypeUtils;
import top.moma.m78.framework.common.model.pojo.ResponseStatusInfo;
import top.moma.m78.framework.helper.json.JacksonHelper;

/**
 * ResponseWrapper
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:23 PM.
 */
@Slf4j
public class ResponseWrapper extends HttpServletResponseWrapper {
  private ResponseStatusInfo responseStatusInfo;

  public ResponseWrapper(HttpServletResponse response) {
    super(response);
  }

  public ResponseWrapper(HttpServletResponse response, ResponseStatusInfo responseStatusInfo) {
    super(response);
  }

  public void writeJsonResponse(Object obj) {
    if (super.isCommitted()) {
      log.warn("Response is commit");
    } else {
      super.setContentType(MimeTypeUtils.APPLICATION_JSON_VALUE);
      super.setCharacterEncoding(StandardCharsets.UTF_8.name());
      try (PrintWriter writer = super.getWriter()) {
        writer.print(JacksonHelper.toJson(obj));
        writer.flush();
      } catch (IOException e) {
        log.warn(
            "Error: Response print Json failed, stackTrace: {}",
            Throwables.getStackTraceAsString(e));
      }
    }
  }
}
