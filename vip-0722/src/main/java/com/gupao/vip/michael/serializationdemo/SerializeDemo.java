package com.gupao.vip.michael.serializationdemo;

import java.io.*;

public class SerializeDemo {
    public static void main(String[] args) {
        //序列化操作
        SerializePerson();
         Person.height=5;
        //反序列化操作
       Person person= DeSerializePerson();
        System.out.println(person);
    }
    private static void SerializePerson(){
        try {
            ObjectOutputStream  oo=new ObjectOutputStream(new FileOutputStream(new File("person")));
            Person person=new Person();
            person.setAge(19);
            person.setName("John");
            oo.writeObject(person);
            oo.flush();
            oo.writeObject(person);
            oo.flush();
            System.out.println("序列化成功"+new File("person").length());
            ObjectInputStream   ois=new ObjectInputStream(new FileInputStream(new File("person")));
            Person person1= (Person) ois.readObject();
            Person person2= (Person) ois.readObject();
            System.out.println(person1==person2);
            oo.close();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
    private  static Person DeSerializePerson(){
        try {
            ObjectInputStream   ois=new ObjectInputStream(new FileInputStream(new File("person")));
           Person person= (Person) ois.readObject();
           return person;
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

}
