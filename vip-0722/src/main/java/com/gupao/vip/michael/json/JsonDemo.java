package com.gupao.vip.michael.json;

import com.alibaba.fastjson.JSON;
import com.baidu.bjf.remoting.protobuf.Codec;
import com.baidu.bjf.remoting.protobuf.ProtobufProxy;
import com.caucho.hessian.io.HessianInput;
import com.caucho.hessian.io.HessianOutput;
import com.gupao.vip.michael.serializationdemo.Person;
import org.codehaus.jackson.map.ObjectMapper;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

public class JsonDemo {
    //初始化
    private static Person init(){
        Person person=new Person();
        person.setName("tom");
        person.setAge(18);
        return person;
    }

    public static void main(String[] args) throws IOException {
        executeWithJack();
        executeWithFastJson();
        executeWithProtoBuf();
        executeWithHession();
    }
    public static void executeWithJack() throws IOException {
        Person person=init();
        ObjectMapper mapper=new ObjectMapper();
        byte[] writeBytes=null;
        Long start = System.currentTimeMillis();
        for(int i=0;i<100;i++){
            writeBytes=mapper.writeValueAsBytes(person);
        }
        System.out.println("Json序列化："+(System.currentTimeMillis()-start)+"ms:总大小->"+writeBytes.length);
        Person person1=mapper.readValue(writeBytes, Person.class);
        System.out.println(person1);
    }
    public static void executeWithFastJson() throws IOException {
        Person person=init();
        String text=null;
        Long start = System.currentTimeMillis();
        for(int i=0;i<100;i++){
            text=JSON.toJSONString(person);
        }
        System.out.println("Fastjson序列化："+(System.currentTimeMillis()-start)+"ms:总大小->"+text.getBytes().length);
        Person person1 = JSON.parseObject(text, Person.class);
        System.out.println(person1);
    }
    public static void executeWithProtoBuf() throws IOException {
        Person person=init();
        Codec<Person> personCodec=ProtobufProxy.create(Person.class,false);
        byte[] bytes=null;
        Long start = System.currentTimeMillis();
        for(int i=0;i<100;i++){
            bytes=personCodec.encode(person);
        }
        System.out.println("protobuf序列化："+(System.currentTimeMillis()-start)+"ms:总大小->"+bytes.length);
        Person person1 = personCodec.decode(bytes);
        System.out.println(person1);
    }
    public static void executeWithHession() throws IOException {
        Person person=init();
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        HessianOutput ho = new HessianOutput(os);

        byte[] bytes=null;
        Long start = System.currentTimeMillis();
        for(int i=0;i<100;i++){
          ho.writeObject(person);
        }
        System.out.println("hession序列化："+(System.currentTimeMillis()-start)+"ms:总大小->"+os.toByteArray().length);
        HessianInput hi = new HessianInput(new ByteArrayInputStream(os.toByteArray()));
        Person person1 = (Person) hi.readObject();
        System.out.println(person1);
    }
}
