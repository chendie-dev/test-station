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
public class Lesson implements Serializable {

    private static final long serialVersionUID = -4225421794932510368L;

    /**
     * 班级id
     */
    @TableId(value = "lesson_id", type = IdType.AUTO)
    private Long lessonId;

    /**
     * 试卷标题
     */
    private String lessonName;

    /**
     * 创建时间
     */
    private Long createTime;

    /**
     * 更新时间
     */
    private Long updateTime;


}
