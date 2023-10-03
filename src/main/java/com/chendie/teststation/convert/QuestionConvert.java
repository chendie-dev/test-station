package com.chendie.teststation.convert;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.chendie.teststation.entity.Question;

/**
 *
 * @author chendie
 * @since 2023-10-01
 */
public class QuestionConvert {
    public static SFunction<Question, ?> filedName2Function(String name) {
        switch (name) {
            case "questionName":
                return Question::getQuestionName;
            default:
                return Question::getCreateTime;
        }
    }
}
