package com.bin.spring.testDI;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ThreadFactory;

@Conditional(TrueCondition.class)   // 只有当此注解中实现了Condition接口的类中的match方法返回true时，才会创建这个bean
@Component("CDPlay")
@Profile("dev")         // 只有当处于dev模式时才会创建这个bean，而现在处于什么模式由spring.profiles.active决定，可以在JVM、文件中设置然后读取
@Primary        // 解决自动装配时的歧义问题的另一种方法，给相同标识符的bean不同的优先级，有此注解的bean会优先被注入
@Qualifier("CDPlay")    // 解决自动装配时的歧义问题的一种方法，value属性为标识符。在注入时可以再使用一遍这个注解，指定使用这个标识符的bean
@Scope(ConfigurableBeanFactory.SCOPE_SINGLETON)     // 设置这个bean的作用域，有原型与单例
public class CDPlay {
//    @Autowired
    public SgtPeppers st;
    DVDPlayer dvd;

    @Autowired      // 对于集合的bean可以直接注入，不会存在问题，而在XML中就需要单独的<list>、<set>、<map>标签了。
    List<Track> track;

    public CDPlay(SgtPeppers st) {
        this.st = st;
    }

    public CDPlay() {
    }

    @Autowired  // @Autowired 注解会尽量满足域和构造器、方法中参数的要求，注入一个bean。如果required = false,则没有这样的bean也可以。
    private void setSt(SgtPeppers st,DVDPlayer dvd) {   // @Autowired也可以作用在方法的参数上，不过在Spring框架中，几乎没什么地方有效。而Autowired可以根据类型、bean名字来注入
        this.st = st;
        this.dvd = dvd;
    }

//        @Autowired
    public void ac(){
        System.out.println("A");
        dvd.play();
        st.play();
//        track.forEach(e->{
//            for (String s : e.tracks) {
//                System.out.println(s);
//            }
//        });
    }
}
