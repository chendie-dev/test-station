package com.chendie.teststation.convert;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.chendie.teststation.entity.ExamRecord;

/**
 *
 * @author chendie
 * @since 2023-10-01
 */
public class ExamRecordConvert {
    public static SFunction<ExamRecord, ?> filedName2Function(String name) {
        switch (name) {
            default:
                return ExamRecord::getCreateTime;
        }
    }
}
