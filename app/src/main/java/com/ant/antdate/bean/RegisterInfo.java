package com.ant.antdate.bean;

public class RegisterInfo {

    /**
     * expired : 2020-06-16T12:30:19.202204Z
     * im : {"token":"67a70586a8cec0ec232ecb22fe3f6c2a","accid":"16601163551","name":"16601163551"}
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZ2UiOjAsImF2YXRhciI6IiIsImVtYWlsIjoiIiwiZXhwIjoxNTkyMzEwNjE5LCJpZCI6IjQiLCJsZXZlbCI6MCwibmlja25hbWUiOiIxNjYwMTE2MzU1MSIsIm9yaWdfaWF0IjoxNTg5NzE4NjE5LCJwYXNzd29yZCI6IiIsInBob25lIjoiMTY2MDExNjM1NTEiLCJzZXgiOjB9.f8yMcTKzZNp-5Rpky6_Sx_T-Esa_yAEfswZQ5iR8WBo
     * user : {"id":4,"phone":"16601163551","email":"","avatar":"","nickname":"16601163551","sex":0,"age":0,"level":0,"last_login_time":"0001-01-01 00:00:00","register_time":"2020-05-17 20:30:19"}
     */

    private String expired;
    private ImBean im;
    private String token;
    private Userinfo user;

    public String getExpired() {
        return expired;
    }

    public void setExpired(String expired) {
        this.expired = expired;
    }

    public ImBean getIm() {
        return im;
    }

    public void setIm(ImBean im) {
        this.im = im;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public Userinfo getUser() {
        user.setToken(token);
        return user;
    }

    public void setUser(Userinfo user) {
        this.user = user;
    }

    public static class ImBean {
        /**
         * token : 67a70586a8cec0ec232ecb22fe3f6c2a
         * accid : 16601163551
         * name : 16601163551
         */

        private String token;
        private String accid;
        private String name;

        public String getToken() {
            return token;
        }

        public void setToken(String token) {
            this.token = token;
        }

        public String getAccid() {
            return accid;
        }

        public void setAccid(String accid) {
            this.accid = accid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }
}
