package mybatis;

import org.apache.ibatis.annotations.InsertProvider;

import java.io.Serializable;

/**
 * @description
 * @Author beifang
 * @Create 2021/12/9 15:09
 */
public interface CrudRepository<T, ID extends Serializable> extends BaseRepository<T, ID>{

    @InsertProvider(type=MybatisProvider.class, method = MybatisProvider.SAVE)
    int save(T entity);

}
