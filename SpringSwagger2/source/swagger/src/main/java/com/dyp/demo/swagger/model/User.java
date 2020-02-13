package com.dyp.demo.swagger.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;
import java.util.Map;

// Value is model name that will show on Swagger-ui as Model name,
// by default, the class name is used
@ApiModel(description = "user detail entity", value = "User")
public class User {
    @ApiModelProperty(required = true, value = "user name", example = "testName")
    private String name;

    @ApiModelProperty(required = true, value = "sex flag", example = "1")
    private int sex;

    @ApiModelProperty(required = false, accessMode = ApiModelProperty.AccessMode.READ_WRITE ,
            notes="User,name String, sex int",example = "{\"name\":\"sampleName\",\"sex\":1}")
    private User father;

    @ApiModelProperty(required = false, notes="java.util.List<user>", dataType = "java.util.List<user>", example = "[{\"name\":\"sampleName\",\"sex\":1}]")
    private List<User> friends;

    @ApiModelProperty(required = false)
    private Error error;

    @ApiModelProperty(required = false)
    private List<Error> errors;

    @ApiModelProperty(required = false )
    private Map<Integer, Error> errorSet;

    @ApiModelProperty(required = false, notes="java.util.Map<String, Integer>", dataType = "java.util.Map<String, Integer>",example = "{\"name\":1,\"sex\":1}")
    private Map<String,Integer>  tree;

    public Map<Integer, Error> getErrorSet() {
        return errorSet;
    }

    public void setErrorSet(Map<Integer, Error> errorSet) {
        this.errorSet = errorSet;
    }

    public List<Error> getErrors() {
        return errors;
    }

    public void setErrors(List<Error> errors) {
        this.errors = errors;
    }

    public Error getError() {
        return error;
    }

    public void setError(Error error) {
        this.error = error;
    }

    public User getFather() {
        return father;
    }

    public void setFather(User father) {
        this.father = father;
    }

    public Map<String, Integer> getTree() {
        return tree;
    }

    public void setTree(Map<String, Integer> tree) {
        this.tree = tree;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public List<User> getFriends() {
        return friends;
    }

    public void setFriends(List<User> friends) {
        this.friends = friends;
    }
}
