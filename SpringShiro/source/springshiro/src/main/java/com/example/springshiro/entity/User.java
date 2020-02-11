package com.example.springshiro.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;

import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

/**
 * @Author: xu.dm
 * @Date: 2018/6/10 17:14
 * @Description: 为什么要使用@Table注解，并且把表名小写？
 * 是因为mysql数据库在windows环境下会被统一转成小写，
 * 而为了兼容Mariadb，Mariadb在linux环境下会按实际类名大小写来建表，而linux是大小写敏感，两者之间在迁移的时候就遇到问题。
 * 这里属于设计上的失误，表名不应该使用驼峰命名，应该统一小写，并使用下划线来分隔语义。
 * 用户及权限这套表都有次情况，所以使用@Table强行指定表名。
 */

//@Entity
//@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 5887833463461262508L;
    private Integer userId;
    private String userName; //登录用户名，账号
    private String name;//名称（昵称或者真实姓名，根据实际情况定义）
    private String password;
    private String salt;//加密密码的盐
    private String credentialsSalt;
    private byte state;//用户状态,0:创建未认证（比如没有激活，没有输入验证码等等）--等待验证的用户 , 1:正常状态,2：用户被锁定.

    private List<SysUserRole> roleList;// 一个用户具有多个角色

    private List<SysPermission> permissionList;//用户的权限
    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    //格式化前台页面收到的json时间格式，不指定的话会变成缺省的"yyyy-MM-dd'T'HH:mm:ss"
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime createTime;//创建时间
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate expiredDate;//过期日期
    private String email;
    private String tel;

    public User() {
    }

    public List<SysUserRole> getRoleList() {
        return roleList;
    }

    public void setRoleList(List<SysUserRole> roleList) {
        this.roleList = roleList;
    }

    public List<SysPermission> getPermissionList() {
        return permissionList;
    }

    public void setPermissionList(List<SysPermission> permissionList) {
        this.permissionList = permissionList;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDate getExpiredDate() {
        return expiredDate;
    }

    public void setExpiredDate(LocalDate expiredDate) {
        this.expiredDate = expiredDate;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getSalt() {
        return salt;
    }

    public void setSalt(String salt) {
        this.salt = salt;
    }

    public byte getState() {
        return state;
    }

    public void setState(byte state) {
        this.state = state;
    }

    /**
     * 密码盐.
     * @return
     */ //重新对盐重新进行了定义，用户名+salt，这样就更加不容易被破解
    public String getCredentialsSalt(){
        return this.userName+this.salt;
    }

    public void setCredentialsSalt(String credentialsSalt) {
        this.credentialsSalt = credentialsSalt;
    }
}
