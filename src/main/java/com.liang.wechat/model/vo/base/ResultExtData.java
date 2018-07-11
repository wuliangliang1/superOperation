package com.liang.wechat.model.vo.base;

/**
 * Created by yhj on 16/10/21.
 */
public class ResultExtData<T1,T2> extends Result {

    protected T1 data ;
    protected T2 dataext;

    public ResultExtData() {

    }
    public ResultExtData(Integer code, String message, T1 data) {
        super(code, message);
        this.data = data;
    }
    public ResultExtData(Integer code, String message, T1 data, T2 dataext) {
        super(code, message);
        this.data = data;
        this.dataext = dataext;
    }

    public T2 getDataext() {
        return dataext;
    }

    public T1 getData() {
        return data;
    }

    public void setData(T1 data) {
        this.data = data;
    }

    public void setDataext(T2 dataext) {
        this.dataext = dataext;
    }
}
