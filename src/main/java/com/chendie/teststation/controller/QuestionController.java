package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chendie.teststation.convert.QuestionConvert;
import com.chendie.teststation.entity.Question;
import com.chendie.teststation.model.IdView;
import com.chendie.teststation.model.PageQry;
import com.chendie.teststation.model.PageResult;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.IQuestionService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

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
@Controller
@RequestMapping("/question")
public class QuestionController {
    @Resource
    private IQuestionService questionService;

    @PostMapping("/addOrUpdateQuestion")
    public ResultView<IdView> addOrUpdateQuestion(
            @RequestBody Question question
    ) {
        boolean ok = questionService.saveOrUpdate(question);
        IdView idView = IdView.builder().build();
        if (ok) {
            idView.setId(question.getQuestionId());
        }
        return ResultView.success(idView);
    }

    @PostMapping("/deleteQuestions")
    public ResultView<Boolean> deleteQuestions(
            @RequestBody List<Long> ids
    ) {
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
}
