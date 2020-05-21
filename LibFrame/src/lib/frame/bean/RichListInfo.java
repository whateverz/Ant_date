package lib.frame.bean;

import java.io.Serializable;
import java.util.List;

/**
 * Created by lwxkey on 2017/4/11.
 */

public class RichListInfo<A, B> implements Serializable {

    private A detail;

    private List<B> list;

    public A getDetail() {
        return detail;
    }

    public void setDetail(A detail) {
        this.detail = detail;
    }

    public List<B> getList() {
        return list;
    }

    public void setList(List<B> list) {
        this.list = list;
    }
}
