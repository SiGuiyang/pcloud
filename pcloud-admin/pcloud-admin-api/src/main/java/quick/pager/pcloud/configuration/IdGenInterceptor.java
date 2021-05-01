package quick.pager.pcloud.configuration;


import com.google.common.collect.Maps;
import java.beans.IntrospectionException;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Map;
import java.util.Objects;
import javax.annotation.Resource;
import org.apache.commons.lang3.ClassUtils;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.apache.ibatis.plugin.Interceptor;
import org.apache.ibatis.plugin.Intercepts;
import org.apache.ibatis.plugin.Invocation;
import org.apache.ibatis.plugin.Plugin;
import org.apache.ibatis.plugin.Signature;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import quick.pager.pcloud.annotation.IdGen;
import quick.pager.pcloud.integration.client.IntegrationIdGenClient;
import quick.pager.pcloud.response.ResponseResult;
import quick.pager.pcloud.utils.Assert;

/**
 * id生成注解
 *
 * @author siguiyang
 */
@Intercepts({@Signature(type = Executor.class, method = "update", args = {MappedStatement.class, Object.class})})
@Component
public class IdGenInterceptor implements Interceptor {

    /**
     * 类与id生成器biz缓存器
     */
    private static final Map<Class<?>, String> CLASS_FIELD_BIZ_MAP = Maps.newConcurrentMap();
    /**
     * 类与反射缓存
     */
    private static final Map<Class<?>, PropertyDescriptor> CLASS_PROPERTY_DESCRIPTOR_MAP = Maps.newConcurrentMap();

    @Resource
    private IntegrationIdGenClient integrationIdGenClient;

    @Override
    public Object intercept(Invocation invocation) throws Throwable {
        Object[] args = invocation.getArgs();

        if (invocation.getTarget() instanceof Executor) {
            Executor executor = (Executor) invocation.getTarget();
            MappedStatement mappedStatement = (MappedStatement) args[0];
            Object parameterObject = args[1];

            // 插入没有参数，直接通过，下面逻辑pass
            if (null == parameterObject) {
                return invocation.proceed();
            }

            // 基本数据类型及基本数据类型数组剔除
            if (ClassUtils.isPrimitiveOrWrapper(parameterObject.getClass()) || !isAvailable(parameterObject)) {
                return invocation.proceed();
            }

            // 如果是插入操作执行如下逻辑
            if (SqlCommandType.INSERT == mappedStatement.getSqlCommandType()) {

                loadAllClassWidthIdGenerator(parameterObject);

                // 如果存在号段生成器注解，则调用号段生成器，否则使用非号段生成器策略
                if (CLASS_FIELD_BIZ_MAP.containsKey(parameterObject.getClass())) {
                    requestIdGeneratorAndSetValue(parameterObject);
                    return executor.update(mappedStatement, parameterObject);
                }
            }
        }

        return invocation.proceed();
    }

    @Override
    public Object plugin(Object target) {
        return Plugin.wrap(target, this);
    }

    /**
     * 加载所有的clazz对象如缓存
     */
    private static void loadAllClassWidthIdGenerator(Object entity) throws IntrospectionException {

        Class<?> clazz = entity.getClass();

        if (!CLASS_FIELD_BIZ_MAP.containsKey(clazz)) {
            Field[] fields = clazz.getDeclaredFields();

            for (Field field : fields) {
                if (field.isAnnotationPresent(IdGen.class)) {
                    CLASS_FIELD_BIZ_MAP.putIfAbsent(clazz, field.getAnnotation(IdGen.class).value());
                    CLASS_PROPERTY_DESCRIPTOR_MAP.putIfAbsent(clazz, new PropertyDescriptor(field.getName(), clazz));
                    break;
                }
            }
        }
    }

    /**
     * 向Id生成器服务请求，或者当前生成的Id值<br />
     * 并使用反射方式给字段设置当前的Id值
     */
    private void requestIdGeneratorAndSetValue(Object entity) throws InvocationTargetException, IllegalAccessException {
        Class<?> clazz = entity.getClass();

        String bizTag = CLASS_FIELD_BIZ_MAP.get(clazz);
        PropertyDescriptor propertyDescriptor = CLASS_PROPERTY_DESCRIPTOR_MAP.get(clazz);

        Assert.isTrue(StringUtils.hasLength(bizTag) && Objects.nonNull(propertyDescriptor), () -> "Id生成器数据缓存异常，请检查加入缓存的业务");

        ResponseResult<String> result = integrationIdGenClient.getSegmentId(bizTag);

        if (result.check()) {
            // 获得用于写入属性值的方法
            Method method = propertyDescriptor.getWriteMethod();
            method.invoke(entity, Long.valueOf(result.getData()));
        }

    }

    /**
     * 是否可用的
     *
     * @param parameterObject mybatis insert 操作的入参
     */
    private boolean isAvailable(Object parameterObject) {
        Class clazz = parameterObject.getClass();
        return !(clazz.isInterface() || clazz.isEnum() || Modifier.isAbstract(clazz.getModifiers()));
    }
}
