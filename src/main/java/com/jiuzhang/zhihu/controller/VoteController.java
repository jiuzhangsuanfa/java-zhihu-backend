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
import com.jiuzhang.zhihu.common.Func;
import com.jiuzhang.zhihu.common.api.R;
import com.jiuzhang.zhihu.common.support.Condition;
import com.jiuzhang.zhihu.common.support.Query;
import com.jiuzhang.zhihu.entity.Vote;
import com.jiuzhang.zhihu.entity.vo.VoteVO;
import com.jiuzhang.zhihu.service.IVoteService;
import com.jiuzhang.zhihu.service.vote.IVoteStrategyService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 *  控制器
 *
 * @author 作者
 * @since 2020-11-12
 */
@RestController
@AllArgsConstructor
@RequestMapping("/vote")
public class VoteController {

	private final IVoteService voteService;

	private final IVoteStrategyService voteStrategyService;


	// ---------------------------- CRUD ----------------------------

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	public R<Vote> detail(Vote vote) {
		Vote detail = voteService.getOne(Condition.getQueryWrapper(vote));
		return R.data(detail);
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list")
	public R<IPage<Vote>> list(Vote vote, Query query) {
		IPage<Vote> pages = voteService.page(Condition.getPage(query), Condition.getQueryWrapper(vote));
		return R.data(pages);
	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	public R<IPage<VoteVO>> page(VoteVO vote, Query query) {
		IPage<VoteVO> pages = voteService.selectVotePage(Condition.getPage(query), vote);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	public R save(@RequestBody Vote vote) {
		return R.status(voteService.save(vote));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	public R update(@RequestBody Vote vote) {
		return R.status(voteService.updateById(vote));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	public R submit(@RequestBody Vote vote) {
		return R.status(voteService.saveOrUpdate(vote));
	}

	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	public R remove(@RequestParam String ids) {
		return R.status(voteService.removeByIds(Func.toLongList(ids)));
	}

	// ------------------------------- 赞、踩（多种策略实现）-------------------------------
	/**
	 * 提交/取消 赞踩
	 */
	@PostMapping("/vote")
	public R vote(@RequestBody VoteVO vote) {

		// 保存投票记录
		boolean rv = voteService.saveOrUpdate(vote);
		// 更新得票计数器
		voteStrategyService.vote(vote);
		return R.status(rv);
	}

}
