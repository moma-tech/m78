package top.moma.m78.framework.response.domain;

import com.fasterxml.jackson.databind.JsonMappingException.Reference;
import com.fasterxml.jackson.databind.exc.MismatchedInputException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.ConstraintViolationException;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingPathVariableException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import top.moma.m78.framework.common.model.pojo.ResponseStatusInfo;
import top.moma.m78.framework.constants.ApiConstants;
import top.moma.m78.framework.response.Response;

/**
 * FailedResponse
 *
 * <p>TODO
 *
 * @author ivan
 * @version 1.0 Created by ivan on 1/7/19 - 4:33 PM.
 */
@Getter
@ToString
@Builder
@EqualsAndHashCode(callSuper = false)
public class FailedResponse extends Response implements java.io.Serializable {

  private static final long serialVersionUID = -5563634937142514154L;

  /** Response Code */
  private Integer code;
  /** Response Message */
  private String msg;
  /** Response Error Message */
  private String errorMsg;
  /** Response Extra Info */
  private String info;
  /** Response Time */
  private LocalDateTime time;

  public static FailedResponse buildFailedResponse(
      HttpServletRequest request, Exception exception, ResponseStatusInfo responseStatusInfo) {
    FailedResponse.FailedResponseBuilder builder = FailedResponse.builder();
    if (Objects.nonNull(responseStatusInfo)) {
      builder.code(responseStatusInfo.getCode()).msg(responseStatusInfo.getMsg());
    }
    if (Objects.nonNull(exception)) {
      builder.errorMsg(formatException(exception));
    }
    if (Objects.nonNull(request)) {
      builder.info((String) request.getAttribute(ApiConstants.REQUEST_ID));
    }
    builder.time(LocalDateTime.now());
    return builder.build();
  }

  public static String formatException(Exception exception) {
    if (null == exception) {
      return null;
    } else if (exception instanceof MethodArgumentNotValidException) {
      StringBuilder builder = new StringBuilder("校验失败:");
      List<ObjectError> allErrors =
          ((MethodArgumentNotValidException) exception).getBindingResult().getAllErrors();
      allErrors
          .stream()
          .findFirst()
          .ifPresent(
              error -> {
                builder
                    .append(((FieldError) error).getField())
                    .append(" 字段规则为 ")
                    .append(error.getDefaultMessage());
              });
      return builder.toString();
    } else if (exception instanceof MissingServletRequestParameterException) {
      StringBuilder builder = new StringBuilder("参数字段");
      MissingServletRequestParameterException ex =
          (MissingServletRequestParameterException) exception;
      builder.append(ex.getParameterName());
      builder.append("校验不通过");
      return builder.toString();
    } else if (exception instanceof MissingPathVariableException) {
      StringBuilder builder = new StringBuilder("路径字段");
      MissingPathVariableException ex = (MissingPathVariableException) exception;
      builder.append(ex.getVariableName());
      builder.append("校验不通过");
      return builder.toString();
    } else if (exception instanceof ConstraintViolationException) {
      StringBuilder builder = new StringBuilder("方法.参数字段");
      ConstraintViolationException ex = (ConstraintViolationException) exception;
      Optional<ConstraintViolation<?>> first = ex.getConstraintViolations().stream().findFirst();
      if (first.isPresent()) {
        ConstraintViolation<?> constraintViolation = first.get();
        builder.append(constraintViolation.getPropertyPath().toString());
        builder.append("校验不通过");
      }
      return builder.toString();
    } else if (exception instanceof HttpMessageNotReadableException) {
      if (exception.getCause() instanceof MismatchedInputException) {
        StringBuilder builder = new StringBuilder("参数字段");
        MismatchedInputException ex = ((MismatchedInputException) exception.getCause());
        List<Reference> referenceList = ex.getPath();
        referenceList
            .stream()
            .findFirst()
            .ifPresent(
                reference -> {
                  builder.append(reference.getFieldName());
                });
        builder.append(" 期望类型: ").append(ex.getTargetType());
        return builder.toString();
      }
    }
    return exception.toString();
  }
}
