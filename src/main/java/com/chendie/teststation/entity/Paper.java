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
public class Paper implements Serializable {

    private static final long serialVersionUID = -393126775578187303L;

    /**
     * 试卷id
     */
    @TableId(value = "paper_id", type = IdType.AUTO)
    private Long paperId;

    /**
     * 试卷标题
     */
    private String title;

    /**
     * 考试时间
     */
    private Long time;

    /**
     * 创建人id
     */
    private Long userId;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 分值
     */
    private Integer totalScore;

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
