package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chendie.teststation.convert.LessonConvert;
import com.chendie.teststation.entity.Lesson;
import com.chendie.teststation.entity.LessonPaper;
import com.chendie.teststation.model.IdView;
import com.chendie.teststation.model.PageQry;
import com.chendie.teststation.model.PageResult;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.ILessonPaperService;
import com.chendie.teststation.service.ILessonService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chendie
 * @since 2023-10-01
 */
@RestController
@RequestMapping("/lesson")
public class LessonController {
    @Resource
    private ILessonService lessonService;
    @Resource
    private ILessonPaperService lessonPaperService;

    @PostMapping("/addOrUpdateLesson")
    public ResultView<IdView> addOrUpdateLesson(
            @RequestBody Lesson lesson
    ) {
        boolean ok = lessonService.saveOrUpdate(lesson);
        IdView idView = IdView.builder().build();
        if (ok) {
            idView.setId(lesson.getLessonId());
        }
        return ResultView.success(idView);
    }

    @PostMapping("/deleteLessons")
    public ResultView<Boolean> deleteLessons(
            @RequestBody List<Long> ids
    ) {
        boolean ok = lessonService.removeByIds(ids);
        return ResultView.success(ok);
    }

    @PostMapping("/getLessonsByPage")
    public ResultView<PageResult<Lesson>> getLessonsByPage(
            @RequestBody PageQry<Lesson> lessonPageQry
    ) {
        Page<Lesson> page = new Page<>(lessonPageQry.getPageNum(), lessonPageQry.getPageSize());
        LambdaQueryWrapper<Lesson> queryWrapper = new LambdaQueryWrapper<>();
        Lesson queryParam = lessonPageQry.getQueryParam();
        // 条件
        queryWrapper
                .eq(Objects.nonNull(queryParam.getLessonId()), Lesson::getLessonId, queryParam.getLessonId())
                .like(Objects.nonNull(queryParam.getLessonName()), Lesson::getLessonName, queryParam.getLessonName());
        // 排序规则
        LinkedHashMap<String, Boolean> orderByFields = lessonPageQry.getOrderByFields();
        if (CollectionUtils.isEmpty(orderByFields)) {
            orderByFields = new LinkedHashMap<>();
            orderByFields.put("createTime", false);
        }
        orderByFields.forEach((name, asc) ->
                queryWrapper.orderBy(true, asc, LessonConvert.filedName2Function(name))
        );
        Page<Lesson> lessonPage = lessonService.page(page, queryWrapper);
        List<Lesson> lessonList = lessonPage.getRecords();
        PageResult<Lesson> pageResult = new PageResult<>();
        pageResult.setTotalPage(lessonPage.getPages());
        pageResult.setData(lessonList);
        return ResultView.success(pageResult);
    }

    @PostMapping("/getLessonsByPaper")
    public ResultView<List<Lesson>> getLessonsByPaper(
            @RequestParam("paperId") Long paperId
    ) {
        // 先去关系表里面获取对应信息
        LambdaQueryWrapper<LessonPaper> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(LessonPaper::getPaperId, paperId);
        List<LessonPaper> lessonPaperList = lessonPaperService.list(queryWrapper);
        // 去表里面获取
        List<Lesson> lessonList = lessonService.listByIds(lessonPaperList);
        return ResultView.success(lessonList);
    }

}
