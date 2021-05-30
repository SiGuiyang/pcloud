package quick.pager.pcloud.exception;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.NoHandlerFoundException;
import quick.pager.pcloud.constants.ResponseStatus;
import quick.pager.pcloud.response.ResponseResult;

/**
 * 统一异常处理
 *
 * @author siguiyang
 */
@RestControllerAdvice
@Slf4j
public class PCloudExceptionAdvice {

    @ExceptionHandler(Exception.class)
    public ResponseResult<String> doException(Exception e) {

        log.error("统一异常处理机制，触发异常 msg = {}", e);
        String message;
        if (e instanceof PCloudException) {
            PCloudException exception = (PCloudException) e;
            message = exception.getMessage();
            // PCloudException 特殊处理返回
            return ResponseResult.toError(exception.getCode(), message);
        } else if (e instanceof HttpRequestMethodNotSupportedException) {
            message = "不支持GET/POST方法";
        } else if (e instanceof NoHandlerFoundException) {
            message = "请求接口不存在";
        } else if (e instanceof IllegalArgumentException) {
            IllegalArgumentException exception = (IllegalArgumentException) e;
            message = exception.getMessage();
        } else {
            message = "系统异常";
        }

        return ResponseResult.toError(ResponseStatus.FAIL_CODE, message);
    }
}
