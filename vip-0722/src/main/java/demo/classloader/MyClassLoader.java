package demo.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

//自定义一个类加载器从指定磁盘目录加载类
public class MyClassLoader extends ClassLoader {
    //不破坏双亲委派模型
    @Override
    protected Class<?> findClass(String name) {
        String myPath = "D:\\Code\\michaelvip\\vip-0722\\target\\classes\\demo\\classloader\\" + name.replace(".","/") + ".class";
        System.out.println(myPath);
        byte[] classBytes = null;
        FileInputStream in = null;

        try {
            File file = new File(myPath);
            in = new FileInputStream(file);
            classBytes = new byte[(int) file.length()];
            in.read(classBytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        Class<?> clazz = defineClass(name, classBytes, 0, classBytes.length);
        return clazz;
    }
    //破坏双亲委派模型
    @Override
    public Class<?> loadClass(String name)
            throws ClassNotFoundException {
        String myPath = "D:\\Code\\michaelvip\\vip-0722\\target\\classes\\" + name.replace(".","/") + ".class";
        System.out.println(myPath);
        byte[] classBytes = null;
        FileInputStream in = null;

        try {
            File file = new File(myPath);
            in = new FileInputStream(file);
            classBytes = new byte[(int) file.length()];
            in.read(classBytes);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }finally{
            try {
                in.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        System.out.println();
        Class<?> clazz = defineClass(name, classBytes, 0, classBytes.length);
        return clazz;
    }


}


