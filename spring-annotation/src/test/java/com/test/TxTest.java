package com.test;

import com.aop.MathCalculator;
import com.config.MainConfigOfAOP;
import com.config.MainConfigOfTx;
import com.tx.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class TxTest {

    AnnotationConfigApplicationContext context =
      new AnnotationConfigApplicationContext(MainConfigOfTx.class);


    @Test
    public void test01() {

        UserService service = context.getBean(UserService.class);
        service.insertUser();
        context.close();
    }

    private void printBeans(AnnotationConfigApplicationContext context) {
        String[] names = context.getBeanDefinitionNames();
        for (String s : names) {
            System.out.println(s);
        }
    }
}
