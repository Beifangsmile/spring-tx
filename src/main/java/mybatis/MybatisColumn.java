package mybatis;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/29 17:32
 */
public class MybatisColumn {
    private MybatisTable mybatisTable;
    private String name;
    private String fieldName;
    private boolean insertable;
    private boolean updatable;
    private  boolean nullable;

    public MybatisTable getMybatisTable() {
        return mybatisTable;
    }

    public void setMybatisTable(MybatisTable mybatisTable) {
        this.mybatisTable = mybatisTable;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public boolean isInsertable() {
        return insertable;
    }

    public void setInsertable(boolean insertable) {
        this.insertable = insertable;
    }

    public boolean isUpdatable() {
        return updatable;
    }

    public void setUpdatable(boolean updatable) {
        this.updatable = updatable;
    }

    public boolean isNullable() {
        return nullable;
    }

    public void setNullable(boolean nullable) {
        this.nullable = nullable;
    }
}
