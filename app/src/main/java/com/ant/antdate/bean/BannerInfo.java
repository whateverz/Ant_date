package com.ant.antdate.bean;

public class BannerInfo {


    /**
     * id : 1
     * slot_id : 1
     * title : 热区广告1
     * banner_image : https://antdate.oss-cn-beijing.aliyuncs.com/c42098c0-4263-4671-9a9d-a51394dbe033.jpg
     * banner_link : https://www.zhihu.com/hot
     * target_type : 1
     * status : 1
     * created_at : 2020-06-03 00:00:00
     * updated_at : 2020-06-03 00:00:00
     */

    private int id;
    private int slot_id;
    private String title;
    private String banner_image;
    private String banner_link;
    private int target_type;
    private int status;
    private String created_at;
    private String updated_at;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getSlot_id() {
        return slot_id;
    }

    public void setSlot_id(int slot_id) {
        this.slot_id = slot_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBanner_image() {
        return banner_image;
    }

    public void setBanner_image(String banner_image) {
        this.banner_image = banner_image;
    }

    public String getBanner_link() {
        return banner_link;
    }

    public void setBanner_link(String banner_link) {
        this.banner_link = banner_link;
    }

    public int getTarget_type() {
        return target_type;
    }

    public void setTarget_type(int target_type) {
        this.target_type = target_type;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreated_at() {
        return created_at;
    }

    public void setCreated_at(String created_at) {
        this.created_at = created_at;
    }

    public String getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(String updated_at) {
        this.updated_at = updated_at;
    }
}
