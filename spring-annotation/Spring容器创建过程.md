#### Spring容器的refresh()【创建刷新】
一、BeanFactory的创建

1. 刷新前的预处理：
    ```prepareRefresh() ```
    
    (1) 初始化一些属性设置；子类自定义个性化的属性设置方法
    ```initPropertySources() ```
    
    (2) 验证属性的合法等
    ```getEnvironment().validateRequiredProperties() ```
  
    (3) 保存容器中的一些早期的事件
    ```earlyApplicationEvents = new LinkedHashedSet<ApplicationEvent>() ```                                                                                                                    

2. 获取BeanFactory
    ```obtainFreshBeanFactory()```
    
    (1) 刷新【创建】BeanFactory
    ```
    refreshBeanFactory()
    创建了一个this.beanFactory = new DefaultListableBeanFactory();
    设置id
    ```
   
   (2) 返回刚才GenericApplicationContext创建的BeanFactory对象
   ```getBeanFactory()```
   
   (3)将创建的BeanFactory[DefaultListableBeanFactory]返回
   
3. BeanFactory的预准备工作（BeanFactory进行一些设置）
    ```prepareBeanFactory(beanFactory)```
    
    (1) 设置BeanFactory的类加载器、支持表达式解析器...
    
    (2) 添加部分
    ```BeanPostProcessor[ApplicationContextAwareProcessor]```
    
    (3) 设置忽略的自动装配的接口
    ```EnvironmentAware、EmbeddedValueResolverAware、xxx```
     
    (4) 注册可以解析的自动装配，我们能直接在任何组件中自动注入
    ```BeanFactory、ResourceLoader、ApplicationEventPublisher、ApplicationContext```
    
    (5) 添加
    ```BeanPostProcessor[ApplicationListenerDetector]```
    
    (6) 添加编译时的
    ```AspectJ```
    
    (7) 给BeanFactory中注册一些能用的组件
    ```
    environment[ConfigurableEnvironment]
    systemProperties[Map<String, Object>]
    systemEnvironment[Map<String, Object>]
    ```
   
4. BeanFactory准备工作完成后进行的后置处理工作
    ```postProcessorBeanFactory```
   
    (1) 子类通过重些这个方法来在BeanFactory创建并预准备完成以后做进一步的设置
    
---------------------------------------以上为预准备工作--------------------------------------------   
   
5. 执行BeanFactoryPostProcessor的方法
    ```
    invokeBeanFactoryPostProcessors(beanFactory)
    BeanFactoryPostProcessor:BeanFactory的后置处理器，在BeanFactory标准初始化之后执行
    两个接口：BeanFactoryPostProcessor、BeanDefinitionRegistryPostProcessor
    ```
   
    (1) 执行BeanFactoryPostProcessor方法
    ```
    [1] 先执行BeanDefinitionRegistryPostProcessor
        *1 获取所有的BeanDefinitionRegistryPostProcessor
        *2 先执行实现了PriorityOrdered优先级接口的BeanDefinitionRegistryPostProcessor
           postProcessor.postProcessBeanDefinitionRegistryPostProcessor(registry);
        *3 再执行实现了Ordered顺序接口的BeanDefinitionRegistryPostProcessor
        *4 最后执行没有实现任何优先级或者顺序接口的BeanDefinitionRegistryPostProcessor
    [2] 再执行BeanFactoryPostProcessor
        *1 获取所有的BeanFactoryPostProcessor
        *2 先执行实现了PriorityOrdered优先级接口的BeanFactoryPostProcessor
           postProcessor.postProcessBeanFactory()
        *3 再执行实现了Ordered顺序接口的BeanFactoryPostProcessor
        *4 最后执行没有实现任何优先级或者顺序接口的BeanFactoryPostProcessor
    ```
   
