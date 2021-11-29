package proxy;

import org.junit.Test;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/25 23:44
 */
public class ProxyTest {

    /**
     * jdk动态代理
     */
    @Test
    public void testDynamicProxy () {
        Parent child = (Parent) new ProxyObject().bind(new Children());
        child.sysHello();
    }

    @Test
    public void testCglibProxy() {
        CglibProxyExample cpe = new CglibProxyExample();
        MyTest t = (MyTest) cpe.getProxy(MyTest.class);
        t.test();
    }

}
