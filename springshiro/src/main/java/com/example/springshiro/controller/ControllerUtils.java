package com.example.springshiro.controller;



import com.example.springshiro.model.Pageable;

import javax.servlet.http.HttpServletRequest;

public class ControllerUtils {
    public static Pageable getPageInfo(HttpServletRequest request)
    {
        if(request==null) return null;
        int pageSize = 10;
        try {
            pageSize = Integer.parseInt(request.getParameter("pageSize"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        int pageNumber = 0;
        try {
            pageNumber = Integer.parseInt(request.getParameter("pageNumber")) - 1;
        } catch (Exception e) {
            e.printStackTrace();
        }


        String sortName = request.getParameter("sortName") == null ? "" : request.getParameter("sortName");
        String sortOrder = request.getParameter("sortOrder") == null ? "asc" : request.getParameter("sortOrder");


        Pageable pageable = new Pageable(pageNumber, pageSize, sortOrder);

        return pageable;
    }

    public static String getSearchText(HttpServletRequest request)
    {
        if (request==null) return "";
        return request.getParameter("searchText") == null ? "" : request.getParameter("searchText");
    }
}
