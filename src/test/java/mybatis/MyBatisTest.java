package mybatis;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/28 22:05
 */
public class MyBatisTest {

    @Test
    public void test() {
        Interceptor interceptor = new TransactionInterceptor();
        Target target = new TargetImpl();
        //Target targetProxy = (Target) TargetProxy.wrap(target, interceptor);

        // 传入拦截器就可以了
        target = (Target) interceptor.plugin(target);
        target.execute(" HelloWord ");
    }

}
