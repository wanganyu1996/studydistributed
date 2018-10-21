package demo.classloader;

import org.junit.jupiter.api.Test;

public class TestStaticBlock {
    static {
        System.out.println("static block init");
    }

    @Test
    public void test() {
        //new TestStaticBlock();

        Class<?> class0 = TestStaticBlock.class;
        try {
            System.out.println(class0.getClassLoader() instanceof MyClassLoader);
            Class<?> class1 = class0.getClassLoader().loadClass("demo.classloader.TestStaticBlock");
            ClassLoader classLoader = new MyClassLoader();
            Class<?> class2 = classLoader.loadClass("demo.classloader.TestStaticBlock");

            System.out.println(class1.hashCode());
            System.out.println(class2.hashCode());
            System.out.println(class1.equals(class2));
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }
}
