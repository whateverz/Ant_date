package lib.frame.module.http;


/**
 * Created by lwxkey on 16/7/8.
 */
public abstract class HttpResult<T> {

    public abstract T getResults();

    public abstract String getUserMsg();

    public abstract boolean isSuccess();

    public abstract int getCode();

    public abstract String getMsg();

    public abstract boolean isNeedlogin();

    public static <T> T getResults(HttpResult result) {
        return (T) result.getResults();
    }

}

