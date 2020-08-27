package com.test;

import com.bean.Person;
import com.config.MainConfig;
import com.config.MainConfig2;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

public class IOCTest {

    AnnotationConfigApplicationContext context =
      new AnnotationConfigApplicationContext(MainConfig2.class);

    @SuppressWarnings("resource")
    @Test
    public void test01() {
        String[] definitionNames = context.getBeanDefinitionNames();
        for (String name : definitionNames) {
            System.out.println(name);
        }
    }

    @Test
    public void test02() {
        String[] definitionNames = context.getBeanDefinitionNames();
//        for (String name : definitionNames) {
//            System.out.println(name);
//        }

        //默认是单实例的
        Object person = context.getBean("person");
        Object person2 = context.getBean("person");
        System.out.println(person == person2);
    }

    @Test
    public void test03() {
        String[] names = context.getBeanNamesForType(Person.class);
        ConfigurableEnvironment environment = context.getEnvironment();
        //获取环境变量的值
        String property = environment.getProperty("os.name");
        System.out.println("环境--> " + property);
        for (String s: names) {
            System.out.println(s);
        }

        Map<String, Person> type = context.getBeansOfType(Person.class);
        System.out.println(type);
    }

    @Test
    public void testImport() {
        printBeans(context);

        //工厂Bean获取的是调用getObject创建的对象
        Object bean = context.getBean("colorFactoryBean");
        System.out.println("bean的类型：" + bean.getClass());

        //加&前缀能获取到工厂本身
        Object bean2 = context.getBean("&colorFactoryBean");
        System.out.println("bean的类型：" + bean2.getClass());
    }

    private void printBeans(AnnotationConfigApplicationContext context) {
        String[] names = context.getBeanDefinitionNames();
        for (String s : names) {
            System.out.println(s);
        }
    }
}
