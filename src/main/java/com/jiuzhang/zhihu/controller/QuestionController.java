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

import io.swagger.annotations.Api;
import lombok.AllArgsConstructor;
import javax.validation.Valid;
import com.jiuzhang.zhihu.common.R;
import com.jiuzhang.zhihu.common.Query;
import org.springblade.core.tool.utils.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiuzhang.zhihu.entity.Question;
import com.jiuzhang.zhihu.vo.QuestionVO;
import com.jiuzhang.zhihu.service.IQuestionService;
import com.jiuzhang.zhihu.common.BaseController;

/**
 *  控制器
 *
 * @author 九章算法
 * @since 2020-11-06
 */
@RestController
@AllArgsConstructor
@RequestMapping("/")
@Api(value = "", tags = "接口")
public class QuestionController extends BaseController {

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
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入question")
	public R<IPage<Question>> list(Question question, Query query) {
		IPage<Question> pages = questionService.page(Condition.getPage(query), Condition.getQueryWrapper(question));
		return R.data(pages);
	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入question")
	public R<IPage<QuestionVO>> page(QuestionVO question, Query query) {
		IPage<QuestionVO> pages = questionService.selectQuestionPage(Condition.getPage(query), question);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入question")
	public R save(@Valid @RequestBody Question question) {
		return R.status(questionService.save(question));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入question")
	public R update(@Valid @RequestBody Question question) {
		return R.status(questionService.updateById(question));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入question")
	public R submit(@Valid @RequestBody Question question) {
		return R.status(questionService.saveOrUpdate(question));
	}


	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(questionService.removeByIds(Func.toLongList(ids)));
	}


}
