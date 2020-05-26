package com.ant.antdate.bean;

public class LoginInfo {

    /**
     * expired : 2020-06-16T11:35:15.734947Z
     * im : {"token":"4f7b823961b58c790a0eb660e5c0f0b3","accid":"16601163553","name":""}
     * token : eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJhZ2UiOjk5LCJhdmF0YXIiOiJodHRwOi8vZ29vZ2xlLmNvbSIsImVtYWlsIjoiIiwiZXhwIjoxNTkyMzA3MzE1LCJpZCI6IjMiLCJsZXZlbCI6MCwibmlja25hbWUiOiJjaGVuc2hpd2VpIiwib3JpZ19pYXQiOjE1ODk3MTUzMTUsInBhc3N3b3JkIjoiIiwicGhvbmUiOiIxNjYwMTE2MzU1MyIsInNleCI6MX0.vuvvuRoSHpCwu_wVMoHhXvE3esrWiARynJGsxsFWCs8
     * user : {"id":3,"phone":"16601163553","email":"","avatar":"http://google.com","nickname":"chenshiwei","sex":1,"age":99,"level":0,"last_login_time":"0001-01-01 00:00:00","register_time":"2020-05-14 15:08:18"}
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
         * token : 4f7b823961b58c790a0eb660e5c0f0b3
         * accid : 16601163553
         * name :
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
