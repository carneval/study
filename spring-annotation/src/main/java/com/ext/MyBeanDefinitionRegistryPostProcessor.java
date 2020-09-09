package com.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {

    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.print("MyBeanDefinitionRegistryPostProcessor...");
        System.out.print("postProcessBeanDefinitionRegistry...");
        int count = beanDefinitionRegistry.getBeanDefinitionCount();
        System.out.println("bean的数量：" + count);
    }

    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.print("MyBeanDefinitionRegistryPostProcessor...");
        System.out.print("postProcessBeanFactory...");
        int count = configurableListableBeanFactory.getBeanDefinitionCount();
        System.out.println("bean的数量：" + count);
    }
}
