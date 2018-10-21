package com.gupao.vip.michael.serializationdemo;

import com.baidu.bjf.remoting.protobuf.FieldType;
import com.baidu.bjf.remoting.protobuf.annotation.Protobuf;

import java.io.Serializable;

public class Person implements Serializable{

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    //private static final long serialVersionUID = -5813478124534894684L;
    @Protobuf(fieldType = FieldType.STRING,order = 1)
    public   String name;
    @Protobuf(fieldType = FieldType.INT32,order = 2)
    private int age;
    public static int height=2;
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }
}
