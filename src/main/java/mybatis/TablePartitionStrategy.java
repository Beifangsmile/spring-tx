package mybatis;

/**
 * @description
 * @Author beifang
 * @Create 2021/12/8 21:58
 */
public enum TablePartitionStrategy {
    DATE,
    HASH,
    RANGE,
    RANGE_AND_HASH;

}
