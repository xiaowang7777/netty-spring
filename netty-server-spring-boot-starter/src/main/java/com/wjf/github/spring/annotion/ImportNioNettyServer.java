package com.wjf.github.spring.annotion;

import com.wjf.github.spring.autoregister.AutoRegisterNettyServer;
import com.wjf.github.spring.autoregister.NettyServerProperties;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

public class ImportNioNettyServer implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry, BeanNameGenerator importBeanNameGenerator) {
        registry.registerBeanDefinition("NettyServerPropertiesBean", BeanDefinitionBuilder.rootBeanDefinition(NettyServerProperties.class).getBeanDefinition());
        registry.registerBeanDefinition("AutoRegisterNettyServerBean", BeanDefinitionBuilder.rootBeanDefinition(AutoRegisterNettyServer.class).getBeanDefinition());
    }

    @Override
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        ImportBeanDefinitionRegistrar.super.registerBeanDefinitions(importingClassMetadata, registry);
    }
}
