package mybatis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/30 15:45
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
public @interface Transient {
}
