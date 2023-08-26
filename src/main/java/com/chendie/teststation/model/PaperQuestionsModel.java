package com.chendie.teststation.model;

import com.chendie.teststation.entity.Paper;
import com.chendie.teststation.entity.Questions;
import lombok.Data;

import java.util.List;

/**
 * @author: chendie
 * @description:
 */
@Data
public class PaperQuestionsModel {
    private Paper paper;
    private List<Questions> questions;
}
