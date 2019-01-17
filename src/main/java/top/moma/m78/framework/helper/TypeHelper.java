package top.moma.m78.framework.helper;

import java.util.Objects;
import top.moma.m78.framework.customizer.exception.exceptions.M78Exception;

/**
 * TypeHelper
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:03 PM.
 */
public class TypeHelper {
  public static String castToString(Object object) {
    if (Objects.isNull(object)) {
      return null;
    } else {
      return object.toString();
    }
  }

  public static <T> T notNull(final T object, final String message) {
    if (object == null) {
      throw new M78Exception("Argument Can not be null. " + message);
    }
    return object;
  }
}
