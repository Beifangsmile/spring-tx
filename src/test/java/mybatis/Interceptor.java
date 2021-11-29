package mybatis;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/28 22:09
 */
public interface Interceptor {
    Object intercept(Invocation invocation) throws Exception;

    Object plugin(Object target);
}
