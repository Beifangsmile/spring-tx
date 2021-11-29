package proxy;

import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/26 00:05
 */
public class CglibProxyExample implements MethodInterceptor {

    public Object getProxy(Class cls) {
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(cls);
        enhancer.setCallback(this);
        return enhancer.create();
    }


    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        System.out.println("调用正式对象前");
        Object result = methodProxy.invokeSuper(o,objects);
        System.err.println("调用正式对象后");
        return result;
    }
}
