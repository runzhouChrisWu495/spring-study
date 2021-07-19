# 1. Bean的默认属性值
```java
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
@Data
class Person{
    String name;    
}

@Configuration
class MyConfig{
    @Bean
    public Person person(){
        return new Person();
    }
}
```
可以看到，像这样Bean是没有默认值的

    1.使用@Value进行赋值
        1.基本数值
        2.SpEL:#{}
        3.${}取配置文件的值
            1.@PropertySource 读取外部配置文件中k/v保存到运行的环境变量中
            2.然后在实体类中使用${}获取
    2.Spring Environment
        1.可以从配置文件中读取属性
```java
import org.springframework.context.annotation.Configuration;import org.springframework.context.annotation.PropertySource;
@Configuration
@PropertySource("classpath:/app.properties")
public class Main{
   @Autowired
    Environment env;

    @Bean
    public Person person(){
        return new Person(env.getPeroperty("person.name"),env.getPeroperty("person.age"));
}
}

```
#2. @Autowire 自动装配

    1.默认优先按照类型去容器中找对应的组件:applicationContext.getBean(BookDao.class);
    2.如果找到多个相同类型Bean，再按照自动注入时 属性的名称 作为组件id去容器中查找:application.getBean("bookDao")
    3.可以使用@Autowired(required=false)配置不存在这个bean时也不报错
    4.@Primary 让Spring自动装配时优先使用带有此注解的bean
    5.可以使用@Qualifier指定需要装配的bean的名字
        @Qualifier("bookDao")
#3. @Resource、@Inject
    Java规范，类似@Autowire实现自动装配功能
    @Resource默认 按照组件名称进行装配。并且无法结合@Qualifier和@Primary
    @Inject需要额外导包，功能可以结合@Qualifier和@Primary，但是这个注解没有属性可以设置(required = false)
    
#4. @Autowired 作用范围
    1.标注在方法上，参数会从容器中获取
        1.setter、任意功能的方法上
        2.@Bena方法中注入另一个bean
    2.构造器
        1.默认调用无参构造器先实例化然后赋值，如果只有有参构造器那么会从容器中获取参数
        2.如果有参构造函数只有一个参数，那么不需要加@Autowired也可以注入
    3，放在方法的参数上（不一定好使）  
    
# 5.Aware
    自定义组件如果想要使用Spring容器底层的一些组件（ApplicationContextAware，BeanFactory）
    自定义组件可以实现xxxAware，在创建对象的时候，会调用结构规定的方法注入
    
    和之前讲述的ApplicationContextAware是由ApplicationContextAwareProcessor后置处理器实现的一样，其他的Aware也是由后置处理器实现的
    
    1.ApplicationContextAware
```java
public class Dog implements ApplicationContextAware {
    private ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
```
    2.BeanNameAware
    可以获得当前Bean的id
```java
@Component
public class Red implements BeanNameAware{
    
    @Override
    public void setBeanName(String name) {
        System.out.println("当前Bean的名字"+name);
    }
}
```

    3.EmbeddedValueResolverAware
    对字符串中的占位进行解析，并返回解析后的结果
```java
@Component
class Blue implements EmbeddedValueResolverAware{
    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String s = resolver.resolveStringValue("你好${os.name}我是#{20*20}");
        System.out.println(s);
    }
}
```

#6.@Profile
    1.作用
        1.标注在bean上，只有当满足@Profile要求的环境时，才会想容器中注入这个bean
        2.标注在配置类上，其内部所有的bean都需要满足要求环境才会注入
        3.没有标注的bean也会注入
    2.用法
        1.JVM运行参数设置 spring.profile.active = dev
        2.在容器环境中配置
```java
class Main{
    public static void main(String[] args){ 
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        context.getEnvirnment().setActiveProfiles("test");
        context.register(MyConfig.class);
        context.refresh();
    }
}
```