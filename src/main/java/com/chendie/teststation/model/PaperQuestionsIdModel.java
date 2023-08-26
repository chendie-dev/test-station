package com.chendie.teststation.model;

import com.chendie.teststation.entity.Paper;
import lombok.Data;

import java.util.List;

/**
 * @author: chendie
 * @description:
 */
@Data
public class PaperQuestionsIdModel {
    private Paper paper;
    private List<Long> qidList;
}
