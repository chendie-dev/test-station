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
@TableName("exam_record")
public class ExamRecord implements Serializable {

    private static final long serialVersionUID = 6044162936102609143L;

    /**
     * 考试记录id
     */
    @TableId(value = "record_id", type = IdType.AUTO)
    private Long recordId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 试卷id
     */
    private Long paperId;

    /**
     * 成绩
     */
    private Integer grade;

    /**
     * 使用时间
     */
    private Long useTime;

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
