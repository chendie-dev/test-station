package com.chendie.teststation.convert;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.chendie.teststation.entity.Paper;

/**
 *
 * @author chendie
 * @since 2023-10-01
 */
public class PaperConvert {
    public static SFunction<Paper, ?> filedName2Function(String name) {
        switch (name) {
            case "title":
                return Paper::getTitle;
            default:
                return Paper::getCreateTime;
        }
    }
}
