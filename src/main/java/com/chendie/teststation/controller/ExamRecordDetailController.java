package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chendie.teststation.entity.ExamRecord;
import com.chendie.teststation.entity.ExamRecordDetail;
import com.chendie.teststation.entity.Question;
import com.chendie.teststation.entity.Tag;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.IExamRecordDetailService;
import com.chendie.teststation.service.IExamRecordService;
import com.chendie.teststation.service.IQuestionService;
import com.chendie.teststation.service.ITagService;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
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
@RequestMapping("/examRecordDetail")
public class ExamRecordDetailController {
    @Resource
    private IExamRecordDetailService examRecordDetailService;
    @Resource
    private IExamRecordService examRecordService;
    @Resource
    private IQuestionService questionService;
    @Resource
    private ITagService tagService;

    @PostMapping("/getExamRecordDetail")
    public ResultView<ExamRecordDetail> getExamRecordDetail(
            @RequestBody ExamRecordDetail examRecordDetail
    ) {
        List<ExamRecordDetail> examRecordDetails = examRecordDetailService
                .list(new LambdaQueryWrapper<ExamRecordDetail>()
                        .eq(Objects.nonNull(examRecordDetail.getRecordId()),
                                ExamRecordDetail::getRecordId, examRecordDetail.getRecordId()));
        if (!CollectionUtils.isEmpty(examRecordDetails)) {
            return ResultView.success(examRecordDetails.get(0));
        }
        return ResultView.success();
    }

    @PostMapping("/getErrorTag")
    public ResultView<List<Tag>> getErrorTag(
            @RequestParam("userId") Long userId
    ) {
        // 获取所有的错误考试回答的原问题id列表
        List<ExamRecord> examRecords = examRecordService
                .list(new LambdaQueryWrapper<ExamRecord>()
                        .eq(ExamRecord::getUserId, userId));
        List<Long> recordIds = examRecords.stream()
                .map(ExamRecord::getRecordId)
                .collect(Collectors.toList());
        List<Long> questionIds = examRecordDetailService
                .list(new LambdaQueryWrapper<ExamRecordDetail>()
                        .in(!CollectionUtils.isEmpty(recordIds), ExamRecordDetail::getRecordId, recordIds))
                .stream()
                .filter(examRecordDetail -> examRecordDetail.getScore() == 0)
                .map(ExamRecordDetail::getQuestionId)
                .distinct()
                .collect(Collectors.toList());
        // 获取对应的标签
        if (CollectionUtils.isEmpty(questionIds)) {
            return ResultView.success();
        }
        List<Long> tagIds = questionService.listByIds(questionIds).stream()
                .map(Question::getTagId)
                .distinct()
                .collect(Collectors.toList());
        List<Tag> tags = tagService.listByIds(tagIds);
        return ResultView.success(tags);
    }
}
