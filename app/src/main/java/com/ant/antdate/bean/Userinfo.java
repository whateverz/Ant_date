package com.ant.antdate.bean;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;
import lib.frame.bean.UserBaseInfo;

@Entity
public class Userinfo extends UserBaseInfo {
    @Id(assignable = true)
    private long id;
    private String phone;
    private String email;
    private String avatar;
    private String nickname;
    private int sex;
    private int age;
    private int level;
    private String last_login_time;
    private String register_time;
    private String token;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public int getSex() {
        return sex;
    }

    public void setSex(int sex) {
        this.sex = sex;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getLast_login_time() {
        return last_login_time;
    }

    public void setLast_login_time(String last_login_time) {
        this.last_login_time = last_login_time;
    }

    public String getRegister_time() {
        return register_time;
    }

    public void setRegister_time(String register_time) {
        this.register_time = register_time;
    }

    @Override
    public String getNickName() {
        return nickname;
    }

    @Override
    public void setNickName(String nickName) {
        this.nickname = nickName;
    }

    @Override
    public String getHeader() {
        return avatar;
    }

    @Override
    public void setHeader(String header) {
        avatar = header;
    }

    @Override
    public long getUid() {
        return id;
    }

    @Override
    public void setUid(long uid) {
        id = uid;
    }

    @Override
    public String getUserId() {
        return null;
    }

    @Override
    public void setUserId(String userId) {

    }

    @Override
    public String getToken() {
        return token;
    }

    @Override
    public void setToken(String token) {
        this.token = token;
    }
}
