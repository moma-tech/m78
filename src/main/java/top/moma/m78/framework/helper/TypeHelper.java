package top.moma.m78.framework.helper;

import java.util.Objects;

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
}
