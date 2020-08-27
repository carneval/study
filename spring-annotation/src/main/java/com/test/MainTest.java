package com.test;

import com.bean.Person;
import com.config.MainConfig;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


/**
 * @author chenjingyi
 */
public class MainTest {

    public static void main(String[] args) {
        AnnotationConfigApplicationContext context =
          new AnnotationConfigApplicationContext(MainConfig.class);
        Person bean = context.getBean(Person.class);
        System.out.println(bean);

        String[] types = context.getBeanNamesForType(Person.class);
        for (String type : types) {
            System.err.println(type);
        }
    }
}
