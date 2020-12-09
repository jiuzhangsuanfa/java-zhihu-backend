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
import com.jiuzhang.zhihu.entity.Answer;
import com.jiuzhang.zhihu.entity.vo.AnswerVO;
import com.jiuzhang.zhihu.service.IAnswerService;

/**
 *  控制器
 *
 * @author 作者
 * @since 2020-11-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/answer")
public class AnswerController {

	private final IAnswerService answerService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	public R<Answer> detail(Answer answer) {
		Answer detail = answerService.getOne(Condition.getQueryWrapper(answer));
		return R.data(detail);
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list")
	public R<IPage<Answer>> list(Answer answer, Query query) {
		IPage<Answer> pages = answerService.page(Condition.getPage(query), Condition.getQueryWrapper(answer));
		return R.data(pages);
	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	public R<IPage<AnswerVO>> page(AnswerVO answer, Query query) {
		IPage<AnswerVO> pages = answerService.selectAnswerPage(Condition.getPage(query), answer);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	public R save(@RequestBody Answer answer) {
		return R.status(answerService.save(answer));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	public R update(@RequestBody Answer answer) {
		return R.status(answerService.updateById(answer));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	public R submit(@RequestBody Answer answer) {
		return R.status(answerService.saveOrUpdate(answer));
	}


	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	public R remove(@RequestParam String ids) {
		return R.status(answerService.removeByIds(Func.toLongList(ids)));
	}


}
