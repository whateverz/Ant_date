package com.ant.antdate.bean;

import java.util.List;

public class CommonListInfo<T> {
    private int total;
    private int total_page;
    private int prev_page;
    private int next_page;
    private int limit;
    private List<T> list;

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public int getTotal_page() {
        return total_page;
    }

    public void setTotal_page(int total_page) {
        this.total_page = total_page;
    }

    public List<T> getList() {
        return list;
    }

    public void setList(List<T> list) {
        this.list = list;
    }

    public int getPrev_page() {
        return prev_page;
    }

    public void setPrev_page(int prev_page) {
        this.prev_page = prev_page;
    }

    public int getNext_page() {
        return next_page;
    }

    public void setNext_page(int next_page) {
        this.next_page = next_page;
    }

    public int getLimit() {
        return limit;
    }

    public void setLimit(int limit) {
        this.limit = limit;
    }
}
