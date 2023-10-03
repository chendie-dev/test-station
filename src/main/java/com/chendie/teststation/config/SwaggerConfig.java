package com.chendie.teststation.config;

import com.github.xiaoymin.knife4j.spring.annotations.EnableKnife4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.core.env.Profiles;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;

/**

 */
/**
 * <p>
 *  SwaggerConfig 配置器
 * </p>
 *
 * @author chendie
 * @since 2023-10-01
 * EnableSwagger2 开启自动配置
 * 地址/doc.html knife4j
 * 地址/swagger-ui.html swagger
 */
@Configuration
@EnableSwagger2
@EnableKnife4j
public class SwaggerConfig {

    @Autowired
    private Environment environment;

    /**
     * 配置docket
     * @return Docket
     */
    @Bean
    public Docket docket() {
        // 检测是否为dev环境
        Profiles profiles = Profiles.of("dev");
        boolean isEnable = environment.acceptsProfiles(profiles);
        return new Docket(DocumentationType.SWAGGER_2)
                // 选择是否开启swagger
                .enable(isEnable)
                .apiInfo(apiInfo())
                // 组名
                .groupName("ALL API")
                .select()
                // 选择api显示的范围 any,none,basePackage等里面
                .apis(RequestHandlerSelectors.basePackage("top.ddgotxdy.api.controller"))
                .build();
    }

    private ApiInfo apiInfo() {
        Contact contact = new Contact("ddgo", "ddgotxdy.top", "1812805089@qq.com");
        return new ApiInfo(
                "博客API",
                "博客swagger生成文档",
                "v1.0.0",
                "",
                contact,
                "",
                "",
                new ArrayList<>());
    }
}
