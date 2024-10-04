package com.annotation.domain.entity;

import java.util.List;

public class Page<K extends BaseEntity<?>> {
    private List<K> values;
    private int pageSize;
    private int pageIndex;

    public List<K> getValues() {
        return values;
    }

    public void setValues(List<K> values) {
        this.values = values;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public int getPageIndex() {
        return pageIndex;
    }

    public void setPageIndex(int pageIndex) {
        this.pageIndex = pageIndex;
    }
}
