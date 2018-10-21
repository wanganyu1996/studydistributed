package com.gupao.vip.michael.serializationdemo.parent;


import java.io.*;

public class SuperDemo {
    public static void main(String[] args) {
        //序列化操作
        SerializePerson();
        User user=DeSerializePerson();
        System.out.println(user);
    }
    private static void SerializePerson(){
        try {
            ObjectOutputStream oo=new ObjectOutputStream(new FileOutputStream(new File("person")));
            User user=new User();
            user.setAge(19);
            oo.writeObject(user);
            System.out.println("序列化成功");
            oo.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private  static User DeSerializePerson(){
        try {
            ObjectInputStream ois=new ObjectInputStream(new FileInputStream(new File("person")));
            User user= (User) ois.readObject();
            return user;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
