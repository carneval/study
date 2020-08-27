package com.config;

import com.bean.Color;
import com.bean.ColorFactoryBean;
import com.bean.Person;
import com.bean.Red;
import com.condition.LinuxCondition;
import com.condition.MyImportBeanDefinitionRegistrar;
import com.condition.MyImportSelector;
import com.condition.WindowsCondition;
import org.springframework.context.annotation.*;

/**
 * @author chenjingyi
 */
// 类中组件统一设置
// 满足当前条件，这个类中配置的所有的bean才能生效
@Conditional({LinuxCondition.class})
@Configuration
//导入组件，id默认是组件的全类名
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {

    /**
     * @Scope
     *  singleton：单实例（默认值）
     *  prototype：多实例
     *  request：同一次请求创建一个实例
     *  session：同一个session创建一个实例
     *
     * @Lazy
     *  单实例bean：默认在容器启动的时候创建对象
     *  懒加载：容器启动不创建对象，第一次使用（获取）bean创建对象，并初始化
     * @return
     */
    @Scope
    @Lazy
    @Bean("person")
    public Person person() {
        return new Person("张三", 25);
    }

    /**
     * @Conditional({Condition})
     *  按照一定的条件进行判断，满足条件给容器中注册bean
     */
    @Conditional({WindowsCondition.class})
    @Bean("bill")
    public Person bill() {
        return new Person("Bill Gates", 62);
    }

    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person linus() {
        return new Person("linus", 48);
    }

    /**
     * 给容器中注册组件：
     * 1、包扫描 + 组件标注注解@Controller/@Service/@Repository/@Component）
     *    局限于自己写的类
     * 2、@Bean
     *    导入的第三方包里面的组件
     * 3、@Import[快速给容器中导入一个组件]
     *    1）@Import(要导入到容器中的组件)，容器就会自动注册这个组件，id默认是全类名
     *    2）ImportSelector:返回需要导入的组件的全类名数组
     *    3）ImportBeanDefinitionRegistrar:手动注册bean到容器中
     * 4、使用Spring提供的FactoryBean(工厂Bean)
     *    1）默认获取到的是工厂bean调用getObject创建的对象
     *    2）要获取工厂Bean本身，需要给id前面加一个&
     *       &colorFactoryBean
     */
    @Bean
    public ColorFactoryBean colorFactoryBean() {
        return new ColorFactoryBean();
    }


}
