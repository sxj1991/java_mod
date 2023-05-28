package com.xjsun.swagger.config;

import io.swagger.annotations.ApiOperation;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.oas.annotations.EnableOpenApi;
import springfox.documentation.service.*;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.Docket;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * swagger配置类 配置扫描的路径和基础信息
 * 启动报错 ： 使用EnableWebMvc注解 启用Spring MVC框架的默认配置或者配置文件配置匹配信息
 *（目的是解决因为springboot新版本的路径匹配使用了PathPatternParser，而swagger一直没有更新，还使用的AntPathMatcher）
 */
@Configuration
@EnableOpenApi
public class SwaggerConfig {
    /**
     * 与Spring 同一级的配置
     * swagger:
     * enable: true
     * application-name: ${spring.application.name}
     * application-version: 1.0
     * application-description: springfox swagger 3.0整合Demo
     * try-host: <a href="http://localhost:$">...</a>{server.port}
     */
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .protocols(buildProtocols())
                // 请求头 token
                .securityContexts(List.of(securityContext()))
                .securitySchemes(List.of(apiKey()))
                // 是否开启 实际中可配置文件注入灵活控制
                .enable(true)
                .select()
                // 扫描的路径使用@Api的controller
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                // 指定路径处理PathSelectors.any()代表所有的路径
                .paths(PathSelectors.any())
                .build();

    }


    /**
     * swagger 介绍信息
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger3接口文档")
                .description("适用于前后端分离统一的接口文档")
                //作者信息
                .contact(new Contact("xjsun", "www.baidu.com,", "xjsun@gmail.com"))
                .version("1.0")
                .build();
    }

    /**
     * 自定义一个Apikey
     * 这是一个包含在header中键名为Authorization的JWT标识
     */
    private ApiKey apiKey() {
        return new ApiKey("Authorization", "Authorization", "header");
    }

    /**
     * 配置JWT的SecurityContext 并设置全局生效
     */
    private SecurityContext securityContext() {
        return SecurityContext.builder().securityReferences(defaultAuth()).build();
    }

    private List<SecurityReference> defaultAuth() {
        AuthorizationScope authorizationScope = new AuthorizationScope("global", "accessEverything");

        return List.of(new SecurityReference("Authorization",
                new AuthorizationScope[]{authorizationScope}));
    }


    /**
     * 配置支持的协议
     */
    private Set<String> buildProtocols() {
        HashSet<String> protocols = new HashSet<>(2);
        protocols.add("http");
        return protocols;
    }
}