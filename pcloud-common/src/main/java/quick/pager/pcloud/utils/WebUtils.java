package quick.pager.pcloud.utils;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * web request 工具类
 *
 * @author siguiyang
 */
public class WebUtils {

    /**
     * 获取当前人登录的手机号
     *
     * @return 登录的手机号
     */
    public static HttpServletRequest getRequest() {
        return ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()).getRequest();
    }

    /**
     * 获取当前人登录的手机号
     *
     * @return 登录的手机号
     */
    public static String getPhone() {
        return getRequest().getHeader("phone");
    }


    /**
     * 获取当前人登录人名称
     *
     * @return 登录人名称
     */
    public static String getName() {
        return getRequest().getHeader("name");
    }
}
