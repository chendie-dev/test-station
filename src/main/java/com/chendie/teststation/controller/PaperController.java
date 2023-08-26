package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chendie.teststation.entity.Paper;
import com.chendie.teststation.entity.PaperQuestions;
import com.chendie.teststation.entity.Questions;
import com.chendie.teststation.model.PaperQuestionsIdModel;
import com.chendie.teststation.model.PaperQuestionsModel;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.IPaperQuestionsService;
import com.chendie.teststation.service.IPaperService;
import com.chendie.teststation.service.IQuestionsService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author chendie
 * @since 2023-08-26
 */
@RestController
@RequestMapping("/paper")
public class PaperController {
    @Resource
    private IPaperService paperService;
    @Resource
    private IPaperQuestionsService paperQuestionsService;
    @Resource
    private IQuestionsService questionsService;

    @PostMapping("/addOrUpdatePaper")
    public ResultView<Long> addOrUpdatePaper(
            @RequestBody PaperQuestionsIdModel paperQuestionsIdModel
    ) {
        Paper paper = paperQuestionsIdModel.getPaper();
        List<Long> qidList = paperQuestionsIdModel.getQidList();
        paperService.saveOrUpdate(paper);
        // 如果不是修改分数，且不是修改试题列表
        if (Objects.isNull(paper.getTotalValue()) && !CollectionUtils.isEmpty(qidList)) {
            // 关系表，先删除后新增，保证顺序
            LambdaQueryWrapper<PaperQuestions> queryWrapper = new LambdaQueryWrapper<>();
            queryWrapper
                    .eq(PaperQuestions::getPid, paper.getPid())
                    .in(PaperQuestions::getQid, qidList);
            paperQuestionsService.remove(queryWrapper);
            // 新增关系
            List<PaperQuestions> paperQuestions = new ArrayList<>();
            qidList.forEach(qId -> {
                PaperQuestions questions = new PaperQuestions();
                questions.setPid(paper.getPid());
                questions.setQid(qId);
                paperQuestions.add(questions);
            });
            paperQuestionsService.saveBatch(paperQuestions);
        }
        return ResultView.success(paper.getPid());
    }

    @PostMapping("/getPaper")
    public ResultView<List<PaperQuestionsModel>> getPaper(
            @RequestParam(value = "pid", required = false) Long pid, @RequestParam("uid") Long uid
    ) {
        // 获取paper
        LambdaQueryWrapper<Paper> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Objects.nonNull(pid), Paper::getPid, pid)
                .eq(Paper::getUid, uid);
        List<Paper> paperList = paperService.list(queryWrapper);
        List<PaperQuestionsModel> paperQuestionsModels = new ArrayList<>();
        paperList.forEach(paper -> {
            PaperQuestionsModel paperQuestionsModel = new PaperQuestionsModel();
            LambdaQueryWrapper<PaperQuestions> queryWrapper1 = new LambdaQueryWrapper<>();
            queryWrapper1
                    .eq(PaperQuestions::getPid, paper.getPid());
            List<PaperQuestions> paperQuestionsList = paperQuestionsService.list(queryWrapper1);
            List<Long> qIds = paperQuestionsList.stream().map(PaperQuestions::getQid).collect(Collectors.toList());
            List<Questions> questions = questionsService.listByIds(qIds);
            paperQuestionsModel.setQuestions(questions);
            paperQuestionsModel.setPaper(paper);
            paperQuestionsModels.add(paperQuestionsModel);
        });
        return ResultView.success(paperQuestionsModels);
    }

    @PostMapping("/delete")
    public ResultView<Long> deletePaper(
            @RequestBody PaperQuestionsIdModel paperQuestionsIdModel
    ) {
        // 删除paper
        Paper paper = paperQuestionsIdModel.getPaper();
        LambdaQueryWrapper<Paper> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(Paper::getPid, paper.getPid())
                .eq(Paper::getUid, paper.getUid());
        paperService.remove(queryWrapper);
        // 删除关系表
        List<Long> qIdList = paperQuestionsIdModel.getQidList();
        // 关系表，先删除后新增，保证顺序
        LambdaQueryWrapper<PaperQuestions> queryWrapper1 = new LambdaQueryWrapper<>();
        queryWrapper1
                .eq(PaperQuestions::getPid, paper.getPid())
                .in(PaperQuestions::getQid, qIdList);
        paperQuestionsService.remove(queryWrapper1);
        return ResultView.success(paper.getPid());
    }
}
