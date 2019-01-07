package top.moma.m78.framework.customizer.exception.exceptions;

/**
 * M78Exception
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:06 PM.
 */
public class M78Exception extends RuntimeException {
  private static final long serialVersionUID = 8188554792053824006L;

  public M78Exception(String message) {
    super(message);
  }

  public M78Exception(Throwable throwable) {
    super(throwable);
  }

  public M78Exception(String message, Throwable throwable) {
    super(message, throwable);
  }
}
