package quick.pager.pcloud.exception;


import org.springframework.security.oauth2.common.exceptions.InvalidClientException;
import org.springframework.security.oauth2.common.exceptions.InvalidGrantException;
import org.springframework.security.oauth2.common.exceptions.InvalidRequestException;
import org.springframework.security.oauth2.common.exceptions.InvalidScopeException;
import org.springframework.security.oauth2.common.exceptions.InvalidTokenException;
import org.springframework.security.oauth2.common.exceptions.OAuth2Exception;
import org.springframework.security.oauth2.common.exceptions.RedirectMismatchException;
import org.springframework.security.oauth2.common.exceptions.UnauthorizedClientException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedGrantTypeException;
import org.springframework.security.oauth2.common.exceptions.UnsupportedResponseTypeException;
import org.springframework.security.oauth2.common.exceptions.UserDeniedAuthorizationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import quick.pager.pcloud.model.response.ResponseResult;

/**
 * 统一异常处理
 *
 * @author siguiyang
 */
@RestControllerAdvice
public class PCloudAdvice {


    @ExceptionHandler(OAuth2Exception.class)
    public ResponseResult<String> oauth(OAuth2Exception e) {
        ResponseResult<String> result;
        if (e instanceof InvalidClientException) {
            result = ResponseResult.toError("用户名或这密码错误");
        } else if (e instanceof UnauthorizedClientException) {
            result = ResponseResult.toError("未授权的ClientId");
        } else if (e instanceof InvalidGrantException) {
            result = ResponseResult.toError("授权失败，用户名或者密码错误");
        } else if (e instanceof InvalidScopeException) {
            result = ResponseResult.toError("授权客户端错误");
        } else if (e instanceof InvalidTokenException) {
            result = ResponseResult.toError("授权token错误");
        } else if (e instanceof InvalidRequestException) {
            result = ResponseResult.toError("授权请求错误");
        } else if (e instanceof RedirectMismatchException) {
            result = ResponseResult.toError("redirect_uri未匹配");
        } else if (e instanceof UnsupportedGrantTypeException) {
            result = ResponseResult.toError("不支持此授权类型");
        } else if (e instanceof UnsupportedResponseTypeException) {
            result = ResponseResult.toError("不支持此类型的授权码");
        } else if (e instanceof UserDeniedAuthorizationException) {
            result = ResponseResult.toError("您没有访问权限");
        } else {
            result = ResponseResult.toError(e.getMessage());
        }
        return result;
    }
}
