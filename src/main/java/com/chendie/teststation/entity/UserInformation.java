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
@TableName("user_information")
public class UserInformation implements Serializable {

    private static final long serialVersionUID = 215612042737598860L;

    /**
     * id
     */
    @TableId(value = "user_information_id", type = IdType.AUTO)
    private Long userInformationId;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 信息id
     */
    private Long informationId;

    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    private Long createTime;


}
