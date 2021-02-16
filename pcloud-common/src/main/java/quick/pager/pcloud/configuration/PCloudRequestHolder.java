package quick.pager.pcloud.configuration;

import com.alibaba.ttl.TransmittableThreadLocal;
import java.util.Map;

/**
 * RequestHolder 全局线程变量
 *
 * @author siguiyang
 */
public class PCloudRequestHolder {

    private static final ThreadLocal<Map<String, String>> HOLDER = new TransmittableThreadLocal<>();

    public static Map<String, String> get() {
        return HOLDER.get();
    }


    public static void set(Map<String, String> map) {
        HOLDER.set(map);
    }

    public static void remove() {
        HOLDER.remove();
    }
}
