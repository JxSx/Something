package com.yolo.myapplication.databinding;

import java.util.List;

/**
 * @author: jiaxin
 * @date: 2017-03-03 15:30
 */

public class User {

    private String name;
    private int age;
    private boolean girl;

    private List<Course> courses;

    public List<Course> getCourses() {
        return courses;
    }

    public void setCourses(List<Course> courses) {
        this.courses = courses;
    }

    public boolean isGirl() {
        return girl;
    }

    public void setGirl(boolean girl) {
        this.girl = girl;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

}
