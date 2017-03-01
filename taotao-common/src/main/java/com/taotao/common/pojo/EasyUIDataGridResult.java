package com.taotao.common.pojo;

import java.io.Serializable;
import java.util.List;

/**
 * 包装返回EasyUI返回 json 的工具类
 */
public class EasyUIDataGridResult implements Serializable{

    private long total;
    // 不添加泛型。 因为之后有可能改变
    private List rows;

    public long getTotal() {
        return total;
    }

    public void setTotal(long total) {
        this.total = total;
    }

    public List getRows() {
        return rows;
    }

    public void setRows(List rows) {
        this.rows = rows;
    }
}
