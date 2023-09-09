package com.lazzy.base.spingExpand;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 *
 *  SpringBoot项目中配置文件是必不可少的，随着配置的大量增加，如果将所有的配置都放在框架自带的application.properties文件中，
 *  就会导致该过度臃肿且分类不够明确，为解决这一问题，我们常常需要自定义配置文件，此时就会带来一个问题，那就是自定配置文件无法被框架所识别。
 *  spring-boot-configuration-processor 库解决上述问题
 * ————————————————
 */
@Configuration
@ConfigurationProperties(prefix = "custom.config")
@Setter
@Getter
public class CustomConfiguration {

    private int password;

    private String name;

    private int port;

    private String host;

}