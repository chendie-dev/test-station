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
@TableName("user_paper")
public class UserPaper implements Serializable {

    private static final long serialVersionUID = 3174589643982752700L;

    /**
     * id
     */
    @TableId(value = "user_paper_id", type = IdType.AUTO)
    private Long userPaperId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 试卷id
     */
    private Long paperId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;


}
