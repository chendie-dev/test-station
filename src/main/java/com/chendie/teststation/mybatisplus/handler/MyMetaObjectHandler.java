package com.chendie.teststation.mybatisplus.handler;

import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

/**
 *
 * @author chendie
 * @since 2023-10-01
 */
@Component
@Slf4j
public class MyMetaObjectHandler implements MetaObjectHandler {
    /**
     * 插入数据时填充
     * @param metaObject 元数据
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        log.info("MyMetaObjectHandler insertFill start");
        // 创建时间填充
        long now = System.currentTimeMillis();
        setFieldValByName("createTime", now, metaObject);
        setFieldValByName("updateTime", now, metaObject);
    }

    /**
     * 更新数据时填充
     * @param metaObject 元数据
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间填充
        long now = System.currentTimeMillis();
        setFieldValByName("updateTime", now, metaObject);
    }
}
