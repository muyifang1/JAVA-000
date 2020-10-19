package jvm.demo;

/**
 * Hello JVM Demo
 *
 * @author YangQi
 */
public class HelloJVMDemo {

    private String hello = "Hello";

    private void helloJVM(){
        // when javac with none, <jvm> will be set <var1>
        String jvm = "JVM";

        String desc = "This is test Method!";

        System.out.println(hello + " " + jvm);
        System.out.println(desc);
    }

    public static void main(String[] args) {
        HelloJVMDemo demo = new HelloJVMDemo();
        demo.helloJVM();
    }
}
