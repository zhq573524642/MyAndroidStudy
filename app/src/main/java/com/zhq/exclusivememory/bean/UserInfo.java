package com.zhq.exclusivememory.bean;

import com.google.gson.JsonElement;

/**
 * Created by Huiqiang Zhang
 * on 2019/1/8.
 */
public class UserInfo implements Cloneable {

    private String name;
    private String pwd;
    //存在引用数据类型时
    private Skill skill;

    public UserInfo(String name, String pwd, Skill skill) {
        this.name = name;
        this.pwd = pwd;
        this.skill = skill;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    /*
           注意：
           首先方法的权限修饰符需要更改为public，以便外界调用
           方法的返回值可以更改为当前类的类名
         */
    @Override
    public Object clone() throws CloneNotSupportedException {
//        return super.clone();//深拷贝，不能简单的调用父类的方法
        //先克隆出一个UserInfo对象
        UserInfo userInfo = (UserInfo) super.clone();
        //调用Skill类中的克隆方法，克隆出一个Skill对象
        Skill skill = (Skill) this.skill.clone();
        //将克隆出来的skill赋值给userinfo该对象的成员变量
        userInfo.setSkill(skill);
        //需要把userinfo返回
        return userInfo;
    }


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPwd() {
        return pwd;
    }

    public void setPwd(String pwd) {
        this.pwd = pwd;
    }
}
