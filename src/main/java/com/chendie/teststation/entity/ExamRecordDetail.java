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
@TableName("exam_record_detail")
public class ExamRecordDetail implements Serializable {

    private static final long serialVersionUID = -8862607534672764249L;

    /**
     * 考试记录id
     */
    @TableId(value = "detail_id", type = IdType.AUTO)
    private Long detailId;

    /**
     * 考试记录id
     */
    private Long recordId;

    /**
     * 试题id
     */
    private Long questionId;

    /**
     * 成绩
     */
    private Integer store;

    /**
     * 回答
     */
    private String reply;

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
