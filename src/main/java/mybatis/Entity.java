package mybatis;

import java.lang.annotation.*;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/30 15:34
 */
@Documented
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface Entity {
    String name() default "";
}
