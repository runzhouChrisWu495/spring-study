package com.bin.spring.action5;


import com.bin.spring.action5.converters.GuiGuMessageConverter;
import com.bin.spring.action5.interceptor.MyInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.core.convert.converter.Converter;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.List;


// 通过@Configuration和注入webMvcConfigurer Bean实现自定义配置
// 自定义POJO的解析如果有特殊的约定，那么就需要自定义解析器
@Configuration
public class MyConfig {
    @Bean
    public WebMvcConfigurer webMvcConfigurer(){
        return new WebMvcConfigurer() {

            // MyConvertTest 自定义Convert，默认情况下是可以将网页的String解析为Bean的，但如果String是特殊格式就无法解析，这样就需要我们自定义converter
            @Override
            public void addFormatters(FormatterRegistry registry) {
                registry.addConverter(new Converter<String, Car>() {
                    @Override
                    public Car convert(String source) {
                        if(!StringUtils.isEmpty(source)){
                            Car car = new Car();
                            String[] split = source.split(",");
                            car.setBrand(split[0]);
                            car.setPrice(Integer.parseInt(split[1]));
                            return car;
                        }
                        return null;
                    }
                });
            }

            @Override
            public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
                    converters.add(new GuiGuMessageConverter());
            }

            @Override
            public void addInterceptors(InterceptorRegistry registry) {
                registry.addInterceptor(new MyInterceptor());
//                        .addPathPatterns("/**")
//                        .excludePathPatterns("/","/login","fonts");
            }
        };
    }
}
