package com.chendie.teststation.convert;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.chendie.teststation.entity.Information;

/**
 * @author: ddgo
 * @description:
 */
public class InformationConvert {
    public static SFunction<Information, ?> filedName2Function(String name) {
        switch (name) {
            case "title":
                return Information::getTitle;
            default:
                return Information::getCreateTime;
        }
    }
}
