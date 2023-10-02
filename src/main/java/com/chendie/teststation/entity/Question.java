package com.chendie.teststation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
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
public class Question implements Serializable {

    private static final long serialVersionUID = -7993429335544917395L;

    /**
     * 试题id
     */
    @TableId(value = "question_id", type = IdType.AUTO)
    private Long questionId;

    /**
     * 试题标题
     */
    private String questionName;

    /**
     * 选型
     */
    private String options;

    /**
     * 答案
     */
    private String answer;

    /**
     * 题目分析
     */
    private String analysis;

    /**
     * 试题类型
     */
    private Integer type;

    /**
     * 分值
     */
    private Integer score;

    /**
     * 标签id
     */
    private Long tagId;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;


}
