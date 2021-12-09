package mybatis;

/**
 * @description
 * @Author beifang
 * @Create 2021/12/5 22:56
 */
public class MyBatisProviderContext {

    private static ThreadLocal<MyBatisProviderContext> threadLocal = new ThreadLocal<>();

    private ProviderContext providerContext;

    public static MyBatisProviderContext get() {
        if(threadLocal.get() == null) {
            MyBatisProviderContext threadLocalContext = new MyBatisProviderContext();
            threadLocal.set(threadLocalContext);
        }
        return threadLocal.get();
    }

    public void remove() {
        this.providerContext = null;
        threadLocal.remove();
    }

    public ProviderContext getProviderContext() {
        return providerContext;
    }

    public void setProviderContext(ProviderContext providerContext) {
        this.providerContext = providerContext;
    }


}
