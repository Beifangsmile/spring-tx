package mybatis;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/28 22:11
 */
public class TransactionInterceptor implements Interceptor{

    @Override
    public Object intercept(Invocation invocation) throws Exception {
        System.out.println(" 开启事务 ");
        Object result = invocation.process();
        System.out.println(" 提交事务 ");
        return result;
    }

    /**
     * 插入目标类的方法
     * @param target
     * @return
     */
    @Override
    public Object plugin(Object target) {
        return TargetProxy.wrap(target,this);
    }
}
