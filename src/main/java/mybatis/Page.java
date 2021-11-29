package mybatis;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @description 分页类
 * @Author beifang
 * @Create 2021/11/29 17:16
 */
public class Page<T> {

    /**
     * 是否为最后一页
     */
    private boolean hasMore = true;

    /**
     * 当前页尾记录位置
     */
    private int end;

    /**
     * 每页记录数
     */
    private int limit;

    /**
     * 当前页
     */
    private int page;

    /**
     * 总记录数
     */
    private long total;

    /**
     * 排序列
     */
    private String sortName;

    /**
     * 排序规则
     */
    private String sortOrder;

    /**
     * 请求参数
     */
    private Map<String, Object> params = new HashMap<>();

    /**
     * 需要查询的列
     */
    private List<String> columns = new ArrayList<>();

    /**
     * 求和的列
     */
    private String sumColumn;

    /**
     * 查询出来的记录
     */
    private List<T> rows = new ArrayList<>();

    /**
     * 是否忽略总数
     */
    private boolean ignoreCountSql = false;

    /**
     * 最大数
     */
    private Integer max = -1;

    /**
     * 当前页首条记录位置
     */
    private int begin;

    public boolean isHasMore() {
        return hasMore;
    }

    public void setHasMore(boolean hasMore) {
        this.hasMore = hasMore;
    }

    public int getEnd() {
        return end;
    }

    public void setEnd(int end) {
        this.end = end;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public String getSortName() {
        return sortName;
    }

    public void setSortName(String sortName) {
        this.sortName = sortName;
    }

    public String getSortOrder() {
        return sortOrder;
    }

    public void setSortOrder(String sortOrder) {
        this.sortOrder = sortOrder;
    }

    public Map<String, Object> getParams() {
        return params;
    }

    public void setParams(Map<String, Object> params) {
        this.params = params;
    }

    public List<String> getColumns() {
        return columns;
    }

    public void setColumns(List<String> columns) {
        this.columns = columns;
    }

    public String getSumColumn() {
        return sumColumn;
    }

    public void setSumColumn(String sumColumn) {
        this.sumColumn = sumColumn;
    }

    public List<T> getRows() {
        return rows;
    }

    public void setRows(List<T> rows) {
        this.rows = rows;
    }

    public boolean isIgnoreCountSql() {
        return ignoreCountSql;
    }

    public void setIgnoreCountSql(boolean ignoreCountSql) {
        this.ignoreCountSql = ignoreCountSql;
    }

    public Integer getMax() {
        return max;
    }

    public void setMax(Integer max) {
        this.max = max;
    }

    public int getBegin() {
        return begin;
    }

    public void setBegin(int begin) {
        this.begin = begin;
    }

    public Page(int limit) {
        this.limit = limit;
    }

    public Page(int limit, boolean ignoreCountSql) {
        this.limit = limit;
        this.ignoreCountSql = ignoreCountSql;
    }


}
