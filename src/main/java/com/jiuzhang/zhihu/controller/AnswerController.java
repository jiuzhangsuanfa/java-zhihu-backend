/*
*      Copyright (c) 2018-2028, Chill Zhuang All rights reserved.
*
*  Redistribution and use in source and binary forms, with or without
*  modification, are permitted provided that the following conditions are met:
*
*  Redistributions of source code must retain the above copyright notice,
*  this list of conditions and the following disclaimer.
*  Redistributions in binary form must reproduce the above copyright
*  notice, this list of conditions and the following disclaimer in the
*  documentation and/or other materials provided with the distribution.
*  Neither the name of the dreamlu.net developer nor the names of its
*  contributors may be used to endorse or promote products derived from
*  this software without specific prior written permission.
*  Author: Chill 庄骞 (smallchill@163.com)
*/
package com.jiuzhang.zhihu.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiuzhang.zhihu.common.api.R;
import com.jiuzhang.zhihu.common.support.Condition;
import com.jiuzhang.zhihu.common.support.Query;
import com.jiuzhang.zhihu.entity.Answer;
import com.jiuzhang.zhihu.service.IAnswerService;
import com.jiuzhang.zhihu.service.IQuestionService;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 * 控制器
 *
 * @author 作者
 * @since 2020-11-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("answers")
public class AnswerController {

    private final IAnswerService answerService;

    private final IQuestionService questionService;

    /**
     * 答案列表
     */
    @GetMapping
    public R<IPage<Answer>> list(Answer answer, Query query) {
        IPage<Answer> pages = answerService.page(Condition.getPage(query), Condition.getQueryWrapper(answer));
        return R.data(pages);
    }

    /**
     * 新增答案
     */
    @PostMapping
    public R<Boolean> save(@RequestBody Answer answer) {
        // 答案计数器+1
        questionService.incrAnswerCount(answer.getQuestionId());
        return R.status(answerService.save(answer));
    }

    /**
     * 修改答案
     */
    @PutMapping
    public R<Boolean> update(@RequestBody Answer answer) {
        return R.status(answerService.updateById(answer));
    }

    /**
     * 删除答案
     */
    @DeleteMapping("{id}")
    public R<Boolean> remove(@PathVariable(name = "id") Long id) {
        Answer answer = answerService.getById(id);
        // 答案计数器-1
        questionService.decrAnswerCount(answer.getQuestionId());
        return R.status(answerService.removeById(id));
    }

    /**
     * 详情
     */
    // @GetMapping("{id}")
    // public R<Answer> detail(Answer answer) {
    // Answer detail = answerService.getOne(Condition.getQueryWrapper(answer));
    // return R.data(detail);
    // }

    /**
     * 自定义分页
     */
    // @GetMapping("page")
    // public R<IPage<AnswerVO>> page(AnswerVO answer, Query query) {
    // IPage<AnswerVO> pages =
    // answerService.selectAnswerPage(Condition.getPage(query), answer);
    // return R.data(pages);
    // }

    /**
     * 新增或修改
     */
    // @PostMapping("submit")
    // public R submit(@RequestBody Answer answer) {
    // return R.status(answerService.saveOrUpdate(answer));
    // }

}
