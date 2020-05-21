package lib.frame.bean;

import com.google.gson.Gson;

import lib.frame.utils.CipherUtils;

/**
 * Created by lwxkey on 16/7/7.
 */
public class BaseReqBody {

    private String api_code = "common-sendSms";//	API编号
    private String api_key = "d0febc70e739e16740d40e52e12932cf";//	API KEY
    private String api_sign = CipherUtils.md5("api_code=common-sendSms&api_key=d0febc70e739e16740d40e52e12932cf&phone=18664629669&84db0b7f1fdc24c5342f3dbf042e3fb6");//	API签名，具体算法请见下一节点说明

    private String phone = "18664629669";

    public String getApi_code() {
        return api_code;
    }

    public void setApi_code(String api_code) {
        this.api_code = api_code;
    }

    public String getApi_key() {
        return api_key;
    }

    public void setApi_key(String api_key) {
        this.api_key = api_key;
    }

    public String getApi_sign() {
        return api_sign;
    }

    public void setApi_sign(String api_sign) {
        this.api_sign = api_sign;
    }

    public String getJsonStr() {
        return new Gson().toJson(this);
    }

}

