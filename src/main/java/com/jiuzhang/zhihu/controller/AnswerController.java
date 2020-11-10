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
import com.jiuzhang.zhihu.entity.Answer;
import com.jiuzhang.zhihu.vo.AnswerVO;
import com.jiuzhang.zhihu.service.IAnswerService;
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
public class AnswerController extends BaseController {

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
	@ApiOperationSupport(order = 2)
	@ApiOperation(value = "分页", notes = "传入answer")
	public R<IPage<Answer>> list(Answer answer, Query query) {
		IPage<Answer> pages = answerService.page(Condition.getPage(query), Condition.getQueryWrapper(answer));
		return R.data(pages);
	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	@ApiOperationSupport(order = 3)
	@ApiOperation(value = "分页", notes = "传入answer")
	public R<IPage<AnswerVO>> page(AnswerVO answer, Query query) {
		IPage<AnswerVO> pages = answerService.selectAnswerPage(Condition.getPage(query), answer);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	@ApiOperationSupport(order = 4)
	@ApiOperation(value = "新增", notes = "传入answer")
	public R save(@Valid @RequestBody Answer answer) {
		return R.status(answerService.save(answer));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	@ApiOperationSupport(order = 5)
	@ApiOperation(value = "修改", notes = "传入answer")
	public R update(@Valid @RequestBody Answer answer) {
		return R.status(answerService.updateById(answer));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	@ApiOperationSupport(order = 6)
	@ApiOperation(value = "新增或修改", notes = "传入answer")
	public R submit(@Valid @RequestBody Answer answer) {
		return R.status(answerService.saveOrUpdate(answer));
	}


	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	@ApiOperationSupport(order = 8)
	@ApiOperation(value = "删除", notes = "传入ids")
	public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
		return R.status(answerService.removeByIds(Func.toLongList(ids)));
	}


}
