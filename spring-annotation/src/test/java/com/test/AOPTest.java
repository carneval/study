package com.test;

import com.aop.MathCalculator;
import com.config.MainConfigOfAOP;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class AOPTest {

    AnnotationConfigApplicationContext context =
      new AnnotationConfigApplicationContext(MainConfigOfAOP.class);


    @Test
    public void test01() {

        //不要自己创建对象
//        MathCalculator mathCalculator = new MathCalculator();
//        mathCalculator.div(1, 1);
        MathCalculator mathCalculator = context.getBean(MathCalculator.class);
        mathCalculator.div(1, 0);
        context.close();
    }

    private void printBeans(AnnotationConfigApplicationContext context) {
        String[] names = context.getBeanDefinitionNames();
        for (String s : names) {
            System.out.println(s);
        }
    }
}
