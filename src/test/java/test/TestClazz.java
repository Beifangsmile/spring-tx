package test;

import com.google.common.collect.Lists;
import org.junit.Test;

import java.lang.reflect.Field;
import java.util.List;

/**
 * @description
 * @Author beifang
 * @Create 2021/12/5 18:07
 */
public class TestClazz {

    @Test
    public void test() {
        Class clazz = Student.class;
        List<Field> fields = Lists.newArrayList();
        Class current = clazz;
        while(!current.getName().equals(Object.class.getName())) {
            getFields(fields, current);
            current = current.getSuperclass();
        }
        fields.stream().forEach(System.out::println);
    }
    public static void getFields(List<Field> fields, Class clazz) {
        for(Field field : clazz.getDeclaredFields()) {
            fields.add(field);
        }
    }
}
