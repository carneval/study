package com.test;

import com.config.MainConfigOfLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class LIfeCycleTest {
    AnnotationConfigApplicationContext context =
      new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);


    @Test
    public void test01() {
        System.out.println("容器创建完成...");
        //关闭容器
        context.close();
    }
}
