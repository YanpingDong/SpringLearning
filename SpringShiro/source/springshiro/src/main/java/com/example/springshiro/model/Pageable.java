package com.example.springshiro.model;

public class Pageable {
    private  int pageNumber;
    private int pageSize;
    private String sortLocal;
    public Pageable(){}

    public Pageable(int pageNumber, int pageSize, String sortLocal)
    {
        this.pageNumber = pageNumber;
        this.pageSize = pageSize;
        this.sortLocal = sortLocal;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public String getSortLocal() {
        return sortLocal;
    }

    public void setSortLocal(String sortLocal) {
        this.sortLocal = sortLocal;
    }
}
