package lib.frame.bean;

import com.google.gson.Gson;

/**
 * Created by lwxkey on 16/7/7.
 */
public class BaseHeaderInfo {

    public String device_no = "";//	设备号	Anroid的IMEI，IOS的IDFA
    public String mac_address = "";
    public int os_version = 0;//	操作系统版本
    public String channel_code = "";// 渠道号 googleplay,appstore,baidu,360
    public String client_bundle_id = "";// 客户端包名
    public String client_version_name = "";// 客户端版本名
    public int client_version_code = 0;// 客户端版本号
    public String user_token = "";// 用户登录令牌，当用户完成登录后由服务端返回，请存储于客户端。
    public String lang = "en";//默认是英文
    public String model_name = "";
    public String lat = "";
    public String lng = "";

    public String getDevice_no() {
        return device_no;
    }

    public void setDevice_no(String device_no) {
        this.device_no = device_no;
    }

    public int getOs_version() {
        return os_version;
    }

    public void setOs_version(int os_version) {
        this.os_version = os_version;
    }

    public String getChannel_code() {
        return channel_code;
    }

    public void setChannel_code(String channel_code) {
        this.channel_code = channel_code;
    }

    public String getClient_bundle_id() {
        return client_bundle_id;
    }

    public void setClient_bundle_id(String client_bundle_id) {
        this.client_bundle_id = client_bundle_id;
    }

    public String getClient_version_name() {
        return client_version_name;
    }

    public void setClient_version_name(String client_version_name) {
        this.client_version_name = client_version_name;
    }

    public int getClient_version_code() {
        return client_version_code;
    }

    public void setClient_version_code(int client_version_code) {
        this.client_version_code = client_version_code;
    }

    public String getUser_token() {
        return user_token;
    }

    public void setUser_token(String user_token) {
        this.user_token = user_token;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getModel_name() {
        return model_name;
    }

    public void setModel_name(String model_name) {
        this.model_name = model_name;
    }

    public String getMac_address() {
        return mac_address;
    }

    public void setMac_address(String mac_address) {
        this.mac_address = mac_address;
    }

    public String getLat() {
        return lat;
    }

    public void setLat(String lat) {
        this.lat = lat;
    }

    public String getLng() {
        return lng;
    }

    public void setLng(String lng) {
        this.lng = lng;
    }

    public String getJsonStr() {
        return new Gson().toJson(this);
    }
}
