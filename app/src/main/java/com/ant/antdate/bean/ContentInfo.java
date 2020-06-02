package com.ant.antdate.bean;

import java.util.List;

public class ContentInfo {

    /**
     * topic_id : 1
     * user_id : 3
     * nickname : chenshiwei
     * avatar : http://google.com
     * created_time : 2020-05-23T00:19:02+08:00
     * chat_id :
     * online : 1
     * views : 1
     * covers : [{"url":"http://baidu.com","type":1,"title":"baidu"}]
     * title : title
     * summary :
     * topic_type : 0
     */

    private int topic_id;
    private int user_id;
    private String nickname;
    private String avatar;
    private String created_time;
    private String chat_id;
    private int online;
    private int views;
    private String title;
    private String summary;
    private int topic_type;
    private List<CoversBean> covers;

    public int getTopic_id() {
        return topic_id;
    }

    public void setTopic_id(int topic_id) {
        this.topic_id = topic_id;
    }

    public int getUser_id() {
        return user_id;
    }

    public void setUser_id(int user_id) {
        this.user_id = user_id;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getCreated_time() {
        return created_time;
    }

    public void setCreated_time(String created_time) {
        this.created_time = created_time;
    }

    public String getChat_id() {
        return chat_id;
    }

    public void setChat_id(String chat_id) {
        this.chat_id = chat_id;
    }

    public int getOnline() {
        return online;
    }

    public void setOnline(int online) {
        this.online = online;
    }

    public int getViews() {
        return views;
    }

    public void setViews(int views) {
        this.views = views;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public int getTopic_type() {
        return topic_type;
    }

    public void setTopic_type(int topic_type) {
        this.topic_type = topic_type;
    }

    public List<CoversBean> getCovers() {
        return covers;
    }

    public void setCovers(List<CoversBean> covers) {
        this.covers = covers;
    }

    public static class CoversBean {
        /**
         * url : http://baidu.com
         * type : 1
         * title : baidu
         */

        private String url;
        private int type;
        private String title;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public int getType() {
            return type;
        }

        public void setType(int type) {
            this.type = type;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }
    }
}
