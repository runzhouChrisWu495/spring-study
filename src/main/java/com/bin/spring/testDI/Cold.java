package com.bin.spring.testDI;

import org.springframework.beans.factory.annotation.Qualifier;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR,ElementType.FIELD,ElementType.METHOD,ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Qualifier()    // 自定义标识符注解，用来解决无法靠一个标识符就分辨多个相同类的bean的问题。之所以要自定义是因为Spring旧版本中存在一个类上不能加多个相同注解的问题。
public @interface Cold {
}
