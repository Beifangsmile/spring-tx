package reflect;

import mybatis.Entity;
import mybatis.GeneratedValue;
import mybatis.GenerationType;
import mybatis.Table;
import org.junit.Test;

/**
 * @description
 * @Author beifang
 * @Create 2021/12/7 17:14
 */
@Entity
@Table(name="person")
public class Person {
    @GeneratedValue(strategy = GenerationType.CUSTOM)
    private String id;
    private String name;
    private Integer age;

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNameAndAge() {
        String result = name + ":" + age;
        return result;
    }

    public String getNameAndAgeWithTarget(String target) {
        String result = name + " : " + age + " : " + target;
        return result;
    }
}
