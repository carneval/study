package com.condition;

import com.bean.Rainbow;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {

    /**
     *
     * @param importingClassMetadata 当前类的注解信息
     * @param registry BeanDefinition注册类
     *        把所有需要添加到容器中的bean调用
     *          BeanDefinitionRegistry.registerBeanDefinition()手工注册
     */
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean red = registry.containsBeanDefinition("com.bean.Red");
        boolean blue = registry.containsBeanDefinition("com.bean.Blue");
        if (red && blue) {
            //指定Bean定义信息：Bean的类型/作用域
            RootBeanDefinition definition = new RootBeanDefinition(Rainbow.class);
            //注册一个bean，指定bean名
            registry.registerBeanDefinition("rainbow", definition);
        }
    }
}
