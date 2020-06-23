package com.zhq.exclusivememory.ui.activity.login.bean;

/**
 * Created by Huiqiang Zhang
 * on 2018/11/7.
 */

public class GetSmsCodeBean {
    @Override
    public String toString() {
        return "GetSmsCodeBean{" +
                "key='" + key + '\'' +
                ", expired_at='" + expired_at + '\'' +
                '}';
    }

    /**
     * key : verificationCode_421TQVchMldu72D
     * expired_at : 2018-07-24 15:53:55
     */

    private String key;
    private String expired_at;

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getExpired_at() {
        return expired_at;
    }

    public void setExpired_at(String expired_at) {
        this.expired_at = expired_at;
    }
}
