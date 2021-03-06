package mybatis;


import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.ArrayList;
import java.util.List;

/**
 * @description 代理类
 * @Author beifang
 * @Create 2021/11/28 21:56
 */
public class TargetProxy implements InvocationHandler {

    private Object target;

    private Interceptor interceptor;

    public TargetProxy(Object target, Interceptor interceptor) {
        this.target = target;
        this.interceptor = interceptor;
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
       Invocation invocation = new Invocation(target, method, args);
        return interceptor.intercept(invocation);
    }

    /**
     * 返回代理对象
     * @param target
     * @return
     */
    public static Object wrap(Object target, Interceptor interceptor) {
        TargetProxy targetProxy = new TargetProxy(target, interceptor);
        return Proxy.newProxyInstance(target.getClass().getClassLoader(),
                target.getClass().getInterfaces(),
                targetProxy);
    }
}
