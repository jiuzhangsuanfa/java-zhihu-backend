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

import com.jiuzhang.zhihu.entity.event.QuestionChangeEvent;
import lombok.AllArgsConstructor;

import com.jiuzhang.zhihu.common.support.Condition;
import com.jiuzhang.zhihu.common.support.Query;
import com.jiuzhang.zhihu.common.api.R;
import com.jiuzhang.zhihu.common.Func;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiuzhang.zhihu.entity.Question;
import com.jiuzhang.zhihu.entity.vo.QuestionVO;
import com.jiuzhang.zhihu.service.IQuestionService;

/**
 *  控制器
 *
 * @author 作者
 * @since 2020-11-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/questions")
public class QuestionController {

	private final IQuestionService questionService;

	@Autowired
	private ApplicationEventPublisher applicationEventPublisher;

	/**
	 * 详情
	 */
	@GetMapping("/{id}")
	public R<Question> detail(@PathVariable(name = "id") Long id) {
		Question query = new Question(id);
		Question detail = questionService.getOne(Condition.getQueryWrapper(query));
		return R.data(detail);
	}

//	/**
//	 * 问题列表
//	 */
//	@GetMapping("/")
//	public R<IPage<Question>> list(Question question, Query query) {
//		IPage<Question> pages = questionService.page(Condition.getPage(query), Condition.getQueryWrapper(question));
//		return R.data(pages);
//	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/")
	public R<IPage<QuestionVO>> page(QuestionVO question, Query query) {
		IPage<QuestionVO> pages = questionService.selectQuestionPage(Condition.getPage(query), question);
		return R.data(pages);
	}

	/**
	 * 新增 （发布问题）
	 */
	@PostMapping("/")
	public R save(@RequestBody Question question) {
		QuestionChangeEvent event = new QuestionChangeEvent(null);
		applicationEventPublisher.publishEvent(event);

		return R.status(questionService.save(question));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	public R update(@RequestBody Question question) {
		return R.status(questionService.updateById(question));
	}

	/**
	 * 新增或修改 
	 */
	@PutMapping("/")
	public R submit(@RequestBody Question question) {
		return R.status(questionService.saveOrUpdate(question));
	}


	/**
	 * 删除 
	 */
	@DeleteMapping("/{id}")
	public R remove(@PathVariable(name = "id") Long id) {
		return R.status(questionService.removeById(id));
	}

}
