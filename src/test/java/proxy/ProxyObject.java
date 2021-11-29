package proxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/25 23:46
 */
public class ProxyObject implements InvocationHandler {

    private Object target;

    public Object bind(Object target) {
        this.target = target;
        // 1. 目标对象的类加载器
        // 2. 目标对象的接口
        // 3. 定义实现方法的代理类
        return Proxy.newProxyInstance(target.getClass().getClassLoader(), target.getClass().getInterfaces(),this);
    }


    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        System.out.println("代理类新增的操作1");
        return method.invoke(target, args);
    }
}
