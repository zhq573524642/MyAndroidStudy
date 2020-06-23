package com.zhq.exclusivememory.bean;

/**
 * Created by Huiqiang Zhang
 * on 2020/4/2.
 */

public class Skill implements  Cloneable{

    private String skillName;

    public Skill(String skillName) {
        this.skillName = skillName;
    }

    @Override
    public Object clone() throws CloneNotSupportedException {
        return super.clone();
    }

    public String getSkillName() {
        return skillName;
    }

    public void setSkillName(String skillName) {
        this.skillName = skillName;
    }
}
