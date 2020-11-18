package spring.test.service;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import spring.test.bean.Klass;
import spring.test.bean.School;
import spring.test.bean.Student;

public class DiByXmlDemo {

    public static void main(String[] args) {
        ApplicationContext applicationContext = new ClassPathXmlApplicationContext("applicationContext.xml");

        Student student001 = (Student) applicationContext.getBean("student001");
        System.out.println("student001 = " + student001);

        Student student002 = (Student) applicationContext.getBean("student002");
        System.out.println("student002 = " + student002);

        Student student003 = (Student) applicationContext.getBean("student003");
        System.out.println("student003 = " + student003);

        System.out.println("applicationContext.getBean(Klass.class) = " + applicationContext.getBean(Klass.class));
        // todo 这里School 中 class1 = null 。未查出原因？？？
        System.out.println("applicationContext.getBean(School.class) = " + applicationContext.getBean(School.class));
    }
}
