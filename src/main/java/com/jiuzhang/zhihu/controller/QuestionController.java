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

import lombok.AllArgsConstructor;

import com.jiuzhang.zhihu.common.support.Condition;
import com.jiuzhang.zhihu.common.support.Query;
import com.jiuzhang.zhihu.common.api.R;
import com.jiuzhang.zhihu.common.Func;
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
@RequestMapping("/question")
public class QuestionController {

	private final IQuestionService questionService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	public R<Question> detail(Question question) {
		Question detail = questionService.getOne(Condition.getQueryWrapper(question));
		return R.data(detail);
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list")
	public R<IPage<Question>> list(Question question, Query query) {
		IPage<Question> pages = questionService.page(Condition.getPage(query), Condition.getQueryWrapper(question));
		return R.data(pages);
	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	public R<IPage<QuestionVO>> page(QuestionVO question, Query query) {
		IPage<QuestionVO> pages = questionService.selectQuestionPage(Condition.getPage(query), question);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	public R save(@RequestBody Question question) {
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
	@PostMapping("/submit")
	public R submit(@RequestBody Question question) {
		return R.status(questionService.saveOrUpdate(question));
	}


	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	public R remove(@RequestParam String ids) {
		return R.status(questionService.removeByIds(Func.toLongList(ids)));
	}


}
