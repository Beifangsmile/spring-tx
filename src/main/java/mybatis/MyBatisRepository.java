package mybatis;

import java.io.Serializable;

/**
 * @description
 * @Author beifang
 * @Create 2021/12/9 15:09
 */
public interface MyBatisRepository<T, ID extends Serializable> extends CrudRepository<T, ID>{
}
