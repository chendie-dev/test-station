package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chendie.teststation.convert.QuestionConvert;
import com.chendie.teststation.entity.Paper;
import com.chendie.teststation.entity.PaperQuestion;
import com.chendie.teststation.entity.Question;
import com.chendie.teststation.model.IdView;
import com.chendie.teststation.model.PageQry;
import com.chendie.teststation.model.PageResult;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.IPaperQuestionService;
import com.chendie.teststation.service.IPaperService;
import com.chendie.teststation.service.IQuestionService;
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
@RequestMapping("/question")
public class QuestionController {
    @Resource
    private IQuestionService questionService;
    @Resource
    private IPaperQuestionService paperQuestionService;
    @Resource
    private IPaperService paperService;

    @PostMapping("/addOrUpdateQuestion")
    public ResultView<IdView> addOrUpdateQuestion(
            @RequestBody Question question
    ) {
        // 区分一下新增和更新
        if (Objects.nonNull(question.getQuestionId())) {
            // 根据标签新增到对应的试卷
            if (Objects.nonNull(question.getTagId())) {
                // 删除之前的标签对应的试卷
                Question question1 = questionService.getById(question.getQuestionId());
                List<Paper> paperList = paperService
                        .list(new LambdaQueryWrapper<Paper>()
                                .eq(Paper::getTagId, question1.getTagId()));
                if (!CollectionUtils.isEmpty(paperList)) {
                    Paper paper = paperList.get(0);
                    paperQuestionService
                            .remove(new LambdaUpdateWrapper<PaperQuestion>()
                                    .eq(PaperQuestion::getPaperId, paper.getPaperId())
                                    .eq(PaperQuestion::getQuestionId, question1.getQuestionId()));
                    // 更新之前的分数
                    paper.setTotalScore(paper.getTotalScore() - question1.getScore());
                    paperService.saveOrUpdate(paper);
                }

                // 新增现在的
                List<Paper> paperList1 = paperService
                        .list(new LambdaQueryWrapper<Paper>()
                                .eq(Paper::getTagId, question.getTagId()));
                if (CollectionUtils.isEmpty(paperList1)) {
                    return ResultView.fail("error paper");
                }
                Paper paper1 = paperList1.get(0);
                PaperQuestion paperQuestion = new PaperQuestion();
                paperQuestion.setQuestionId(question.getQuestionId());
                paperQuestion.setPaperId(paper1.getPaperId());
                paperQuestionService.save(paperQuestion);

                // 更新之前的分数
                paper1.setTotalScore(paper1.getTotalScore() + question1.getScore());
                paperService.saveOrUpdate(paper1);
            }
            // 更新分数
            if (Objects.nonNull(question.getScore())) {
                // 查询之前的分数，做diff
                Question question1 = questionService.getById(question.getQuestionId());
                int diff = question.getScore() - question1.getScore();
                List<PaperQuestion> paperQuestionList = paperQuestionService
                        .list(new LambdaQueryWrapper<PaperQuestion>()
                                .eq(PaperQuestion::getQuestionId, question.getQuestionId()));
                // 更新分数
                paperQuestionList.forEach(paperQuestion -> {
                    // 查询之前的paper
                    Paper paper = paperService.getById(paperQuestion.getPaperId());
                    paper.setTotalScore(paper.getTotalScore() + diff);
                    paperService.saveOrUpdate(paper);
                });
            }
            boolean ok = questionService.saveOrUpdate(question);
            IdView idView = IdView.builder().build();
            if (ok) {
                idView.setId(question.getQuestionId());
            }
            return ResultView.success(idView);
        } else {
            boolean ok = questionService.saveOrUpdate(question);
            // 根据标签新增到对应的试卷
            if (Objects.nonNull(question.getTagId())) {
                List<Paper> paperList = paperService
                        .list(new LambdaQueryWrapper<Paper>()
                                .eq(Paper::getTagId, question.getTagId()));
                if (CollectionUtils.isEmpty(paperList)) {
                    return ResultView.fail("error paper");
                }
                Paper paper = paperList.get(0);
                // 添加现在的
                PaperQuestion paperQuestion = new PaperQuestion();
                paperQuestion.setQuestionId(question.getQuestionId());
                paperQuestion.setPaperId(paper.getPaperId());
                paperQuestionService.save(paperQuestion);
            }

            // 更新分数
            if (Objects.nonNull(question.getScore())) {
                List<PaperQuestion> paperQuestionList = paperQuestionService
                        .list(new LambdaQueryWrapper<PaperQuestion>()
                                .eq(PaperQuestion::getQuestionId, question.getQuestionId()));
                // 更新分数
                paperQuestionList.forEach(paperQuestion -> {
                    // 查询之前的paper
                    Paper paper = paperService.getById(paperQuestion.getPaperId());
                    paper.setTotalScore(paper.getTotalScore() + question.getScore());
                    paperService.saveOrUpdate(paper);
                });
            }

            IdView idView = IdView.builder().build();
            if (ok) {
                idView.setId(question.getQuestionId());
            }
            return ResultView.success(idView);
        }
    }

