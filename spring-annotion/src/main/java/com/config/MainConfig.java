package com.config;


import com.bean.Person;
import com.service.BookService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @author chenjingyi
 * 配置类
 */

//告诉Spring这是一个配置类
@Configuration
@ComponentScan(value = "com", includeFilters = {
//  @ComponentScan.Filter(type = FilterType.ANNOTATION,
//    classes = {Controller.class}),
//  @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE,
//    classes = {BookService.class},
  @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
}, useDefaultFilters = false)
// @ComponentScan value:指定要扫描的包
// excludeFilters = Filter[] : 指定扫描的时候按照什么规则 排除 哪些组件
// includeFilters = Filter[] : 指定扫描的时候按照什么规则 只要 哪些组件
// FilterType.ANNOTATION : 注解
// FilterType.ASSIGNABLE_TYPE : 给定的类型
// FilterType.ASPECTJ : ASPECTJ表达式
// FilterType.REGEX : 正则表达式
// FilterType.CUSTOM : 自定义规则
public class MainConfig {

    //给容器中注册一个Bean
    //类型为返回值得类型， id默认是用方法名作为id
    @Bean("person")
    public Person person01() {
        return new Person("lisi", 20);
    }

}
