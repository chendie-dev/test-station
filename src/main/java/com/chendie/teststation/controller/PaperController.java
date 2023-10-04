package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chendie.teststation.convert.PaperConvert;
import com.chendie.teststation.entity.LessonPaper;
import com.chendie.teststation.entity.Paper;
import com.chendie.teststation.entity.PaperQuestion;
import com.chendie.teststation.entity.User;
import com.chendie.teststation.model.IdView;
import com.chendie.teststation.model.PageQry;
import com.chendie.teststation.model.PageResult;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.ILessonPaperService;
import com.chendie.teststation.service.IPaperQuestionService;
import com.chendie.teststation.service.IPaperService;
import com.chendie.teststation.service.IUserService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/paper")
public class PaperController {
    @Resource
    private IPaperService paperService;
    @Resource
    private IPaperQuestionService paperQuestionService;
    @Resource
    private ILessonPaperService lessonPaperService;
    @Resource
    private IUserService userService;

    @PostMapping("/addPaperOrUpdate")
    public ResultView<IdView> addPaperOrUpdate(
            @RequestBody Paper paper
    ) {
        boolean ok = paperService.saveOrUpdate(paper);
        IdView idView = IdView.builder().build();
        if (ok) {
            idView.setId(paper.getPaperId());
        }
        return ResultView.success(idView);
    }

    @PostMapping("/deletePapers")
    public ResultView<Boolean> deletePapers(
            @RequestBody List<Long> ids
    ) {
        boolean ok = paperService.removeByIds(ids);
        return ResultView.success(ok);
    }

    @PostMapping("/getPapersByPage")
    public ResultView<PageResult<Paper>> getPapersByPage(
            @RequestBody PageQry<Paper> paperPageQry
    ) {
        Page<Paper> page = new Page<>(paperPageQry.getPageNum(), paperPageQry.getPageSize());
        LambdaQueryWrapper<Paper> queryWrapper = new LambdaQueryWrapper<>();
        Paper queryParam = paperPageQry.getQueryParam();
        // 条件
        queryWrapper
                .eq(Objects.nonNull(queryParam.getTagId()), Paper::getTagId, queryParam.getTagId())
                .eq(Objects.nonNull(queryParam.getPaperId()), Paper::getPaperId, queryParam.getPaperId())
                .like(Objects.nonNull(queryParam.getTitle()), Paper::getTitle, queryParam.getTitle());
        // 排序规则
        LinkedHashMap<String, Boolean> orderByFields = paperPageQry.getOrderByFields();
        if (CollectionUtils.isEmpty(orderByFields)) {
            orderByFields = new LinkedHashMap<>();
            orderByFields.put("createTime", false);
        }
        orderByFields.forEach((name, asc) ->
                queryWrapper.orderBy(true, asc, PaperConvert.filedName2Function(name))
        );
        Page<Paper> paperPage = paperService.page(page, queryWrapper);
        List<Paper> paperList = paperPage.getRecords();
        PageResult<Paper> pageResult = new PageResult<>();
        pageResult.setTotalPage(paperPage.getPages());
        pageResult.setData(paperList);
        return ResultView.success(pageResult);
    }

    @PostMapping("/addOrUpdateQuestion")
    public ResultView<Boolean> addOrUpdateQuestion(
            @RequestParam("paperId") Long paperId,
            @RequestParam("questionIds") List<Long> ids
    ) {
        // 删除之前的数据
        paperQuestionService
                .remove(new LambdaQueryWrapper<PaperQuestion>()
                        .eq(PaperQuestion::getPaperId, paperId));
        // 批量新增
        List<PaperQuestion> paperQuestionList = ids.stream().map(id -> {
            PaperQuestion paperQuestion = new PaperQuestion();
            paperQuestion.setQuestionId(id);
            paperQuestion.setPaperId(paperId);
            return paperQuestion;
        }).collect(Collectors.toList());
        boolean saveBatch = paperQuestionService.saveBatch(paperQuestionList);
        return ResultView.success(saveBatch);
    }

    @PostMapping("/addOrUpdateLessons")
    public ResultView<Boolean> addOrUpdateLessons(
            @RequestParam("paperId") Long paperId,
            @RequestParam("lessonIds") List<Long> lessonIds
    ) {
        // 删除之前的关系
        lessonPaperService
                .remove(new LambdaUpdateWrapper<LessonPaper>()
                        .eq(LessonPaper::getPaperId, paperId));
        // 再去新增
        List<LessonPaper> lessonPaperList = lessonIds.stream().map(id -> {
            LessonPaper lessonPaper = new LessonPaper();
            lessonPaper.setPaperId(paperId);
            lessonPaper.setLessonId(id);
            return lessonPaper;
        }).collect(Collectors.toList());
        boolean saveBatch = lessonPaperService.saveBatch(lessonPaperList);
        return ResultView.success(saveBatch);
    }

    @PostMapping("/getPapersByUser")
    public ResultView<List<Paper>> getPapersByUser(
            @RequestParam("userId") Long userId,
            @RequestParam("title") String title
    ) {
        // 获取对应的班级
        User user = userService.getById(userId);
        Long lessonId = user.getLessonId();
        // 获取当前班级绑定的所有试卷
        List<LessonPaper> lessonPaperList = lessonPaperService
                .list(new LambdaQueryWrapper<LessonPaper>()
                        .eq(LessonPaper::getLessonId, lessonId));
        // 获取所有的试卷id
        List<Long> paperIdList = lessonPaperList.stream()
                .map(LessonPaper::getPaperId)
                .collect(Collectors.toList());
        List<Paper> papers = paperService.list(new LambdaQueryWrapper<Paper>()
                .like(Objects.nonNull(title), Paper::getTitle, title)
                .in(!CollectionUtils.isEmpty(paperIdList), Paper::getPaperId, paperIdList));
        return ResultView.success(papers);
    }

}
