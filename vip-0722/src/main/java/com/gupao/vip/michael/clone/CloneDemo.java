package com.gupao.vip.michael.clone;

import java.io.IOException;

public class CloneDemo {
    public static void main(String[] args) throws IOException, ClassNotFoundException {
        Teacher teacher=new Teacher();
        teacher.setName("tom");
        Student student=new Student();
        student.setName("张三");
        student.setAge(22);
        student.setTeacher(teacher);


        Student student2=(Student)student.deepClone();//克隆一个对象
        System.out.println(student);
         student2.getTeacher().setName("john");

        System.out.println(student2);
    }
}
