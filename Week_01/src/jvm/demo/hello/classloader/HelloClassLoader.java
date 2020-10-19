package jvm.demo.hello.classloader;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URL;

/**
 * HelloClassLoader for Hello.xlass
 *
 * @author YangQi
 */
public class HelloClassLoader extends ClassLoader {

    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException {
        // 读取 Hello.xlass 文件
        //File file = new File(getClass().getResource("/jvm/demo/hello/classloader/Hello.xlass").getPath());

        File file = new File("C:\\Git_Hub\\JAVA-000\\Week_01\\src\\jvm\\demo\\hello\\classloader\\Hello.xlass");
        byte[] bytes = new byte[Long.valueOf(file.length()).intValue()];

        try {
            // 取每一个byte
            new FileInputStream(file).read(bytes);
        } catch (IOException e) {
            e.printStackTrace();
        }

        // 解码 255-byte
        for (int i = 0; i < bytes.length; ++i) {
            bytes[i] = (byte) (255 - bytes[i]);
        }

        return defineClass(name, bytes, 0, bytes.length);
    }

    public void printUrl(String fileName) {
        URL url = getClass().getResource(fileName);
        System.out.println("url = " + url);
    }

    public static void main(String[] args) {

        // System.out.println("getClass().getResource(\"Hello.xlass\") = " + getClass().getResource("Hello.xlass"));
        // HelloClassLoader demo = new HelloClassLoader();

        try {
            // 创建 Hello.xlass 对象
            Class<?> helloClass = new HelloClassLoader().findClass("Hello");
            Object hello = helloClass.getDeclaredConstructor().newInstance();
            // 执行 hello 方法
            Method helloMethod = helloClass.getMethod("hello");
            helloMethod.invoke(hello);
        } catch (ClassNotFoundException | NoSuchMethodException | InstantiationException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
        }
    }
}
