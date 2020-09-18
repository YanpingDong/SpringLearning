package com.tra.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.apache.ibatis.session.SqlSession;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value = "api to test connectivity with related services", produces = "application/json")
@Controller
@RequestMapping("/")
public class ApiTestController {
    private static Logger logger = LoggerFactory.getLogger(ApiTestController.class);


    @ApiOperation(value = "heeartbeat detection", notes = "for heartbeat detection", httpMethod = "GET", response = ModelMap.class)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is ModelMap \n, example : "
                    + "{\"code\":\"200\",\"desc\":\"it works\"} ", response = ModelMap.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @RequestMapping(value = "ping", method = { RequestMethod.GET }, produces = "application/json;charset=UTF-8")
    @ResponseBody
    public ModelMap test() {
        ModelMap map = new ModelMap();
        map.addAttribute("code", "200");
        map.addAttribute("desc", "it works");
        logger.debug("hello world");
        return map;
    }


    @ApiOperation(value = "get version info", notes = "get version info, type is String \n, example : vx.x.x-rcxx", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @ResponseBody
    @RequestMapping(
            value = "version", method = { RequestMethod.GET })
    public String getVersion() {
        String version = getClass().getPackage().getImplementationVersion();
        SqlSession sqlsession;
        return version;
    }
}
