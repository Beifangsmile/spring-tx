package io.transaction.spring.config;

import org.springframework.util.Assert;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/26 20:26
 */
public class DynamicDataSourceHolder {

    private static final ThreadLocal<String> contextHolder = new ThreadLocal<>();

    public static void setDataSource(String dataSource) {
        Assert.notNull(dataSource, "数据源不能为空");
        contextHolder.set(dataSource);
    }

    public static void setApi() {
        setDataSource("api");
    }

    public static void setMaster() {
        setDataSource("master");
    }

    /**
     * 获取数据源
     * @return
     */
    public static String getDataSource() {
        return contextHolder.get();
    }

    /**
     * 检查当前请求是否绑定了数据源
     * @return
     */
    public static boolean isBindDataSource() {
        return contextHolder.get() != null;
    }

    /**
     * 清空数据源
     */
    public static void remove() {
        contextHolder.remove();
    }
}
