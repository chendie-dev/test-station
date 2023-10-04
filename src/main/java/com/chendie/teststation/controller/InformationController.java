package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chendie.teststation.convert.InformationConvert;
import com.chendie.teststation.entity.Information;
import com.chendie.teststation.entity.User;
import com.chendie.teststation.entity.UserInformation;
import com.chendie.teststation.model.IdView;
import com.chendie.teststation.model.PageQry;
import com.chendie.teststation.model.PageResult;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.IInformationService;
import com.chendie.teststation.service.IUserInformationService;
import com.chendie.teststation.service.IUserService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chendie
 * @since 2023-10-01
 */
@RestController
@RequestMapping("/information")
public class InformationController {
    @Resource
    private IInformationService informationService;
    @Resource
    private IUserInformationService userInformationService;
    @Resource
    private IUserService userService;

    @PostMapping("/addOrUpdateInformation")
    public ResultView<IdView> addOrUpdateInformation(
            @RequestBody Information information
    ) {
        boolean ok = informationService.saveOrUpdate(information);
        IdView idView = IdView.builder().build();
        if (ok) {
            idView.setId(information.getInformationId());
        }
        return ResultView.success(idView);
    }

    @PostMapping("/deleteInformation")
    public ResultView<Boolean> deleteInformation(
            @RequestBody List<Long> ids
    ) {
        boolean ok = informationService.removeByIds(ids);
        return ResultView.success(ok);
    }

    @PostMapping("/getInformationByPage")
    public ResultView<PageResult<Information>> geInformationByPage(
            @RequestBody PageQry<Information> informationPageQry
    ) {
        Page<Information> page = new Page<>(informationPageQry.getPageNum(), informationPageQry.getPageSize());
        LambdaQueryWrapper<Information> queryWrapper = new LambdaQueryWrapper<>();
        Information queryParam = informationPageQry.getQueryParam();
        // 条件
        queryWrapper
                .like(Objects.nonNull(queryParam.getTitle()), Information::getTitle, queryParam.getTitle());
        // 排序规则
        LinkedHashMap<String, Boolean> orderByFields = informationPageQry.getOrderByFields();
        if (CollectionUtils.isEmpty(orderByFields)) {
            orderByFields = new LinkedHashMap<>();
            orderByFields.put("createTime", false);
        }
        orderByFields.forEach((name, asc) ->
                queryWrapper.orderBy(true, asc, InformationConvert.filedName2Function(name))
        );
        Page<Information> informationPage = informationService.page(page, queryWrapper);
        List<Information> informationList = informationPage.getRecords();
        PageResult<Information> pageResult = new PageResult<>();
        pageResult.setTotalPage(informationPage.getPages());
        pageResult.setData(informationList);
        return ResultView.success(pageResult);
    }

    @PostMapping("/getInformationByUser")
    public ResultView<List<Information>> getInformationByUser(
            @RequestBody User user
    ) {
        // 用于学生查询所有的信息
        List<UserInformation> userInformationList = userInformationService
                .list(new LambdaQueryWrapper<UserInformation>()
                        .eq(UserInformation::getUserId, user.getUserId()));
        List<Long> informationIdList = userInformationList.stream()
                .map(UserInformation::getInformationId)
                .collect(Collectors.toList());
        if (CollectionUtils.isEmpty(informationIdList)) {
            return ResultView.success();
        }
        List<Information> informationList = informationService.listByIds(informationIdList);
        return ResultView.success(informationList);
    }

    @PostMapping("/send")
    public ResultView<Boolean> send(
            @RequestParam(value = "informationId") Long informationId,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "lessonId", required = false) Long lessonId
    ) {
        List<Long> userIdList = new ArrayList<>();
        // 先通过班级获取学生信息
        if (Objects.isNull(userId) && Objects.nonNull(lessonId)) {
            LambdaQueryWrapper<User> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper.eq(User::getLessonId, lessonId);
            List<User> userList = userService.list(queryWrapper);
            List<Long> list = userList.stream()
                    .map(User::getUserId)
                    .collect(Collectors.toList());
            userIdList.addAll(list);
        }
        if (Objects.nonNull(userId)) {
            userIdList.add(userId);
        }
        if (Objects.isNull(userId) && Objects.isNull(lessonId)) {
            List<User> userList = userService.list();
            List<Long> list = userList.stream()
                    .filter(user -> "student".equals(user.getRealName()))
                    .map(User::getUserId)
                    .collect(Collectors.toList());
            userIdList.addAll(list);
        }
        // 过滤存在的消息，防止重复发送
        LambdaQueryWrapper<UserInformation> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(UserInformation::getInformationId, informationId)
                .in(UserInformation::getUserId, userIdList);
        List<UserInformation> userInformationList = userInformationService.list(queryWrapper);
        userIdList.removeAll(userInformationList.stream()
                .map(UserInformation::getUserId)
                .collect(Collectors.toList()));
        List<UserInformation> userInformationList1 = userIdList.stream().map(id -> {
            UserInformation userInformation = new UserInformation();
            userInformation.setInformationId(informationId);
            userInformation.setUserId(id);
            return userInformation;
        }).collect(Collectors.toList());
        boolean ok = userInformationService.saveOrUpdateBatch(userInformationList1);
        return ResultView.success(ok);
    }

    @PostMapping("/read")
    public ResultView<Boolean> read(
            @RequestParam(value = "informationId") Long informationId,
            @RequestParam(value = "userId", required = false) Long userId
    ) {
        UserInformation userInformation = new UserInformation();
        userInformation.setInformationId(informationId);
        userInformation.setUserId(userId);
        LambdaUpdateWrapper<UserInformation> updateWrapper = new LambdaUpdateWrapper<>();
        updateWrapper
                .set(UserInformation::getIsRead, 1)
                .eq(UserInformation::getInformationId, informationId)
                .eq(UserInformation::getUserId, userId);
        boolean update = userInformationService.update(updateWrapper);
        return ResultView.success(update);
    }
}
