package com.chendie.teststation.convert;

import com.baomidou.mybatisplus.core.toolkit.support.SFunction;
import com.chendie.teststation.entity.User;

/**
 *
 * @author chendie
 * @since 2023-10-01
 */
public class UserConvert {
    public static SFunction<User, ?> filedName2Function(String name) {
        switch (name) {
            case "realName":
                return User::getRealName;
            default:
                return User::getCreateTime;
        }
    }
}
