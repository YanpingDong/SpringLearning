package com.dyp.demo.swagger.controller;

import com.dyp.demo.swagger.model.User;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;
import com.dyp.demo.swagger.model.Error;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Api(value = "for swagger demo", tags = "SampleController",
        authorizations = {@Authorization("lear521@163.com"),@Authorization("xxx@163.com")})
@RestController
@RequestMapping("/")
public class SampleController {

    @RequestMapping(value = "user/{id}", method = RequestMethod.PUT)
    @ApiOperation(value="update user", notes = "update user", produces = "application/json")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "success", responseContainer = "Map"),
            @ApiResponse(code = 500, message = "server error", response = Error.class)
    })
    public Map<String, Object> saveUser(
                                        @ApiParam(name = "id", example = "1", required = true)
                                        @PathVariable(name = "name") int id,
                                        @ApiParam(name = "desc", example = "use desc info", required = false)
                                        @RequestParam(name = "desc", required = false) String desc,
                                        @ApiParam(required = true )
                                        @RequestBody List<User> user)
    {
        Map<String, Object> ret = new HashMap<>();
        return ret;
    }

    @ApiOperation(value = "get version info", notes = "get version info, type is String (Cann't be test in swagger, " +
            "must using Jar file deploy)\n, example : vx.x.x-rcxx", httpMethod = "GET")
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Success, type is String \n, example : vx.x.x-rcxx ", response = String.class),
            @ApiResponse(code = 500, message = "Service error", response = Error.class) })
    @ResponseBody
    @RequestMapping(value = "version", method = { RequestMethod.GET })
    public String getVersion() {

        return "1.0.0";
    }
}
