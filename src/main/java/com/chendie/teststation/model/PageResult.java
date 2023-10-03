package com.chendie.teststation.model;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.util.List;

/**
 * <p>
 *  分页结果
 * </p>
 *
 * @author chendie
 * @since 2023-10-01
 */
@ApiModel("分页结果")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class PageResult<T> {
    @ApiModelProperty("总页数")
    private Long totalPage;
    @ApiModelProperty("返回结果")
    private List<T> data;
}
