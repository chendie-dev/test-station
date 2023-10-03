package com.chendie.teststation.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 
 * </p>
 *
 * @author chendie
 * @since 2023-10-01
 */
@Data
public class Information implements Serializable {

    private static final long serialVersionUID = 2943548141081416718L;

    /**
     * 信息id
     */
    @TableId(value = "information_id", type = IdType.AUTO)
    private Long informationId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 信息标题
     */
    private String title;

    /**
     * 内容
     */
    private String content;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;

    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long updateTime;


}
