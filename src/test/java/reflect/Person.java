package reflect;

/**
 * @description
 * @Author beifang
 * @Create 2021/12/7 17:14
 */
public class Person {
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
