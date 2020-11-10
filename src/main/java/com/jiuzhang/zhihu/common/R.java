package com.jiuzhang.zhihu.common;

import lombok.Data;

import java.io.Serializable;

@Data
public class R<T>  implements Serializable {

    private static final long serialVersionUID = 1L;

    private int code;
    private String msg;
    private boolean success;
    private T data;

    // ------------------------- constructors -------------------------
    public R() {
    }

    private R(ResultCode resultCode, String msg) {
        this(resultCode, (T) null, msg);
    }

    private R(ResultCode resultCode, T data, String msg) {
        this(resultCode.getCode(), data, msg);
    }

    private R(int code, T data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
        this.success = ResultCode.SUCCESS.code == code;
    }

    // ------------------------- 静态工厂方法 -------------------------
    public static <T> R<T> success(String msg) {
        return new R(ResultCode.SUCCESS, msg);
    }

    public static <T> R<T> fail(int code, String msg) {
        return new R(code, (Object)null, msg);
    }

    public static <T> R<T> data(T data) {
        return new R(ResultCode.SUCCESS, data, "查询成功");
    }

    public String toString() {
        return "R(code=" + this.getCode() + ", success=" + this.isSuccess() + ", data=" + this.getData() + ", msg=" + this.getMsg() + ")";
    }
}
