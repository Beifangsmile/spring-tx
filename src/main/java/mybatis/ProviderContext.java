package mybatis;

/**
 * @description
 * @Author beifang
 * @Create 2021/12/5 22:56
 */
public class ProviderContext {
    private Class entityClass;
    private Class idClass;
    private Object entity;

    public Class getEntityClass() {
        return entityClass;
    }

    public void setEntityClass(Class entityClass) {
        this.entityClass = entityClass;
    }

    public Class getIdClass() {
        return idClass;
    }

    public void setIdClass(Class idClass) {
        this.idClass = idClass;
    }

    public Object getEntity() {
        return entity;
    }

    public void setEntity(Object entity) {
        this.entity = entity;
    }
}
