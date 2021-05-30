package quick.pager.pcloud.constants;

/**
 * 响应码<br />
 * <p>
 *
 * @author siguiyang
 */
public interface ResponseStatus {

    // 错误码
    int FAIL_CODE = 1000;
    // 严重错误码
    int ERROR_CODE = 2000;
    // 异常码
    int EXCEPTION_CODE = 3000;
    // 熔断返回吗
    int FALLBACK_CODE = 5000;
    // 成功码
    int SUCCESS = 200;

    String SUCCESS_MSG = "请求成功";

    String PARAMS_EXCEPTION = "网络出了点小问题";

    String TELNET_EXCEPTION = "网络连接错误，请稍后重试";

    String REPEAT_SUBMIT = "请勿重复提交";

    String USER_PHONE_NOT_EXISTS = "用户不存在";

    String FALLBACK_MSG = "服务熔断";


}
