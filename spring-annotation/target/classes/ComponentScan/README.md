#1.value
指定要扫描的包名，默认为当前类所在包

#2.excludeFilters
排除不需要被包扫描的类的规则
```java
@Configuration
@ComponentScan(value = "com.bin",excludeFilters =
    @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class, Service.class
    }))
public class MyConfig {
}

@Configuration
@ComponentScan(value = "com.bin",excludeFilters = @ComponentScan.Filter(value = {User.class}))
public class MyConfig {
}

```
其中@Filter注解拥有如下属性：
> FilterType type() default FilterType.ANNOTATION;  需要被排除的类的标识方式
> Class<?>[] value() default {}; 
> @AliasFor("value")
 		Class<?>[] classes() default {};  可以直接根据.class排除类
> String[] pattern() default {};   正则表达式排除

而FilterType则有如下类型
> 1.ANNOTATION  按照注解的规则
> 2.ASSIGNABLE_TYPE  按照给定类型的规则
> 3.ASPECTJ 按照AOP表达式的规则
> 4.REGEX  按照正则表达式规则
> 5.CUSTOM  自定义规则

## 2.1 CUSTOM
 需要实现 TypeFilter 接口 实现 match()
 > 联想记忆：@Conditional中也需要实现Conditional接口中的match()

#3.includeFilters
只扫描的类规则
```java
@Configuration
@ComponentScan(value = "com.bin",includeFilters =
    @ComponentScan.Filter(type = FilterType.ANNOTATION,classes = {Controller.class}))
public class MyConfig {
}
```
这样就只会扫描带有@Controlle注解的类了

#4.useDefaultFilters
是否开启@Component的自动扫描功能，默认为true。如果为false，那么就不会开启扫描功能。