package com.chendie.teststation.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * <p>
 *  Long2StringConfig 配置器
 * </p>
 *
 * @author chendie
 * @since 2023-10-01
 */
public class Long2StringConfig implements InitializingBean {

    @Autowired
    private ObjectMapper obj;

    /**
     * 定义bean的时候自定义初始化
     * @throws Exception Exception
     */
    @Override
    public void afterPropertiesSet() throws Exception {
        if (obj != null) {
            SimpleModule simpleModule = getSimpleModule();
            obj.registerModule(simpleModule);
        }
    }

    /**
     * 解决后端向前端返回大Long型数据精度丢失
     */
    private SimpleModule getSimpleModule() {
        // 序列换成json时,将所有的long变成string
        // 因为js中得数字类型不能包含所有的java long值
        SimpleModule simpleModule = new SimpleModule();
        simpleModule.addSerializer(Long.class, ToStringSerializer.instance);
        simpleModule.addSerializer(Long.TYPE, ToStringSerializer.instance);
        return simpleModule;
    }
}