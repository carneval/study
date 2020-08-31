package com.test;

import com.bean.Person;
import com.config.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

public class PropertyValuesTest {

    AnnotationConfigApplicationContext context =
      new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);


    @Test
    public void test01() {
        printBeans(context);

        Person person = (Person) context.getBean("person");
        System.out.println(person);

        ConfigurableEnvironment environment = context.getEnvironment();
        String property = environment.getProperty("person.nickName");
        System.out.println(property);
        context.close();
    }

    private void printBeans(AnnotationConfigApplicationContext context) {
        String[] names = context.getBeanDefinitionNames();
        for (String s : names) {
            System.out.println(s);
        }
    }
}
