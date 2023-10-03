package com.chendie.teststation.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author: ddgo
 * @description:
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class IdsView {
    @ApiModelProperty("ids")
    private List<Long> ids;
}