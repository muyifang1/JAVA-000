package spring.test.service;

import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionReaderUtils;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.util.StringUtils;
import spring.test.bean.User;

import static org.springframework.beans.factory.support.BeanDefinitionBuilder.genericBeanDefinition;

/**
 * 使用了三种注解方式注入Bean User
 *
 * @author YangQi
 */
public class AnnotationBeanDemo {

    public static void main(String[] args) {

        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();

        applicationContext.register(AnnotationBeanDemo.class);

        applicationContext.refresh();

        System.out.println("applicationContext.getBean(\"user\") = " + applicationContext.getBean("user"));

        // 命名Bean的注册方式
        registerUserBeanDefinition(applicationContext, "add-YangQi-User");
        System.out.println("applicationContext.getBean(\"add-YangQi-User\") = " + applicationContext.getBean("add-YangQi-User"));
        // 非命名Bean的注册方式
        registerUserBeanDefinition(applicationContext);
        // beanname 自动生成的 "spring.test.bean.User#0"
        System.out.println("applicationContext.getBean(\"spring.test.bean.User#0\") = " + applicationContext.getBean("spring.test.bean.User#0"));

        System.out.println("applicationContext.getBeansOfType(User.class) = " + applicationContext.getBeansOfType(User.class));

        applicationContext.close();
    }

    /**
     * 通过注解定义一个Bean
     *
     * @return user
     */
    @Bean(name = {"user", "yangqi-user"})
    public User user() {
        User user = new User();
        user.setId(28);
        user.setName("YangQi");
        return user;
    }

    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry) {
        registerUserBeanDefinition(registry, null);
    }

    public static void registerUserBeanDefinition(BeanDefinitionRegistry registry, String beanName) {
        BeanDefinitionBuilder beanDefinitionBuilder = genericBeanDefinition(User.class);
        beanDefinitionBuilder
                .addPropertyValue("id", 29)
                .addPropertyValue("name", "氧气");

        if (StringUtils.hasText(beanName)) {
            registry.registerBeanDefinition(beanName, beanDefinitionBuilder.getBeanDefinition());
        } else {
            // 如果beanName为空，使用非命名bean注册方法
            BeanDefinitionReaderUtils.registerWithGeneratedName(beanDefinitionBuilder.getBeanDefinition(), registry);
        }
    }

}
