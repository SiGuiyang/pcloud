package quick.pager.pcloud.annotation;


import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.springframework.context.annotation.Import;
import quick.pager.pcloud.configuration.XxlJobAutoConfiguration;

/**
 * xxl job 启用注解
 *
 * @author siguiyang
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Import(value = XxlJobAutoConfiguration.class)
public @interface EnableXxlJobAutoConfiguration {
}
