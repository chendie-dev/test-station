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
@TableName("questions")
public class Questions {

    @TableId(type = IdType.AUTO)
    private Long qid;

    private String content;

    private String typeA;

    private String typeB;

    private String typeC;

    private String typeD;

    private String rightType;

    private Integer value;

    private Long uid;


}
