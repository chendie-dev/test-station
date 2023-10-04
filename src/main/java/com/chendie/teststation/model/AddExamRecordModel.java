package com.chendie.teststation.model;

import lombok.Data;

import java.util.List;

/**
 * @author: chendie
 * @description:
 */
@Data
public class AddExamRecordModel {
    private Long userId;
    private Long paperId;
    private Long useTime;
    private List<String> replyList;
}
