package top.moma.m78.framework.helper.resttemplate.handler;

import static java.nio.charset.StandardCharsets.UTF_8;
import static org.springframework.util.StreamUtils.copyToByteArray;

import java.io.IOException;
import java.nio.charset.Charset;
import java.util.Optional;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.http.HttpMessage;
import org.springframework.http.HttpRequest;
import org.springframework.http.MediaType;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import top.moma.m78.framework.constants.ApiConstants;

/**
 * CustomLogHandler
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/16/19 - 11:16 AM.
 */
@Slf4j
public class CustomLogHandler implements ClientHttpRequestInterceptor {
  private static final Charset DEFAULT_CHARSET = UTF_8;

  /**
   * @param request :
   * @param body :
   * @param charset :
   * @author Created by ivan on 下午3:40 18-11-29.
   *     <p>//logRequest
   */
  private static long logRequest(HttpRequest request, byte[] body, Charset charset) {
    long startTime = System.currentTimeMillis();
    log.debug(
        "===========================request begin================================================");
    log.debug("REF.ID      : {}", MDC.get(ApiConstants.REQUEST_ID));
    log.debug("URI         : {}", request.getURI());
    log.debug("Method      : {}", request.getMethod());
    log.debug("Headers     : {}", request.getHeaders());
    log.debug("Request body: {}", new String(body, charset));
    log.debug("Start Time  : {}", startTime);
    log.debug(
        "===========================request end================================================");
    return startTime;
  }

  /**
   * @param response :
   * @param charset :
   * @author Created by ivan on 下午3:40 18-11-29.
   *     <p>// logResponse
   */
  private static void logResponse(ClientHttpResponse response, Charset charset, long startTime)
      throws IOException {
    long finishTime = System.currentTimeMillis();
    long costTime = finishTime - startTime;
    log.debug(
        "===========================response begin==========================================");
    log.debug("REF.ID       : {}", MDC.get(ApiConstants.REQUEST_ID));
    log.debug("Status code  : {}", response.getStatusCode());
    log.debug("Status text  : {}", response.getStatusText());
    log.debug("Headers      : {}", response.getHeaders());
    log.debug("Response body: {}", new String(copyToByteArray(response.getBody()), charset));
    log.debug("Finish Time  : {}", finishTime);
    log.debug("Cost Time    : {}", costTime);
    log.debug(
        "===========================response end=================================================");
  }

  /**
   * @param httpRequest :
   * @param body :
   * @param clientHttpRequestExecution :
   * @return org.springframework.http.client.ClientHttpResponse
   * @author Created by ivan on 下午3:40 18-11-29.
   *     <p>//intercept
   */
  @Override
  public ClientHttpResponse intercept(
      HttpRequest httpRequest, byte[] body, ClientHttpRequestExecution clientHttpRequestExecution)
      throws IOException {
    long startTime = logRequest(httpRequest, body, getCharset(httpRequest));
    ClientHttpResponse httpResponse = clientHttpRequestExecution.execute(httpRequest, body);
    logResponse(httpResponse, getCharset(httpRequest), startTime);
    return httpResponse;
  }

  /**
   * @param message :
   * @return java.nio.charset.Charset
   * @author Created by ivan on 下午3:40 18-11-29.
   *     <p>// getCharset
   */
  private Charset getCharset(HttpMessage message) {
    return Optional.ofNullable(message.getHeaders().getContentType())
        .map(MediaType::getCharset)
        .orElse(DEFAULT_CHARSET);
  }
}
