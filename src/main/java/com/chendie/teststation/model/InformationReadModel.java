package com.chendie.teststation.model;

import lombok.Data;

import java.util.List;

/**
 * @author: chendie
 * @description:
 */
@Data
public class InformationReadModel {
    private List<Long> informationIdList;
    private Long userId;
}
