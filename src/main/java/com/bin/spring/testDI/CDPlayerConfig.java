package com.bin.spring.testDI;

import org.springframework.context.annotation.*;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;

@Configuration      // Full模式，其proxyBeanMethods属性可以指定是单例还是原型
@ComponentScan      // 扫描同包下的所有component注解，可以设置属性basePackage来指定扫描的包
@ImportResource("classpath:spring/beans.xml")       // 从XML文件中导入bean
@PropertySource("classpath:Properties/app.properties")      // 导入外部属性源，可以通过注入Environment bean来查看属性源中的内容
public class CDPlayerConfig {
//    @Bean
//    public SgtPeppers SgtPeppers(){       // 用Java配置的方式注册bean的时候，可以完全按照Java的语法来随意写，只要最后返回一个对象即可。
//        Class<SgtPeppers> peppersClass = SgtPeppers.class;
//        SgtPeppers sgtPeppers = null;
//        try {
//            sgtPeppers = peppersClass.newInstance();
//        } catch (InstantiationException e) {
//            e.printStackTrace();
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        }finally {
//            return sgtPeppers;
//        }
//    }
//    @Bean     // 这个注解两个作用：1.表明这个方法会返回一个bean   2.拦截调用，只会返回一个被增强过后的单例对象（Full模式才是）
//    public CDPlay cdPlay(){       // 在Java配置的形式中，如果一个bean依赖了其他bean，那么可以在参数中调用别的bean的方法（在Lite模式每次都会new新的对象，而Full模式则是单例）
//        return new CDPlay(SgtPeppers());
//    }

    @Override
    public String toString() {
        return "haha";
    }

//    @Bean
//    public PropertySourcesPlaceholderConfigurer placeholderConfigurer(){      // Spring实战中，说AOP必须要注入这个bean，但我发现不用注入也可以，可能是版本不一致
//        return new PropertySourcesPlaceholderConfigurer();
//    }
}
