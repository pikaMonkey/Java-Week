package com.pkh.bean.response;

import java.io.Serializable;

public class PikaResponse<T> implements Serializable {
    private static final long serialVersionUID = 1L;
    private T data;
    private String message;
    private String code;

    public PikaResponse() {
        this.code = "0";
    }

    public PikaResponse(T data) {
        if (data == null) {
            this.code = "0";
        } else {
            this.code = "0";
            this.data = data;
        }
        this.message = "success";
    }

    public PikaResponse(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
