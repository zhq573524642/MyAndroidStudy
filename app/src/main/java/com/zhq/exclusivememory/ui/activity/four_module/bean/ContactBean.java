package com.zhq.exclusivememory.ui.activity.four_module.bean;

import android.content.pm.PackageManager;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/21.
 */

public class ContactBean implements Parcelable {

    private String portrait;
    private String name;
    private String phone;

    public String getPortrait() {
        return portrait;
    }

    public void setPortrait(String portrait) {
        this.portrait = portrait;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(portrait);
        dest.writeString(name);
        dest.writeString(phone);
    }

    public static final Parcelable.Creator CREATOR = new Parcelable.Creator<ContactBean>() {

        @Override
        public ContactBean createFromParcel(Parcel source) {
            ContactBean contactBean = new ContactBean();
            contactBean.portrait = source.readString();
            contactBean.name = source.readString();
            contactBean.phone = source.readString();
            return contactBean;
        }

        @Override
        public ContactBean[] newArray(int size) {
            return new ContactBean[size];
        }
    };
}
