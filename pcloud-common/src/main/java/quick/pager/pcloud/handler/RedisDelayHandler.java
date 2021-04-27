package quick.pager.pcloud.handler;

/**
 * Redis 延迟队列顶级接口
 *
 * @author siguiyang
 */
public interface RedisDelayHandler {


    /**
     * redis延迟队列执行方法
     *
     * @param params 执行参数
     */
    void doExecute(final String params);

}
