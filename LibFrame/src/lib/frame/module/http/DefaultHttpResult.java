package lib.frame.module.http;

import java.io.Serializable;

/**
 * Created by shuaqq on 2017/1/14.
 */

public class DefaultHttpResult<T> extends HttpResult<T> implements Serializable {

    protected int CODE_NEED_RELOGIN = HttpHelper.CODE_NEED_RELOGIN;

    private String apiVersion;//	API版本，备用
    private String apiCode;//	API编号
    private boolean isSuccess;//	是否成功，表示业务上成功与否，客户端优先判断此标识，并做出相应动作。下一步动作结合具体交互流程，可能是toast消息，那么就取userMsg；可能是打开页面，就根据code来判断。
    private int code;//	消息代码，适用于客户端根据成功标识做一些比如打开页面动作的判断依据。
    private String msg;//	消息说明，内部使用
    private String userMsg;//	消息说明，显示给用户，适用于toast。
    private int count;//	数据总条数，用于分页
    private int totalPages;//	数据总页数，用于分页
    private int curPage;//	当前页码，用于分页
    private int pageSize;//	每页多少条，用于分页
    private T results;//	数据集，它是一个泛型，没有确定的数据类型，每个API都不一样。可以是空，可以是字符串，可以是数值，可以是JSON对象，可以是JSON数组。

    public String getApiVersion() {
        return apiVersion;
    }

    public void setApiVersion(String apiVersion) {
        this.apiVersion = apiVersion;
    }

    public String getApiCode() {
        return apiCode;
    }

    public void setApiCode(String apiCode) {
        this.apiCode = apiCode;
    }

    public boolean isSuccess() {
        return isSuccess;
    }

    public void setSuccess(boolean success) {
        isSuccess = success;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public boolean isNeedlogin() {
        return code == CODE_NEED_RELOGIN;
    }

    public boolean isMore() {
        return false;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getUserMsg() {
        return userMsg;
    }

    public void setUserMsg(String userMsg) {
        this.userMsg = userMsg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurPage() {
        return curPage;
    }

    public void setCurPage(int curPage) {
        this.curPage = curPage;
    }

    public int getPageSize() {
        return pageSize;
    }

    public void setPageSize(int pageSize) {
        this.pageSize = pageSize;
    }

    public T getResults() {
        return results;
    }

    public void setResults(T results) {
        this.results = results;
    }
}
