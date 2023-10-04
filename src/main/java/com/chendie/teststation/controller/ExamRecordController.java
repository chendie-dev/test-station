package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chendie.teststation.entity.ExamRecord;
import com.chendie.teststation.entity.ExamRecordDetail;
import com.chendie.teststation.entity.PaperQuestion;
import com.chendie.teststation.entity.Question;
import com.chendie.teststation.model.AddExamRecordModel;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.IExamRecordDetailService;
import com.chendie.teststation.service.IExamRecordService;
import com.chendie.teststation.service.IPaperQuestionService;
import com.chendie.teststation.service.IQuestionService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
 * @since 2023-10-01
 */
@RestController
@RequestMapping("/examRecord")
public class ExamRecordController {
    @Resource
    private IExamRecordService examRecordService;
    @Resource
    private IExamRecordDetailService examRecordDetailService;
    @Resource
    private IPaperQuestionService paperQuestionService;
    @Resource
    private IQuestionService questionService;

    @PostMapping("/addExamRecord")
    public ResultView<Boolean> addExamRecord(
            @RequestBody AddExamRecordModel addExamRecordModel
    ) {
        Long paperId = addExamRecordModel.getPaperId();
        Long userId = addExamRecordModel.getUserId();
        Long useTime = addExamRecordModel.getUseTime();
        List<String> replyList = addExamRecordModel.getReplyList();
        // 获取所有的问题列表，和回复一一对应
        List<PaperQuestion> paperQuestionList = paperQuestionService
                .list(new LambdaQueryWrapper<PaperQuestion>()
                        .eq(PaperQuestion::getPaperId, paperId)
                        .orderByAsc(PaperQuestion::getCreateTime));
        List<Question> questionList = questionService
                .listByIds(paperQuestionList.stream()
                        .map(PaperQuestion::getQuestionId)
                        .collect(Collectors.toList()));
        // 计算分数，写入记录表
        Integer score = 0;
        List<ExamRecordDetail> examRecordDetails = new ArrayList<>();
        for (int i = 0; i < replyList.size(); i ++) {
            ExamRecordDetail examRecordDetail = new ExamRecordDetail();
            String reply = replyList.get(i);
            Question question = questionList.get(i);
            String answer = question.getAnswer();
            if (Objects.equals(reply, answer)) {
                score += question.getScore();
                examRecordDetail.setScore(question.getScore());
            } else {
                examRecordDetail.setScore(0);
            }
            examRecordDetail.setReply(reply);
            examRecordDetail.setQuestionId(question.getQuestionId());
            examRecordDetails.add(examRecordDetail);
        }
        ExamRecord examRecord = new ExamRecord();
        examRecord.setGrade(score);
        examRecord.setPaperId(paperId);
        examRecord.setUserId(userId);
        examRecord.setUseTime(useTime);
        examRecordService.save(examRecord);
        // 再去写详细记录
        Long recordId = examRecord.getRecordId();
        examRecordDetails.forEach(examRecordDetail -> examRecordDetail.setRecordId(recordId));
        boolean saveBatch = examRecordDetailService.saveBatch(examRecordDetails);
        return ResultView.success(saveBatch);
    }

    @PostMapping("/getExamRecords")
    public ResultView<List<ExamRecord>> getExamRecords(
            @RequestBody ExamRecord examRecord
    ) {
        LambdaQueryWrapper<ExamRecord> queryWrapper = new LambdaQueryWrapper<>();
        // 条件
        queryWrapper
                .eq(Objects.nonNull(examRecord.getUserId()), ExamRecord::getUserId, examRecord.getUserId())
                .eq(Objects.nonNull(examRecord.getPaperId()), ExamRecord::getPaperId, examRecord.getPaperId());

        List<ExamRecord> examRecordList = examRecordService.list(queryWrapper);
        return ResultView.success(examRecordList);
    }

}
