package mybatis;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @description
 * @Author beifang
 * @Create 2021/12/5 20:41
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(value = {ElementType.FIELD,ElementType.METHOD,ElementType.ANNOTATION_TYPE})
public @interface Deleted {
}
