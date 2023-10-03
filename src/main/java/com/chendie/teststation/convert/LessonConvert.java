package com.chendie.teststation.convert;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.chendie.teststation.entity.Lesson;

/**
 *
 * @author chendie
 * @since 2023-10-01
 */
public class LessonConvert {
    public static SFunction<Lesson, ?> filedName2Function(String name) {
        switch (name) {
            case "lessonName":
                return Lesson::getLessonName;
            default:
                return Lesson::getCreateTime;
        }
    }
}
