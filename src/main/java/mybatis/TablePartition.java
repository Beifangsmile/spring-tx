package mybatis;

import java.lang.annotation.*;

/**
 * @description 表分区策略相关
 * @Author beifang
 * @Create 2021/12/8 21:57
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TablePartition {

    TablePartitionStrategy strategy() default TablePartitionStrategy.HASH;

    /**
     * 分区时间策略
     * Date week: 7, day:1, month:30
     * @return
     */
    int dateRange() default 0;

    /**
     * hash 取余
     */
    int hashBalance() default 10;

    /**
     * range  数，
     * @return
     */
    int range();


    /**
     * 依赖分表的字段
     * @return
     */
    String column() default "id";
}
