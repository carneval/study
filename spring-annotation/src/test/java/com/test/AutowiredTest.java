package com.test;

import com.bean.Boss;
import com.bean.Color;
import com.config.MainConfigOfAutowired;
import com.service.BookService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AutowiredTest {

    AnnotationConfigApplicationContext context =
      new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);


    @Test
    public void test01() {
        BookService bean = context.getBean(BookService.class);
        System.out.println(bean);

        Boss boss = context.getBean(Boss.class);
        System.out.println(boss);
        Color color = context.getBean(Color.class);
        System.out.println(color);
        context.close();
    }

    private void printBeans(AnnotationConfigApplicationContext context) {
        String[] names = context.getBeanDefinitionNames();
        for (String s : names) {
            System.out.println(s);
        }
    }
}