6. 注册BeanPostProcessor
    ```
    registerBeanPostProcessors(beanFactory)
    Bean的后置处理器【intercept bean creation】
    不同接口类型的BeanPostProcessor在Bean创建前后的执行时机是不一样的
    五个接口：BeanPostProcessor
            DestructionAwareBeanPostProcessor
            InstantiationAwareBeanPostProcessor
            SmartInstantiationAwareBeanPostProcessor
            MergedBeanDefinitionPostProcessor【internalPostProcessors】
    ```
   
    (1) 获取所有的BeanPostProcessor，后置处理器都默认可以通过PriorityOrdered、Ordered接口来执行优先级
    
    (2) 先注册PriorityOrdered优先级接口的BeanPostProcessor，把每一个BeanPostProcessor添加到BeanFactory中
        ```beanFactory.addBeanPostProcessor(postProcessor)```
    
    (3) 再注册Ordered接口的
    
    (4) 然后注册没有实现任何优先级接口的
    
    (5) 再注册MergedBeanDefinitionPostProcessor
    
    (6) 最终注册一个ApplicationListenerDetector，来在Bean创建完成后检查是否是ApplicationListener，如果是
        ```applicationContext.addApplicationListener((ApplicationListener<?>) bean);```
        
7. 初始化MessageSource组件（国际化功能：消息绑定，消息解析）
    ```initMessageSource()```
    
    (1) 获取BeanFactory
    
    (2) 看容器中是否有id为messageSource的，类型是messageSource的组件
        ```
        如果有就赋值给messageSource，如果没有自己创建一个DelegatingMessageSource
        MessageSource：取出国际化配置文件中的某个key的值，能按照区域信息获取
        ```
    
    (3) 把创建号的MessageSource注册在容器中，以后获取国际化配置文件的值的时候，可以自动注入MessageSource
        ```
        beanFactory.registerSingleton(MESSAGE_SOURCE_BEAN_NAME， this.messageSource)
        MessageSource.getMessage(String code, Object[] args, String defaultMessage. Locale locale)
        ```
        
8. 初始化事件派发器
    ```initApplicationEventMulticaster()```
    
    (1) 获取
    ```BeanFactory```
    
    (2) 从BeanFactory中获取
    ```ApplicationEventMulticaster```
    
    (3) 如果上一步没有配置，创建一个
    ```SimpleApplicationEventMulticaster```
    
    (4) 将创建的ApplicationEventMulticaster添加到BeanFactory中，以后其他组件直接自动注入
    
9. 留给子容器（子类）
    ```onRefresh()```
    
    (1) 子类重写这个方法，在容器刷新的时候可以自定义逻辑
    
10. 给容器中所有项目里面的ApplicationListener注册进来
    ```registerListeners()```
    
    (1) 从容器中拿到所有的
    ```ApplicationListener```
    
    (2) 将每个监听器添加到事件派发器中
    ```getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName)```
    
    (3) 派发之前步骤产生的事件
    
