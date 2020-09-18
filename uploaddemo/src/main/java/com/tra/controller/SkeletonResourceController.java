package com.tra.controller;

import com.tra.entity.Person;
import com.tra.entity.PersonRole;
import com.tra.repository.IPersonRole;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Api(value = "api to test connectivity with related services", produces = "application/json")
@Controller
@RequestMapping("/skeleton/resource")
public class SkeletonResourceController {
    @Autowired
    private IPersonRole iPersonRole;

    @ApiOperation(value = "insert personroles", notes = "write the for test function", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = String.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @PostMapping("/insert/personroles")
    @ResponseBody
    public String insertPersonRole(
            @ApiParam(value = "personRoles", required = true)
            @RequestBody(required = true)
                    List<PersonRole> personRoles)
    {
        iPersonRole.insertPersonRole(personRoles);
        return "success";
    }

    @ApiOperation(value = "get person info", notes = "write the for test function", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = Person.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @GetMapping("/get/person")
    @ResponseBody
    public Person getPersonAndRoleByUid(
            @ApiParam(value = "uid", required = true, example = "1")
            @RequestParam(value = "uid", required = true)
            int uid)
    {
       return  iPersonRole.getPersonAndRoleByUid(uid);
    }


    @ApiOperation(value = "insert person info", notes = "write the for test function", httpMethod = "POST")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = Person.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @PostMapping("/insert/person")
    @ResponseBody
    public String insertPerson(
            @ApiParam(value = "person", required = true)
            @RequestBody(required = true)
            Person person
    )
    {
        iPersonRole.insertPerson(person);
        return "success";
    }


    @ApiOperation(value = "delete person info", notes = "write the for test function", httpMethod = "DELETE")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = Person.class),
            @ApiResponse(code = 404, message = "Not Found", response = Error.class) })
    @DeleteMapping("/delete/personbyid")
    @ResponseBody
    public String delectePersonById(
            @ApiParam(value = "uid", required = true, example = "1")
            @RequestParam(value = "uid", required = true)
            int uid
    )
    {
        iPersonRole.deletePersonById(uid);
        return "success";
    }
}
