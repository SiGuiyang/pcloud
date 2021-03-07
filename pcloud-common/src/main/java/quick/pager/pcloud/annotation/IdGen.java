package quick.pager.pcloud.annotation;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Id生成注解
 *
 * @author siguiyang
 */
@Documented
@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface IdGen {

    /**
     * 注解类型号段
     *
     * @return 0L
     */
    String value() default "id";
}
