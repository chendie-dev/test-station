package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.chendie.teststation.entity.ExamRecordDetail;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.IExamRecordDetailService;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
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
@RequestMapping("/examRecordDetail")
public class ExamRecordDetailController {
    @Resource
    private IExamRecordDetailService examRecordDetailService;

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
}
