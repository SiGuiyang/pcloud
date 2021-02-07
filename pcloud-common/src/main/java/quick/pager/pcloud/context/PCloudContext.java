package quick.pager.pcloud.context;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Map;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

/**
 * spring 上下文
 *
 * @author siguiyang
 * @version 1.0.0
 */
@Component
public class PCloudContext implements ApplicationContextAware {

    /**
     * 静态成员Spring 容器上下文
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {

        PCloudContext.applicationContext = applicationContext;
    }

    /**
     * 通过class对象从Spring中获取原对象
     *
     * @param requiredType class 对象
     * @param <T>          泛型
     * @return 实例对象引用
     */
    public static <T> T getBean(Class<T> requiredType) {
        return getApplicationContext().getBean(requiredType);
    }

    /**
     * 通过beanName 和 class对象从Spring中获取原对象
     *
     * @param beanName     bean 名称
     * @param requiredType class 对象
     * @param <T>          泛型
     * @return 实例对象引用
     */
    public static <T> T getBean(String beanName, Class<T> requiredType) {
        return getApplicationContext().getBean(beanName, requiredType);
    }

    /**
     * 策略+工厂模式
     *
     * @param requiredType class对象
     * @param <T>          泛型
     */
    public static <T> Map<String, T> getBeanOfType(Class<T> requiredType) {
        return getApplicationContext().getBeansOfType(requiredType);
    }

    /**
     * 策略+工厂模式
     *
     * @param requiredType class对象
     * @param <T>          泛型
     */
    public static <T> List<T> getBeans(Class<T> requiredType) {
        return Lists.newArrayList(getBeanOfType(requiredType).values());
    }

    private static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}
