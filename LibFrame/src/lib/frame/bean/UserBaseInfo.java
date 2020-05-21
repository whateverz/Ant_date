package lib.frame.bean;

import java.io.Serializable;

/**
 * Created by lwxkey on 2017/6/27.
 */

public abstract class UserBaseInfo implements Serializable {

    public abstract String getNickName();

    public abstract void setNickName(String nickName);

    public abstract String getHeader();

    public abstract void setHeader(String header);

    public abstract long getUid();

    public abstract void setUid(long uid);

    public abstract String getUserId();

    public abstract void setUserId(String userId);

    public abstract String getToken();

    public abstract void setToken(String token);


}
