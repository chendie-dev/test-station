package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chendie.teststation.entity.User;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.IUserService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chendie
 * @since 2023-08-26
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/addOrUpdateUser")
    public ResultView<Long> addUser(
            @RequestBody User user
    ) {
        userService.saveOrUpdate(user);
        return ResultView.success(user.getUid());
    }

    @PostMapping("/get")
    public ResultView<User> getUser(
            @RequestBody User user
    ) {
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(User::getUsername, user.getUsername())
                .eq(User::getPwd, user.getPwd());
        List<User> userList = userService.list(queryWrapper);
        if (CollectionUtils.isEmpty(userList)) {
            return ResultView.fail("用户不存在");
        }
        return ResultView.success(userList.get(0));
    }

}
