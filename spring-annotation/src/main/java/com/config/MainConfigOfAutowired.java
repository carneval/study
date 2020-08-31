package com.config;

import com.bean.Car;
import com.bean.Color;
import com.dao.BookDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;


/**
 * 自动装配
 *  Spring利用依赖注入（DI），完成对IOC容器中各个组件的依赖关系赋值
 *
 *  1、@Autowired：自动注入【Spring定义的】
 *      1）默认优先按照类型去容器中找到对应的组件
 *      2）如果找到多个相同类型的组件，再将属性的名称作为组件的id入容器中查找
 *      3）@Qualifier("bookDao")：指定需要装配的组件的id，而不是使用属性名
 *      4）自动装配默认一定要将属性赋值好，没有就会报错
 *         @Autowired(required = false)：装不上不会报错
 *      5）@Primary：让Spring进行自动装配的时候，默认使用首选的bean
 *
 *  2、Spring支持@Resource(JSR250)和@Inject(JSR330)【JAVA规范的注解】
 *      @Resource：自动装配，默认是按照组件名称进行装配
 *          @Resource(name = "bookDao2")：修改名称
 *          没有能支持@Primary功能
 *          没有支持@Autowire(required=false)功能
 *      @Inject:
 *          需要导入javax.inject的包，和Autowired功能一样
 *          没有支持@Autowire(required=false)功能
 *
 *  3、AutowiredAnnotationBeanPostProcessor：解析完成自动装配功能
 *
 *  4、@Autowired：构造器、参数、方法、属性，都是从容器中获取参数组件的值
 *      1）方法：
 *          Spring容器创建当前对象，就会调用方法，完成赋值
 *          @Bean + 方法参数：默认不写@Autowired效果是一样的
 *      2）构造器：
 *          默认加在IOC容器中的组件，容器启动会调用无参构造器创建对象，再进行初始化赋值等操作
 *          如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，参数位置的组件还是可以自动从容器中获取
 *      3）参数
 *
 *  5、自定义组件想要使用Spring容器底层的一些组件（ApplicationContext, BeanFactory, xxx）
 *      自定义组件实现xxxAware：在创建对象的时候，会调用接口规定的方法注入相关组件
 *      把Spring底层的一些组件注入到自定义的Bean中
 *      xxxAware：功能使用xxxProcessor
 */
@ComponentScan({"com.service", "com.dao",
  "com.controller", "com.bean"})
@Configuration
public class MainConfigOfAutowired {

    @Primary
    @Bean("bookDao2")
    public BookDao getBookDao() {
        BookDao dao = new BookDao();
        dao.setLabel("2");
        return dao;
    }

    /**
     * @Bean标注的方法创建对象的时候，方法参数的值从容器中获取
     * @param car
     * @return
     */
    @Bean
    public Color color (Car car) {
        Color color = new Color();
        color.setCar(car);
        return color;
    }
}
