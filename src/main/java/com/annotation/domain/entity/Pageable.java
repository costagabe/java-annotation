package com.annotation.domain.entity;

public class Pageable {
    private int page;
    private int size;
    public Pageable(int page, int size) {}

    public int getPage() {
        return page;
    }

    public void setPage(int page) {
        this.page = page;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }
}
