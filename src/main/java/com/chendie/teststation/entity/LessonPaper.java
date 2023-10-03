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
 * @since 2023-10-03
 */
@Data
@TableName("lesson_paper")
public class LessonPaper implements Serializable {

    private static final long serialVersionUID = -1159765674689712076L;
    /**
     * id
     */
    @TableId(value = "lesson_paper_id", type = IdType.AUTO)
    private Long lessonPaperId;

    /**
     * 班级id
     */
    private Long lessonId;

    /**
     * 试卷id
     */
    @TableField(fill = FieldFill.INSERT)
    private Long paperId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private Long createTime;


}
