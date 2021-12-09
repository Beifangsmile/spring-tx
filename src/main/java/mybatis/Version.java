package mybatis;

import java.lang.annotation.*;

/**
 * @description
 * @Author beifang
 * @Create 2021/12/5 20:40
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE })
public @interface Version {
}
