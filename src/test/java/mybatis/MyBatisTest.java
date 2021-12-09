package mybatis;

import org.junit.Test;
import reflect.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/28 22:05
 */
public class MyBatisTest {

    /**
     * 测试 mybatis 插件/责任链模式
     */
    @Test
    public void test() {
        Interceptor interceptor = new TransactionInterceptor();
        Target target = new TargetImpl();
        //Target targetProxy = (Target) TargetProxy.wrap(target, interceptor);

        // 传入拦截器就可以了
        target = (Target) interceptor.plugin(target);
        target.execute(" HelloWord ");
    }

    @Test
    public void testProviderSave() {
        Person person = new Person("zhangsn", 23);
        MybatisTable table = ProviderUtils.getMybatisTable(Person.class);
        MybatisProvider mybatisProvider = new MybatisProvider();
        System.out.println(table.getName());
        ProviderContext providerContext = new ProviderContext();
        providerContext.setEntityClass(mybatisProvider.getEntityClass());
        providerContext.setEntity(mybatisProvider);
        MyBatisProviderContext.get().setProviderContext(providerContext);
        System.out.println(mybatisProvider.save(person));

//        List list =  ProviderUtils.getMybatisTable(Person.class).getMybatisColumnList();
//        System.out.println(list.size());

    }

}
