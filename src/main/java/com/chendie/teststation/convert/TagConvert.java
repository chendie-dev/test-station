package com.chendie.teststation.convert;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.chendie.teststation.entity.Tag;

/**
 *
 * @author chendie
 * @since 2023-10-01
 */
public class TagConvert {
    public static SFunction<Tag, ?> filedName2Function(String name) {
        switch (name) {
            case "tagName":
                return Tag::getTagName;
            default:
                return Tag::getCreateTime;
        }
    }
}
