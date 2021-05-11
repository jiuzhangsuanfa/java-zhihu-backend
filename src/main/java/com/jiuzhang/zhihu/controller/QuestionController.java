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

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiuzhang.zhihu.common.api.R;
import com.jiuzhang.zhihu.common.support.Condition;
import com.jiuzhang.zhihu.common.support.Query;
import com.jiuzhang.zhihu.entity.Question;
import com.jiuzhang.zhihu.entity.event.QuestionAddedEvent;
import com.jiuzhang.zhihu.entity.event.QuestionRemovedEvent;
import com.jiuzhang.zhihu.entity.event.QuestionUpdatedEvent;
import com.jiuzhang.zhihu.entity.vo.QuestionVO;
import com.jiuzhang.zhihu.service.IQuestionService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.util.StopWatch;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

import java.util.List;

/**
 *  控制器
 *
 * @author 作者
 * @since 2020-11-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("questions")
public class QuestionController {

	private final IQuestionService questionService;

	@Autowired
	private ApplicationEventPublisher eventPublisher;

	/**
	 * 问题详情
	 */
	@GetMapping("{id}")
	public R<Question> detail(@PathVariable(name = "id") Long id) {
		Question query = new Question(id);
		Question detail = questionService.getOne(Condition.getQueryWrapper(query));
		return R.data(detail);
	}

	/**
	 * 问题列表分页
	 */
	@GetMapping
	public R<IPage<QuestionVO>> page(QuestionVO question, Query query) {
		IPage<QuestionVO> pages = questionService.selectQuestionPage(Condition.getPage(query), question);
		return R.data(pages);
	}


	/**
	 * 问题列表分页
	 * @return
	 */
//	@GetMapping
//	public List<QuestionVO> page(QuestionVO question, Query query) {
//		IPage<QuestionVO> pages = questionService.selectQuestionPage(Condition.getPage(query), question);
//		return pages.getRecords();
//	}


	/**
	 * 新增问题
	 */
	@PostMapping
	public R<Boolean> save(@RequestBody Question question) {

		boolean rv = questionService.save(question);

		// 发出Question更新事件
		if (rv) {
			Question q = questionService.getOne(new QueryWrapper<>(question));
			eventPublisher.publishEvent(new QuestionAddedEvent(q.getId()));
		}

		return R.status(rv);
	}

	/**
	 * 修改问题
	 */
	@PutMapping
	public R<Boolean> update(@RequestBody Question question) {

		boolean rv = questionService.updateById(question);

		// 发出Question更新事件
		if (rv) {
			eventPublisher.publishEvent(new QuestionUpdatedEvent(question.getId()));
		}

		return R.status(rv);
	}

	/**
	 * 删除问题
	 */
	@DeleteMapping("{id}")
	public R<Boolean> remove(@PathVariable(name = "id") Long id) {

		// 从DB中删除Question
		boolean rv = questionService.removeById(id);

		// 发出Question删除事件
		if (rv) {
			eventPublisher.publishEvent(new QuestionRemovedEvent(id));
		}

		return R.status(rv);
	}


//	/**
//	 * 问题列表
//	 */
//	@GetMapping
//	public R<IPage<Question>> list(Question question, Query query) {
//		IPage<Question> pages = questionService.page(Condition.getPage(query), Condition.getQueryWrapper(question));
//		return R.data(pages);
//	}

//	/**
//	 * 新增或修改
//	 */
//	@PutMapping("submit")
//	public R submit(@RequestBody Question question) {
//		return R.status(questionService.saveOrUpdate(question));
//	}

	public static void main(String[] args) throws InterruptedException {
		StopWatch stopWatch = new StopWatch("stopwatch test");

		stopWatch.start("第一次开始");
		Thread.sleep(1000);
		stopWatch.stop();

		System.out.println(stopWatch.prettyPrint());
	}

}
