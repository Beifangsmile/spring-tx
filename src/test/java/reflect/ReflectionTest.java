package reflect;

import mybatis.ReflectionUtils;
import org.junit.Test;


import java.util.Map;

/**
 * @description 反射测试
 * @Author beifang
 * @Create 2021/12/7 17:13
 */
public class ReflectionTest {

    /**
     * 测试 ReflectionUtils.objectToMap() 方法
     * @throws Exception
     */
    @Test
    public void testGetBeanInfo() throws Exception {
        Person person = new Person("章三", 23);
        Map<String,Object> map = ReflectionUtils.objectToMap(person);
        System.out.println(map.size());
    }

    /**
     * 测试 invokeGetter/invokeSetter 方法
     */
    @Test
    public void testGetter() {
        Person person = new Person("李四", 24);
        System.out.println(person.getName() + person.getAge());
//        ReflectionUtils.invokeSetter(person,"age",25);
//        System.out.println(person.getName() + person.getAge());
        Integer age = (Integer) ReflectionUtils.invokeGetter(person,"age");
        System.out.println(age);
        System.out.println(person.getAge());

        System.out.println("===========");
        ReflectionUtils.invokeSetter(person,"name","王五");
        System.out.println(person.getName());
    }

    /**
     * 通过反射直接获取字段的值
     */
    @Test
    public void testGetField() {
        Person person = new Person("李四",24);
        String name = (String) ReflectionUtils.getFieldValue(person,"name");
        System.out.println(name);
        System.out.println("=====");
        ReflectionUtils.invokeSetter(person,"name","invokeName");
        System.out.println(person.getName());
        System.out.println(ReflectionUtils.invokeGetter(person,"name"));
    }

    /**
     * 通过反射设置对象属性
     */
    @Test
    public void testSetField() {
        Person person = new Person("王五", 25);
        ReflectionUtils.setFieldValue(person,"name","赵六");
        System.out.println(person.getName());
    }

    /**
     * 测试通过 反射 调用指定方法
     */
    @Test
    public void testMethodInvoke() {
        Person person = new Person("赵六", 26) ;
        System.out.println(ReflectionUtils.invokeMethod(person, "getNameAndAge", null, null));
    }

    /**
     * 测试通过 反射 调用指定含有参数的方法
     */
    @Test
    public void testMethodInvokeWithParameter() {
        Person person = new Person("小华", 28);
        System.out.println(ReflectionUtils.invokeMethod(person, "getNameAndAgeWithTarget", new Class[]{String.class}, new Object[]{"aaa"}));

        System.out.println("-------------");

        System.out.println(ReflectionUtils.invokeMethodByName(person, "getNameAndAgeWithTarget", new Object[]{"bbbb"}));
    }
}
