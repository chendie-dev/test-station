package com.chendie.teststation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author: ddgo
 * @description: 统一返回id视图
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdView {

    @ApiModelProperty("id")
    private Long id;
}