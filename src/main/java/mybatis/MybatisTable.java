package mybatis;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import java.util.List;
import java.util.Map;

/**
 * @description
 * @Author beifang
 * @Create 2021/11/29 17:32
 */
public class MybatisTable {
    private String name;
    private String category;
    private String schema;
    private List<MybatisColumn> mybatisColumnList = Lists.newArrayList();
    private MybatisColumn id;
    private MybatisColumn createdBy;
    private MybatisColumn createdDate;
    private MybatisColumn lastModifiedBy;
    private MybatisColumn LastModifiedDate;
    private MybatisColumn version;
    private MybatisColumn deleted;
    private String generationType;


    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getSchema() {
        return schema;
    }

    public void setSchema(String schema) {
        this.schema = schema;
    }

    public MybatisColumn getId() {
        return id;
    }

    public void setId(MybatisColumn id) {
        this.id = id;
    }

    public MybatisColumn getCreatedBy() {
        return createdBy;
    }

    public void setCreatedBy(MybatisColumn createdBy) {
        this.createdBy = createdBy;
    }

    public MybatisColumn getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(MybatisColumn createdDate) {
        this.createdDate = createdDate;
    }

    public MybatisColumn getLastModifiedBy() {
        return lastModifiedBy;
    }

    public void setLastModifiedBy(MybatisColumn lastModifiedBy) {
        this.lastModifiedBy = lastModifiedBy;
    }

    public MybatisColumn getLastModifiedDate() {
        return LastModifiedDate;
    }

    public void setLastModifiedDate(MybatisColumn lastModifiedDate) {
        LastModifiedDate = lastModifiedDate;
    }

    public MybatisColumn getVersion() {
        return version;
    }

    public void setVersion(MybatisColumn version) {
        this.version = version;
    }

    public MybatisColumn getDeleted() {
        return deleted;
    }

    public void setDeleted(MybatisColumn deleted) {
        this.deleted = deleted;
    }

    public List<MybatisColumn> getMybatisColumnList() {
        return mybatisColumnList;
    }

    public void setMybatisColumnList(List<MybatisColumn> mybatisColumnList) {
        this.mybatisColumnList = mybatisColumnList;
    }

    public String getGenerationType() {
        return generationType;
    }

    public void setGenerationType(String generationType) {
        this.generationType = generationType;
    }

    public Map<String, MybatisColumn> getMybatisColumnMap() {
        Map<String, MybatisColumn> map = Maps.newLinkedHashMap();
        for (MybatisColumn mybatisColumn : getMybatisColumnList()) {
            map.put(mybatisColumn.getName(), mybatisColumn);
        }
        return map;
    }
}
