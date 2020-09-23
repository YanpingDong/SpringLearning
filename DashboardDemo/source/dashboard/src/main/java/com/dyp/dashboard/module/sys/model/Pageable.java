package com.dyp.dashboard.module.sys.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
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

}
