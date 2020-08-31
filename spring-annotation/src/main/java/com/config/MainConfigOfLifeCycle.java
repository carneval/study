package com.config;

import com.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 一、bean的生命周期：
 *      创建 -- 初始化 -- 销毁
 *      由容器管理
 *
 *      构造（对象创建）
 *          单实例：容器启动的时候创建对象
 *          多实例：每次获取的时候创建对象
 *      初始化：
 *          对象创建完成，并赋值好，调用初始化方法
 *      销毁：
 *          单实例：容器关闭的时候
 *          多实例：容器不会管理bean，所以容器不会销毁，需要手动销毁
 *
 * 二、我们可以自定义初始化和销毁的方法
 *      1）指定初始化和销毁的方法
 *          通过@Bean指定init-method="" destroy-method=""
 *      2）通过Bean实现InitializingBean（定义初始化逻辑）
 *                   DisposableBean（定义销毁逻辑）
 *      3）可以使用JSR250
 *          @PostContruct:在bean创建完成并且属性赋值完成，来执行初始化方法
 *          @PreDestroy:在容器销毁bean之前通知进行清理工作
 *      4）BeanPostProcessor[interface]:bean的后置处理器
 *          在bean的初始化前后进行一些处理工作
 *          postProcessBeforeInitialization:在初始化之前工作
 *          postProcessAfterInitialization:在初始化之后工作
 *
 * 三、BeanPostProcessor原理
 *      1）给bean进行属性赋值
 *          populateBean(beanName, mbd, instanceWrapper);
 *      2）initializeBean(beanName, exposedObject, mbd);
 *      {
 *          applyBeanPostProcessorsBeforeInitialization(bean, beanName);
 *          invokeInitMethods(beanName, wrappedBean, mbd);  --> 执行自定义初始化
 *          applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 *      }
 *
 */
@Configuration
@ComponentScan("com.*")
public class MainConfigOfLifeCycle {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car car () {
        return new Car();
    }
}
