package mybatis;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/29 17:31
 */
public class BaseProvider {

    protected MybatisTable getMybatisTable() {
        return ProviderUtils.getMybatisTable(getEntityClass());
    }

    protected Class getEntityClass() {
        return MyBatisProviderContext.get().getProviderContext().getEntityClass();
    }

    protected Object getEntity() {
        return MyBatisProviderContext.get().getProviderContext().getEntity();
    }
}
