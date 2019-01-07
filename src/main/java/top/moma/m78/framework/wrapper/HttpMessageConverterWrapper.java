package top.moma.m78.framework.wrapper;

import java.util.function.Consumer;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import top.moma.m78.framework.helper.json.JacksonHelper;

/**
 * HttpMessageConverterWrapper
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:59 PM.
 */
public class HttpMessageConverterWrapper {
  public static Consumer<HttpMessageConverter<?>> objectMapperWrapper() {
    return converter -> {
      if (converter instanceof MappingJackson2HttpMessageConverter) {
        MappingJackson2HttpMessageConverter httpMessageConverter =
            (MappingJackson2HttpMessageConverter) converter;
        JacksonHelper.getObjectMapper(httpMessageConverter.getObjectMapper());
      }
    };
  }
}
