package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chendie.teststation.convert.UserConvert;
import com.chendie.teststation.entity.User;
import com.chendie.teststation.model.IdView;
import com.chendie.teststation.model.PageQry;
import com.chendie.teststation.model.PageResult;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.IUserService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  用户管理前端控制器
 * </p>
 *
 * @author chendie
 * @since 2023-10-01
 */
@RestController
@RequestMapping("/user")
public class UserController {

    @Resource
    private IUserService userService;

    @PostMapping("/addOrUpdateStudent")
    public ResultView<IdView> addOrUpdateStudent(
            @RequestBody User user
    ) {
        boolean ok = userService.saveOrUpdate(user);
        IdView idView = IdView.builder().build();
        if (ok) {
            idView.setId(user.getUserId());
        }
        return ResultView.success(idView);
    }

    @PostMapping("/login")
    public ResultView<User> login(
            @RequestBody User user
    ) {
        List<User> userList = userService
                .list(new LambdaQueryWrapper<User>()
                        .eq(User::getUsername, user.getUsername())
                        .eq(User::getPassword, user.getPassword()));
        if (!CollectionUtils.isEmpty(userList)) {
            return ResultView.success(userList.get(0));
        }
        return ResultView.fail("用户名或者密码错误");
    }

    @PostMapping("/deleteStudents")
    public ResultView<Boolean> deleteStudents(
            @RequestBody List<Long> ids
    ) {
        boolean ok = userService.removeByIds(ids);
        return ResultView.success(ok);
    }

    @PostMapping("/getStudentsByPage")
    public ResultView<PageResult<User>> getStudentsByPage(
            @RequestBody PageQry<User> userPageQry
    ) {
        Page<User> page = new Page<>(userPageQry.getPageNum(), userPageQry.getPageSize());
        LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
        User queryParam = userPageQry.getQueryParam();
        // 条件
        queryWrapper
                .eq(Objects.nonNull(queryParam.getUserId()), User::getUserId, queryParam.getUserId())
                .like(Objects.nonNull(queryParam.getRealName()), User::getRealName, queryParam.getRealName());
        // 排序规则
        LinkedHashMap<String, Boolean> orderByFields = userPageQry.getOrderByFields();
        if (CollectionUtils.isEmpty(orderByFields)) {
            orderByFields = new LinkedHashMap<>();
            orderByFields.put("createTime", false);
        }
        orderByFields.forEach((name, asc) ->
                queryWrapper.orderBy(true, asc, UserConvert.filedName2Function(name))
        );
        Page<User> userPage = userService.page(page, queryWrapper);
        List<User> userList = userPage.getRecords();
        PageResult<User> pageResult = new PageResult<>();
        pageResult.setTotalPage(userPage.getPages());
        pageResult.setData(userList);
        return ResultView.success(pageResult);
    }

}