11. 初始化所有剩下的单实例Bean
    ```
    finishBeanFactoryInitialization(beanFactory)
    beanFactory.preInstantiateSingletons()
    ```
    
    (1) 获取容器中的所有Bean，一次进行初始化和创建对象
    
    (2) 获取Bean的定义信息
    ```RootBeanDefinition```
    
    (3) Bean不是抽象的、是单实例的、不是懒加载的
    ```
    [1] 判断是否是FactoryBean，是否实现FactoryBean接口的Bean
    [2] 不是工厂Bean，则利用getBean(beanName)创建对象
        *0 getBean(beanName) --> ioc.getBean()
        *1 doGetBean(name, null, null, false)
        *2 先获取缓存中保存的单实例Bean，如果能获取到说明这个Bean之前被创建过（所有创建过的单实例Bean都会被缓存起来）
           private final Map<String, Object> singletonObjects = new ConcurrentHashMap<String, Object>
        *3 缓存中获取不到，开始Bean的创建对象流程
        *4 标记当前bean已经被创建
        *5 获取bean的定义信息
        *6 获取当前bean依赖的其他bean，如果有，按照getBean()把依赖的bean先创建出来
        *7 启动单实例Bean的创建流程
            ·1 createBean(beanName, mbd, args)
            ·2 Object bean = resolveBeforeInstantiation(beanName, mbdToUse)
               让BeanPostProcessor先拦截返回代理对象
               InstantiationAwareBeanPostProcessor提前执行
               先触发：postProcessorBeforeInstantiation()
               如果有返回值，🔛再触发：postProcessorAfterInstantiation()
            ·3 如果前面的InstantiationAwareBeanPostProcessor没有返回代理对象，则调用·4
            ·4 Object beanInstance = doCreateBean(beanName, mbdToUse, args)
               ··1 【创建Bean实例】：createBeanInstance(beanName, mbd, args)
                   利用工厂方法或者对象的构造器创建出bean实例
               ··2 applyMergedBeanDefinitionPostProcessors(mbd, beanType, beanName)
                   调用MergedBeanDefinitionPostProcessor的postProcessMergedBeanDefinition(mbd, beanType, beanName)
               ··3 【Bean属性赋值】populateBean(beanName, mbd, instanceWrapper)
                    赋值之前：
                    ···1 拿到InstantiationAwareBeanPostProcessor后置处理器
                         postProcessAfterInstantiation()
                    ···2 拿到InstantiationAwareBeanPostProcessor后置处理器
                         postProcessPropertyValues()
                    赋值：
                    ···3 应用Bean属性的值，为属性利用setter方法等进行赋值
                         applyPropertyValues(beanName, mbd, bw, pvs)
               ··4 【Bean初始化】initializeBean(beanName, exposedObject, mbd)
                    ···1 执行Aware接口的方法
                         invokeAwareMethods(beanName, bean)
                         BeanNameAware\BeanClassLoaderAware\BeanFactoryAware
                    ···2 执行后置处理器初始化之前的方法
                         applyBeanPostProcessorsBeforeInitialization(warppedBean, beanName)
                         BeanPostProcessor.postProcessBeforeInitialization()
                    ···3 执行初始化方法
                         invokeInitMethods(beanName, wrappedBean, mbd)
                         是否是InitializingBean接口的实现，执行接口规定的初始化
                         是否自定义初始化方法
                    ···4 执行后置处理器初始化之后的方法
                         applyBeanPostProcessorsAfterINitialization()
                         BeanPostProcessor.postProcessAfterInitialization() 
               ··5 注册Bean的销毁方法
            ·5 将创建的Bean添加到缓存中 singletonObjects
            ioc容器其实就是这些Map，很多的Map里面保存了单实例Bean，环境信息...
    ```
    
    (4)所有Bean都利用getBean创建完成以后，减产所有的Bean是否是SmartInitializingSingleton接口的；
       如果是，就执行afterSingletonsInstantiated()
       
12. 完成BeanFactory的初始化创建工作，IOC容器创建完成
    ```finishRefresh()```
    
    (1) 初始化和生命周期有关的后置处理器 LifecycleProcessor
        ```
        initLifecycleProcessor()
        默认从容器中找是否有lifecycleProcessor的组件【LifecycleProcessor】
        如果没有就 new DefaultLifecycleProcessor加入到容器中
        
        写一个LifecycleProcessor的实现类
        实现 onRefresh() / onClose()
        ```
        
    (2) 拿到前面定义的生命周期处理器（BeanFactory），回调onRefresh()
        ```getLifecycleProcessor().onRefresh()```
        
    (3) 发布容器刷新完成事件
        ```publishEvent(new ContextRefreshedEvent(this))```
    
---

#### 总结

1. Spring容器在启动的时候，会先保存所有注册进来的Bean的定义信息
    
    (1) xml注册Bean
    ```<bean>```
    
    (2) 注解注册Bean
    ```@Service、@Component、@Bean、@xxx```
    
2. Spring容器会在合适的时机创建这些Bean

    (1) 用到这个bean的时候，利用getBean创建bean，创建好以后保存在容器中
    
    (2) 统一创建剩下所有的bean的时候
    ```finishBeanFactoryInitialization()```
    
3. 后置处理器
    ```BeanPostProcessor```
    
    (1) 每一个bean创建完成，都会使用各种后置处理器进行处理，来增强bean的功能
    ```
    AutowiredAnnotationBeanPostProcessor：处理自动注入
    AnnotationAwareAspectJAutoProxyCreator：做AOP功能
    AsyncAnnotationBeanPostProcessor
    xxx...
    ```
   
4. 事件驱动模型
    ```
    ApplicationListener：事件监听
    ApplicationEventMulticaster：事件派发
    ```

