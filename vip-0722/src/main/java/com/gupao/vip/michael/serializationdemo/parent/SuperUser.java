package com.gupao.vip.michael.serializationdemo.parent;

import java.io.Serializable;

public class SuperUser implements Serializable {
    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    @Override
    public String toString() {
        return "SuperUser{" +
                "age=" + age +
                '}';
    }

    private  int age;
}
