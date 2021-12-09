package mybatis;

import com.google.common.collect.Lists;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/29 17:31
 */
public class CurdProvider extends BaseProvider{

    private static Logger logger = LoggerFactory.getLogger(CurdProvider.class);

    public static final String SAVE = "save";

    /**
     * 插入
     * @param entity
     * @return
     */
    public String save(Object entity) {
        // 列名
        List<String> columnNames = Lists.newArrayList();
        // 字段名
        List<String> fieldNames = Lists.newArrayList();

        // 获取所有字段
        for(MybatisColumn mybatisColumn : getMybatisTable().getMybatisColumnList()) {
            // 该列是否可以插入
            boolean insertable = getInsertable(getMybatisTable(), mybatisColumn);
            // 如果可插入，就加入到列名，字段名中。
            if(insertable) {
                // 通过反射获取字段的值
                Object value = ReflectionUtils.getFieldValue(entity, mybatisColumn.getFieldName());
                if(value != null) {
                    columnNames.add(mybatisColumn.getName());
                    fieldNames.add("#{" + mybatisColumn.getFieldName() + "}");
                }
            }
        }

        // 拼接sql

        StringBuffer sb = new StringBuffer("INSERT INTO ");
        // 根据实体类获取表名
        String tableName = ProviderUtils.getTableName(entity);

        sb.append(tableName);
        sb.append(" (");
        sb.append(StringUtils.join(columnNames, ","));
        sb.append(") ");
        sb.append("VALUES(");
        sb.append(StringUtils.join(fieldNames, ","));
        sb.append(")");
        String sql = sb.toString();
        return sql;
    }

    public String update(Object entity) {
        return updateSource(entity, true, false);
    }

    public String updateSource(Object entity, Boolean lock, Boolean isNull) {
        List<String> updateColumns = Lists.newArrayList();
        for(MybatisColumn mybatisColumn : getMybatisTable().getMybatisColumnList()) {
            boolean updateable = mybatisColumn.isUpdatable();
            if(getMybatisTable().getId() != null
                    && getMybatisTable().getId().getName().equals(mybatisColumn.getName())) {
                updateable = false;
            } else if(getMybatisTable().getVersion() != null
                    && getMybatisTable().getVersion().getName().equals(mybatisColumn.getName())) {
                if(lock) {
                    updateColumns.add(mybatisColumn.getName() + " = " + mybatisColumn.getName());
                }
                continue;
            }
            if(updateable) {
                Object value = ReflectionUtils.getFieldValue(entity, mybatisColumn.getFieldName());
                if(isNull) {
                    updateColumns.add(mybatisColumn.getName() + " = " + "#{" + mybatisColumn.getFieldName() +"}");
                }else{
                    if(value != null) {
                        updateColumns.add(mybatisColumn.getName() + " = " + "#{" + mybatisColumn.getFieldName() + "}");
                    }
                }
            }
        }
        // 构造sql
        StringBuilder sb = new StringBuilder("UPDATE ");
        String tableName = ProviderUtils.getTableName(entity);
        sb.append(tableName);
        sb.append(" SET ");
        sb.append(StringUtils.join(updateColumns, ","));
        sb.append(" WHERE ");
        sb.append(getMybatisTable().getId().getName());
        sb.append(" = ");
        sb.append("#{" + getMybatisTable().getId().getFieldName() + "}");
        if(lock) {
            sb.append(" and ");
            sb.append(getMybatisTable().getVersion().getName());
            sb.append(" = ");
            sb.append("#{" + getMybatisTable().getVersion().getFieldName() + "}");
        }
        return sb.toString();
    }

    /**
     *  判断 表 的某列是否可插入
     * @param mybatisTable
     * @param mybatisColumn
     * @return
     */
    private boolean getInsertable(MybatisTable mybatisTable, MybatisColumn mybatisColumn) {
        boolean insertable = mybatisColumn.isInsertable();
        if(getMybatisTable().getId() != null && getMybatisTable().getId().getName().equals(mybatisColumn.getName())) {
            insertable = GenerationType.IDENTITY.name().equals(getMybatisTable().getGenerationType());
        } else if(getMybatisTable().getVersion() != null
                && getMybatisTable().getVersion().getName().equals(mybatisColumn.getName())){
            insertable = true;
        }  else if (getMybatisTable().getCreatedBy() != null
                && getMybatisTable().getCreatedBy().getName().equals(mybatisColumn.getName())) {
            insertable = true;
        } else if (getMybatisTable().getCreatedDate() != null
                && getMybatisTable().getCreatedDate().getName().equals(mybatisColumn.getName())) {
            insertable = true;
        } else if (getMybatisTable().getLastModifiedBy() != null
                && getMybatisTable().getLastModifiedBy().getName().equals(mybatisColumn.getName())) {
            insertable = true;
        } else if (getMybatisTable().getLastModifiedDate() != null
                && getMybatisTable().getLastModifiedDate().getName().equals(mybatisColumn.getName())) {
            insertable = true;
        }
        return insertable;
    }



}