    @PostMapping("/deleteQuestions")
    public ResultView<Boolean> deleteQuestions(
            @RequestBody List<Long> ids
    ) {
        // 更新paper的分数
        List<PaperQuestion> paperQuestionList = paperQuestionService
                .list(new LambdaQueryWrapper<PaperQuestion>()
                        .in(PaperQuestion::getQuestionId, ids));
        paperQuestionList.forEach(paperQuestion -> {
            Paper paper = paperService.getById(paperQuestion.getPaperId());
            Question question = questionService.getById(paperQuestion.getQuestionId());
            paper.setTotalScore(paper.getTotalScore() - question.getScore());
            paperService.saveOrUpdate(paper);
        });
        // 删除关系表的数据
        paperQuestionService.remove(new LambdaUpdateWrapper<PaperQuestion>().in(PaperQuestion::getQuestionId, ids));
        boolean ok = questionService.removeByIds(ids);
        return ResultView.success(ok);
    }

    @PostMapping("/getQuestionsByPage")
    public ResultView<PageResult<Question>> getQuestionsByPage(
            @RequestBody PageQry<Question> questionPageQry
    ) {
        Page<Question> page = new Page<>(questionPageQry.getPageNum(), questionPageQry.getPageSize());
        LambdaQueryWrapper<Question> queryWrapper = new LambdaQueryWrapper<>();
        Question queryParam = questionPageQry.getQueryParam();
        // 条件
        queryWrapper
                .eq(Objects.nonNull(queryParam.getQuestionId()), Question::getQuestionId, queryParam.getQuestionId())
                .like(Objects.nonNull(queryParam.getQuestionName()), Question::getQuestionName, queryParam.getQuestionName());
        // 排序规则
        LinkedHashMap<String, Boolean> orderByFields = questionPageQry.getOrderByFields();
        if (CollectionUtils.isEmpty(orderByFields)) {
            orderByFields = new LinkedHashMap<>();
            orderByFields.put("createTime", false);
        }
        orderByFields.forEach((name, asc) ->
                queryWrapper.orderBy(true, asc, QuestionConvert.filedName2Function(name))
        );
        Page<Question> questionPage = questionService.page(page, queryWrapper);
        List<Question> questionList = questionPage.getRecords();
        PageResult<Question> pageResult = new PageResult<>();
        pageResult.setTotalPage(questionPage.getPages());
        pageResult.setData(questionList);
        return ResultView.success(pageResult);
    }

    @PostMapping("/getQuestionsByPaper")
    public ResultView<List<Question>> getQuestionsByPaper(
            @RequestParam("paperId") Long paperId
    ) {
        // 通过paperId获取所有的question
        LambdaQueryWrapper<PaperQuestion> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper
                .eq(PaperQuestion::getPaperId, paperId);
        List<PaperQuestion> paperQuestions = paperQuestionService.list(queryWrapper);
        List<Long> questionIdList = paperQuestions.stream()
                .map(PaperQuestion::getQuestionId)
                .collect(Collectors.toList());
        List<Question> questionList = questionService.listByIds(questionIdList);
        return ResultView.success(questionList);
    }
}
