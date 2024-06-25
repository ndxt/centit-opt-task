package com.centit.task.config;

import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.spring.context.annotation.config.EnableNacosConfig;
import com.alibaba.nacos.spring.context.annotation.config.NacosPropertySource;
import com.centit.framework.components.impl.NotificationCenterImpl;
import com.centit.framework.config.SpringSecurityDaoConfig;
import com.centit.framework.core.service.DataScopePowerManager;
import com.centit.framework.core.service.impl.DataScopePowerManagerImpl;
import com.centit.framework.dubbo.config.DubboConfig;
import com.centit.framework.dubbo.config.IpServerDubboClientConfig;
import com.centit.framework.jdbc.config.JdbcConfig;
import com.centit.framework.model.adapter.NotificationCenter;
import com.centit.framework.security.StandardPasswordEncoderImpl;
import org.springframework.beans.factory.annotation.AutowiredAnnotationBeanPostProcessor;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.*;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.security.web.csrf.CsrfTokenRepository;
import org.springframework.security.web.csrf.HttpSessionCsrfTokenRepository;
import org.springframework.validation.beanvalidation.LocalValidatorFactoryBean;

@Configuration
@PropertySource("classpath:system.properties")
@ComponentScan(basePackages = {"com.centit"},
    excludeFilters = @ComponentScan.Filter(value = org.springframework.stereotype.Controller.class))

@Import({
    DubboConfig.class,
    IpServerDubboClientConfig.class,
    JdbcConfig.class,
    SpringSecurityDaoConfig.class})
@EnableNacosConfig(globalProperties = @NacosProperties(serverAddr = "${nacos.server-addr}"))
@NacosPropertySource(dataId = "${nacos.system-dataid}",groupId = "CENTIT", autoRefreshed = true)
public class ServiceConfig {

    @Bean
    public AutowiredAnnotationBeanPostProcessor autowiredAnnotationBeanPostProcessor(){
        return new AutowiredAnnotationBeanPostProcessor();
    }

    @Bean(name = "passwordEncoder")
    public StandardPasswordEncoderImpl centitPasswordEncoder() {
        return new StandardPasswordEncoderImpl();
    }

    @Bean
    public DataScopePowerManager queryDataScopeFilter() {
        return new DataScopePowerManagerImpl();
    }

    @Bean
    public NotificationCenter notificationCenter() {
        NotificationCenterImpl notificationCenter = new NotificationCenterImpl();
        notificationCenter.initDummyMsgSenders();
        //notificationCenter.registerMessageSender("innerMsg",innerMessageManager);
        return notificationCenter;
    }


    @Bean
    public CsrfTokenRepository csrfTokenRepository() {
        return new HttpSessionCsrfTokenRepository();
    }

    @Bean
    MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasenames("classpath:i18n/messages", "classpath:org/springframework/security/messages");
        ms.setDefaultEncoding("UTF-8");
        return ms;
    }

    @Bean
    public LocalValidatorFactoryBean validatorFactory() {
        return new LocalValidatorFactoryBean();
    }

}
