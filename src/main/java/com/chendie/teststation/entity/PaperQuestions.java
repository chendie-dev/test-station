package com.chendie.teststation.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;

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
@TableName("paper_questions")
public class PaperQuestions {

    @TableId(type = IdType.AUTO)
    private Long pQid;

    private Long pid;

    private Long qid;


}
