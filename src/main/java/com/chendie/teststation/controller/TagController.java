package com.chendie.teststation.controller;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.chendie.teststation.convert.TagConvert;
import com.chendie.teststation.entity.Paper;
import com.chendie.teststation.entity.Tag;
import com.chendie.teststation.model.IdView;
import com.chendie.teststation.model.PageQry;
import com.chendie.teststation.model.PageResult;
import com.chendie.teststation.model.ResultView;
import com.chendie.teststation.service.IPaperService;
import com.chendie.teststation.service.ITagService;
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
 *  前端控制器
 * </p>
 *
 * @author chendie
 * @since 2023-10-01
 */
@RestController
@RequestMapping("/tag")
public class TagController {

    @Resource
    private ITagService tagService;
    @Resource
    private IPaperService paperService;

    @PostMapping("/addOrUpdateTag")
    public ResultView<IdView> addOrUpdateTag(
            @RequestBody Tag tag
    ) {
        // 新增标签创建对应的试卷
        Paper paper = null;
        if (Objects.isNull(tag.getTagId())) {
            paper = new Paper();
            paper.setTime(Long.MAX_VALUE);
            paper.setTitle(tag.getTagName() + "专项训练");
            paper.setUserId(0L);
        }
        boolean ok = tagService.saveOrUpdate(tag);
        IdView idView = IdView.builder().build();
        if (ok) {
            idView.setId(tag.getTagId());
        }
        if (Objects.nonNull(paper)) {
            paper.setTagId(tag.getTagId());
            paperService.save(paper);
        }
        return ResultView.success(idView);
    }

    @PostMapping("/deleteTags")
    public ResultView<Boolean> deleteTags(
            @RequestBody List<Long> ids
    ) {
        boolean ok = tagService.removeByIds(ids);
        return ResultView.success(ok);
    }

    @PostMapping("/getTagsByPage")
    public ResultView<PageResult<Tag>> getTagsByPage(
            @RequestBody PageQry<Tag> tagPageQry
    ) {
        Page<Tag> page = new Page<>(tagPageQry.getPageNum(), tagPageQry.getPageSize());
        LambdaQueryWrapper<Tag> queryWrapper = new LambdaQueryWrapper<>();
        Tag queryParam = tagPageQry.getQueryParam();
        // 条件
        queryWrapper
                .eq(Objects.nonNull(queryParam.getTagId()), Tag::getTagId, queryParam.getTagId())
                .like(Objects.nonNull(queryParam.getTagName()), Tag::getTagName, queryParam.getTagName());
        // 排序规则
        LinkedHashMap<String, Boolean> orderByFields = tagPageQry.getOrderByFields();
        if (CollectionUtils.isEmpty(orderByFields)) {
            orderByFields = new LinkedHashMap<>();
            orderByFields.put("createTime", false);
        }
        orderByFields.forEach((name, asc) ->
                queryWrapper.orderBy(true, asc, TagConvert.filedName2Function(name))
        );
        Page<Tag> tagPage = tagService.page(page, queryWrapper);
        List<Tag> tagList = tagPage.getRecords();
        PageResult<Tag> pageResult = new PageResult<>();
        pageResult.setTotalPage(tagPage.getPages());
        pageResult.setData(tagList);
        return ResultView.success(pageResult);
    }

}
