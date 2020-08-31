package com.config;

import com.bean.Yellow;
import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;

/**
 * Profile:
 *      Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能
 *      开发环境、测试环境、生产环境
 *      数据源：（/A）（/B）（/C）
 *
 * @Profile:指定组件在哪个环境的情况下才能被注册到容器中；不指定，在任何环境下都能注册这个组件
 *      1）加了环境标识的bean，只有这个环境被激活的时候才能注册到容器中；默认是default环境
 *      2）写在配置类上，只有是指定的环境，整个配置类里面的所有配置才能开始生效
 *      3）没有标注环境标识的bean，在任何环境下都可以加载
 */
//@Profile("test")
@PropertySource("classpath:/dbconfig.properties")
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String user;

    private StringValueResolver valueResolver;

    private String driverClass;

    //@Profile("test")
    @Bean
    public Yellow yellow() {
        return new Yellow();
    }

    @Profile("test")
    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}") String pwd) throws Exception {
        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setUser(user);
        source.setPassword(pwd);
        source.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        source.setDriverClass(driverClass);
        return source;
    }

    @Profile("dev")
    @Bean("devDataSource")
    public DataSource dataSourceDev() throws Exception {
        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setUser(user);
        source.setPassword("123456");
        source.setJdbcUrl("jdbc:mysql://localhost:3306/dev");
        source.setDriverClass(driverClass);
        return source;
    }

    @Profile("prod")
    @Bean("prodDataSource")
    public DataSource dataSourceProd() throws Exception {
        ComboPooledDataSource source = new ComboPooledDataSource();
        source.setUser(user);
        source.setPassword("123456");
        source.setJdbcUrl("jdbc:mysql://localhost:3306/prod");
        source.setDriverClass(driverClass);
        return source;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        this.valueResolver = resolver;
        driverClass = valueResolver.resolveStringValue("${db.driverClass}");
    }
}
