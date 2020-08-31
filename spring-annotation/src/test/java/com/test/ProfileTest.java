package com.test;

import com.config.MainConfigOfProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

public class ProfileTest {

    AnnotationConfigApplicationContext context =
      new AnnotationConfigApplicationContext(MainConfigOfProfile.class);

    //1、使用命令行动态参数 在虚拟机参数位置加载 -Dspring-profiles.active=xxx
    //2、代码方式激活某种环境
    @Test
    public void test01() {
        String[] type = context.getBeanNamesForType(DataSource.class);
        for (String s : type) {
            System.out.println(s);
        }

        context.close();
    }


    @Test
    public void test2() {
        //1、创建一个applicationContext
        AnnotationConfigApplicationContext context =
          new AnnotationConfigApplicationContext();
        //2、设置需要激活的环境
        context.getEnvironment().setActiveProfiles("test", "dev");
        //3、注册主配置类
        context.register(MainConfigOfProfile.class);
        //4、启动刷新容器
        context.refresh();
        String[] type = context.getBeanNamesForType(DataSource.class);
        for (String s : type) {
            System.out.println(s);
        }

        context.close();
    }
}
