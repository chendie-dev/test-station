package com.chendie.teststation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.Valid;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import java.util.LinkedHashMap;

/**
 * <p>
 *  分页查询参数
 * </p>
 *
 * @author chendie
 * @since 2023-10-01
 */
@Data
public class PageQry<QUERY_PARAM> {

    @ApiModelProperty("查询第x页")
    @Min(value = 1, message = "页数错误")
    @Max(value = 1000, message = "页数错误错误")
    private int pageNum;

    @ApiModelProperty("每页数据量")
    @Min(value = 1, message = "每页数据量错误")
    @Max(value = 100, message = "每页数据量错误")
    private int pageSize;

    @ApiModelProperty("查询条件")
    @Valid
    private QUERY_PARAM queryParam;

    @ApiModelProperty("排序条件，key为排序字段，value是true为升序，false为降序")
    private LinkedHashMap<String, Boolean> orderByFields;
}