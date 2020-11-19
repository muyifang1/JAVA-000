学习笔记
## 作业说明 ##
1. spring-bean 三种方式使用注解注入Bean
2. auto-config 模仿例子使用XML方式 注入 Student,Klass,School. 目前存在问题 school中class1=null 还没查出原因
3. jdbc-demo 使用jdbc新增一条student数据 (使用H2 hikari)

# Spring Boot Starter详解 #
- 使用方法：在pom中引入starter依赖
## 具体配置步骤 ##
1. spring.provides 表明这个包对外提供的starter
2. spring.factories 把EnableAutoConfiguration类指向自定义Configration类
3. additional--metadata.json 这个文件最复杂，是真正的配置项(application.properties文件内容被配置到这里)，使用json格式。手写properties配置文件根据这个json内容产生提示信息。
4. 自定义Configuration类 告诉SpringBoot 配置参数会传到这里，这个类再实例化Bean

### 自定义Configuration类 ###
1. 需要加@Configuration注解，表明是一个配置类
2. 需要加@ComponentScan("org.XXX.XXX.spring.boot.converter")需要配置扫描convert
3. 需要加@EnableConfigurationProperties(SpringBootPropertiesConfiguration.class) 打开springproperties开关配置方式，允许使用${port}
4. 重点@ConditionalOnProperty条件配置属性
5. 解决冲突的好方法@AutoConfigureBefore 指定某些配置顺序
