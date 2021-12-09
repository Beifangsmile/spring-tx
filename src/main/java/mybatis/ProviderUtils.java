package mybatis;

import com.google.common.base.CaseFormat;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import static mybatis.TablePartitionStrategy.*;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/29 17:36
 */
public class ProviderUtils {
    // 用于存储表结构缓存
    private static Map<String, MybatisTable> mybatisTableMap = Maps.newHashMap();

    /**
     * 更具 实体类class对象，解析成 MybatisTable
     * @param clazz
     * @return
     */
    public static MybatisTable getMybatisTable(Class clazz) {
        // 判断 缓存中是否已经存储类 该表结构信息
        if (!mybatisTableMap.containsKey(clazz.getName())) {
            // 不存在
            MybatisTable mybatisTable = new MybatisTable();
            Entity entity = (Entity) clazz.getAnnotation(Entity.class);
            Table table = (Table) clazz.getAnnotation(Table.class);

            // 如果实体类都加上了 @Entity, @Table 两个注解
            if (entity != null && table != null) {
                // 从 @Table 中获取 name 属性 作为表明
                String tableName = table.name();
                if (StringUtils.isBlank(tableName)) {
                    // 进行格式转换
                    tableName = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, clazz.getSimpleName());
                }
                // 设置 tableName 属性
                mybatisTable.setName(tableName);
                // 获取该实体类对象的字段
                List<Field> fields = getFields(clazz);
                for (Field field : fields) {
                    Class fieldClass = field.getType();
                    if (!"serialVersionUID".equals(field.getName())
                            && field.getAnnotation(Transient.class) == null
                            && fieldClass.getAnnotation(Entity.class) == null
                            && !Collection.class.isAssignableFrom(fieldClass)
                            && !Map.class.isAssignableFrom(fieldClass)) {

                        MybatisColumn mybatisColumn = getMybatisColumn(field);
                        mybatisColumn.setMybatisTable(mybatisTable);
                        mybatisTable.getMybatisColumnList().add(mybatisColumn);
                        //检查是否是ID
                        if (field.getAnnotation(Id.class) != null) {
                            mybatisTable.setId(mybatisColumn);
                            String generationType = GenerationType.IDENTITY.name();
                            if (field.getAnnotation(GeneratedValue.class) != null) {
                                generationType = field.getAnnotation(GeneratedValue.class).strategy().name();
                            }
                            mybatisTable.setGenerationType(generationType);
                        }
                        // 是否加了 @Version
                        if (field.getAnnotation(Version.class) != null) {
                            mybatisTable.setVersion(mybatisColumn);
                        }
                        // 是否加了 @Deleted
                        if (field.getAnnotation(Deleted.class) != null) {
                            mybatisTable.setDeleted(mybatisColumn);
                        }
                    }
                }
            }
            // 如果实体类没有加上 @Entity @Table 注解，则缓存空的，并以类名作为表名
            mybatisTableMap.put(clazz.getName(), mybatisTable);
        }
        return mybatisTableMap.get(clazz.getName());
    }

    /**
     * 根据对象 获取  表名
     * @param object
     * @return
     */
    public static String getTableName(Object object) {
        Class<?> clazz = object.getClass();

        TablePartition tablePartition = clazz.getAnnotation(TablePartition.class);
        if (tablePartition == null) {
            return mybatisTableMap.get(clazz.getName()).getName();
        }
        String column = tablePartition.column();
        Field field = ReflectionUtils.getAccessibleField(clazz, column);
        Class fieldClazz = field.getType();
        Long temp;
        Object tempObject;
        switch (tablePartition.strategy()) {
            case DATE:
                Assert.isTrue(LocalDateTime.class.isAssignableFrom(fieldClazz), "TablePartition column type is not LocalDateTime");
                break;
            case HASH:
                tempObject = ReflectionUtils.invokeGetter(object, column);
                if (Long.class.isAssignableFrom(fieldClazz)) {
                    temp = (Long) tempObject;
                } else {
                    temp = new Long(Objects.hash(tempObject));
                }
                if (temp % tablePartition.hashBalance() != 0) {
                    return mybatisTableMap.get(clazz.getName()).getName() + "_" + "hash" + "_" + temp % tablePartition.hashBalance();
                }
                break;
            case RANGE:
                Assert.isTrue(Long.class.isAssignableFrom(fieldClazz), "TablePartition RANGE column type is not Long");
                break;
            case RANGE_AND_HASH:
                Assert.isTrue(Long.class.isAssignableFrom(fieldClazz), "TablePartition RANGE_AND_HASH column type is not Long");
                tempObject = ReflectionUtils.invokeGetter(object, column);
                if (Long.class.isAssignableFrom(fieldClazz)) {
                    temp = (Long) tempObject;
                } else {
                    temp = new Long(Objects.hash(tempObject));
                }
                if (tablePartition.range() < temp) {
                    return mybatisTableMap.get(clazz.getName()).getName() + "_" + "range_and_hash" + "_" + temp % tablePartition.hashBalance();
                }
        }
        return mybatisTableMap.get(clazz.getName()).getName();
    }


    // 更具实体类字段获取数据库字段
    private static MybatisColumn getMybatisColumn(Field field) {
        MybatisColumn mybatisColumn = new MybatisColumn();
        mybatisColumn.setFieldName(field.getName());
        String name = CaseFormat.UPPER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, field.getName());

        boolean insertable = true;
        boolean updatable = true;
        boolean nullable = false;
        Column column = field.getAnnotation(Column.class);
        if (column != null) {
            if (StringUtils.isNotBlank(column.name())) {
                name = column.name();
            }
            insertable = column.insertable();
            updatable = column.updatable();
            nullable = column.nullable();
        }
        mybatisColumn.setName(name);
        mybatisColumn.setInsertable(insertable);
        mybatisColumn.setUpdatable(updatable);
        mybatisColumn.setNullable(nullable);
        return mybatisColumn;
    }
//

    /**
     * 向父类递归获取字段直至遇到Object类
     * @param clazz
     * @return
     */
    private static List<Field> getFields(Class clazz) {
        List<Field> fields = Lists.newArrayList();
        Class current = clazz;
        while (!current.getName().equals(Object.class.getName())) {
            getFields(fields, current);
            current = current.getSuperclass();
        }
        return fields;
    }

    /**
     * 获取 类所定义的字段
     * @param fields    用来存放字段的容器
     * @param clazz     需要获取那个类的字段
     */
    private static void getFields(List<Field> fields, Class clazz) {
        for (Field field : clazz.getDeclaredFields()) {
            fields.add(field);
        }
    }
}
