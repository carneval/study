#### 组件注册
```
1. 包扫描 + 组件标注注解
    @Controller/@Service/@Repository/@Component
    局限于自己写的类
2. @Bean
    导入的第三方包里面的组件
3. @Import[快速给容器中导入一个组件]
    1）@Import(要导入到容器中的组件)，容器就会自动注册这个组件，id默认是全类名
    2）ImportSelector:返回需要导入的组件的全类名数组
    3）ImportBeanDefinitionRegistrar:手动注册bean到容器中
4. 使用Spring提供的FactoryBean(工厂Bean)
    1）默认获取到的是工厂bean调用getObject创建的对象
    2）要获取工厂Bean本身，需要给id前面加一个&
       &colorFactoryBean
```

#### 生命周期
```
一、bean的生命周期：
     创建 -- 初始化 -- 销毁
     由容器管理

     构造（对象创建）
         单实例：容器启动的时候创建对象
         多实例：每次获取的时候创建对象
     初始化：
         对象创建完成，并赋值好，调用初始化方法
     销毁：
         单实例：容器关闭的时候
         多实例：容器不会管理bean，所以容器不会销毁，需要手动销毁

二、我们可以自定义初始化和销毁的方法
     1）指定初始化和销毁的方法
         通过@Bean指定init-method="" destroy-method=""
     2）通过Bean实现InitializingBean（定义初始化逻辑）
                  DisposableBean（定义销毁逻辑）
     3）可以使用JSR250
         @PostContruct:在bean创建完成并且属性赋值完成，来执行初始化方法
         @PreDestroy:在容器销毁bean之前通知进行清理工作
     4）BeanPostProcessor[interface]:bean的后置处理器
         在bean的初始化前后进行一些处理工作
         postProcessBeforeInitialization:在初始化之前工作
         postProcessAfterInitialization:在初始化之后工作 

三、BeanPostProcessor原理
     1）给bean进行属性赋值
         populateBean(beanName, mbd, instanceWrapper);
     2）initializeBean(beanName, exposedObject, mbd);
       {
         applyBeanPostProcessorsBeforeInitialization(bean, beanName);
         invokeInitMethods(beanName, wrappedBean, mbd);  --> 执行自定义初始化
         applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
       }

```

#### 属性赋值
```
一、@Value
    1、基本数值
       @Value("**")
    2、可以写SpEL: #{}
       @Value("#{20 - 2}")
    3、可以写${}: 取出配置文件[properties]中的值（在运行环境变量里面的值）
       @Value("${**.**}")
二、@PropertySource
    在**.properties文件中配置属性值**.**
    @PropertySource(value = {"classpath:/**.properties"})
```

#### 自动装配
```
Spring利用依赖注入（DI），完成对IOC容器中各个组件的依赖关系赋值

一、@Autowired：自动注入【Spring定义的】
    1）默认优先按照类型去容器中找到对应的组件
    2）如果找到多个相同类型的组件，再将属性的名称作为组件的id入容器中查找
    3）@Qualifier("bookDao")：指定需要装配的组件的id，而不是使用属性名
    4）自动装配默认一定要将属性赋值好，没有就会报错
       @Autowired(required = false)：装不上不会报错
    5）@Primary：让Spring进行自动装配的时候，默认使用首选的bean

二、Spring支持@Resource(JSR250)和@Inject(JSR330)【JAVA规范的注解】
    @Resource：自动装配，默认是按照组件名称进行装配
        @Resource(name = "bookDao2")：修改名称
        没有能支持@Primary功能
        没有支持@Autowire(required=false)功能
    @Inject:
        需要导入javax.inject的包，和Autowired功能一样
        没有支持@Autowire(required=false)功能

三、AutowiredAnnotationBeanPostProcessor：解析完成自动装配功能

四、@Autowired：构造器、参数、方法、属性，都是从容器中获取参数组件的值
    1）方法：
        Spring容器创建当前对象，就会调用方法，完成赋值
        @Bean + 方法参数：默认不写@Autowired效果是一样的
    2）构造器：
        默认加在IOC容器中的组件，容器启动会调用无参构造器创建对象，再进行初始化赋值等操作
        如果组件只有一个有参构造器，这个有参构造器的@Autowired可以省略，参数位置的组件还是可以自动从容器中获取
    3）参数

五、自定义组件想要使用Spring容器底层的一些组件（ApplicationContext, BeanFactory, xxx）
    自定义组件实现xxxAware：在创建对象的时候，会调用接口规定的方法注入相关组件
    把Spring底层的一些组件注入到自定义的Bean中
    xxxAware：功能使用xxxProcessor

六、Profile:
    Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能
        开发环境、测试环境、生产环境
        数据源：（/A）（/B）（/C）
   
   @Profile:指定组件在哪个环境的情况下才能被注册到容器中；不指定，在任何环境下都能注册这个组件
        1）加了环境标识的bean，只有这个环境被激活的时候才能注册到容器中；默认是default环境
        2）写在配置类上，只有是指定的环境，整个配置类里面的所有配置才能开始生效
        3）没有标注环境标识的bean，在任何环境下都可以加载

   1、使用命令行动态参数 在虚拟机参数位置加载 -Dspring-profiles.active=xxx
   2、代码方式激活某种环境
        1）创建一个applicationContext
            AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext();
        2）设置需要激活的环境
            context.getEnvironment().setActiveProfiles("test", "dev");
        3）注册主配置类
            context.register(MainConfigOfProfile.class);
        4）启动刷新容器
            context.refresh();
```