package com.chendie.teststation.entity;

import com.baomidou.mybatisplus.annotation.*;
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
@TableName("paper_question")
public class PaperQuestion implements Serializable {

    private static final long serialVersionUID = 1559260732745910773L;

    /**
     * 试题id
     */
    @TableId(value = "paper_question_id", type = IdType.AUTO)
    private Long paperQuestionId;

    /**
     * 试卷id
     */
    private Long paperId;

    /**
     * 试卷id
     */
    private Long questionId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;


}
