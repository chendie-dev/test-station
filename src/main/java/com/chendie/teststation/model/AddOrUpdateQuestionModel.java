package com.chendie.teststation.model;

import lombok.Data;

import java.util.List;

/**
 * @author: chendie
 * @description:
 */
@Data
public class AddOrUpdateQuestionModel {
    private Long paperId;
    private List<Long> ids;
}
