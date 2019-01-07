package top.moma.m78.framework.customizer.server;

import io.undertow.connector.ByteBufferPool;
import io.undertow.server.DefaultByteBufferPool;
import io.undertow.websockets.jsr.WebSocketDeploymentInfo;
import java.io.IOException;
import org.springframework.boot.web.embedded.undertow.UndertowDeploymentInfoCustomizer;
import org.springframework.boot.web.embedded.undertow.UndertowServletWebServerFactory;
import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.xnio.OptionMap;
import org.xnio.Options;
import org.xnio.Xnio;
import org.xnio.XnioWorker;

/**
 * UndertowServerFactoryCustomizer
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:53 PM.
 */
public class UndertowServerFactoryCustomizer
    implements WebServerFactoryCustomizer<UndertowServletWebServerFactory> {
  @Override
  public void customize(UndertowServletWebServerFactory factory) {
    UndertowDeploymentInfoCustomizer undertowDeploymentInfoCustomizer =
        deploymentInfo -> {
          WebSocketDeploymentInfo info =
              (WebSocketDeploymentInfo)
                  deploymentInfo
                      .getServletContextAttributes()
                      .get(WebSocketDeploymentInfo.ATTRIBUTE_NAME);
          XnioWorker worker = getXnioWorker();
          ByteBufferPool buffers =
              new DefaultByteBufferPool(
                  Boolean.getBoolean("io.undertow.websockets.direct-buffers"), 1024, 100, 12);
          info.setWorker(worker);
          info.setBuffers(buffers);
        };
    factory.addDeploymentInfoCustomizers(undertowDeploymentInfoCustomizer);
  }

  /** 获取XnioWorker */
  private XnioWorker getXnioWorker() {
    XnioWorker worker = null;
    try {
      worker = Xnio.getInstance().createWorker(OptionMap.create(Options.THREAD_DAEMON, true));
    } catch (IOException ignored) {
    }
    return worker;
  }
}
