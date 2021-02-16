package quick.pager.pcloud.service;

import java.math.BigDecimal;

public interface PCloudService {

    /**
     * 获取米粉值
     *
     * @param key      rediskey
     * @param optValue 米粉值
     * @param opt      操作模式
     */
    BigDecimal getMifen(final String key, final BigDecimal optValue, final String opt);
}
