package com.chendie.teststation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * <p>
 * 
 * </p>
 *
 * @author chendie
 * @since 2023-08-26
 */
@Data
@TableName("user")
public class User {

    @TableId(type = IdType.AUTO)
    private Long uid;

    private String username;

    private String pwd;
}
