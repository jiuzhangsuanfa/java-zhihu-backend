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
import com.jiuzhang.zhihu.entity.VoteStats;
import com.jiuzhang.zhihu.entity.vo.VoteVO;
import com.jiuzhang.zhihu.entity.vo.VoteStatsVO;
import com.jiuzhang.zhihu.service.vote.IVoteStrategyService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
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

//	@Autowired
//	@Qualifier("voteService")
//	private final IVoteService voteService;

	@Autowired
//	@Qualifier("simpleVoteStrategyService")
	private final IVoteStrategyService voteStrategyService;


	// ---------------------------- CRUD ----------------------------

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	public R<VoteStats> detail(VoteStats voteStats) {
		VoteStats detail = voteStrategyService.getOne(Condition.getQueryWrapper(voteStats));
		return R.data(detail);
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list")
	public R<IPage<VoteStats>> list(VoteStats voteStats, Query query) {
		IPage<VoteStats> pages = voteStrategyService.page(Condition.getPage(query), Condition.getQueryWrapper(voteStats));
		return R.data(pages);
	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	public R<IPage<VoteStatsVO>> page(VoteStatsVO vote, Query query) {
		IPage<VoteStatsVO> pages = voteStrategyService.selectVotePage(Condition.getPage(query), vote);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	public R save(@RequestBody VoteStats voteStats) {
		return R.status(voteStrategyService.save(voteStats));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	public R update(@RequestBody VoteStats voteStats) {
		return R.status(voteStrategyService.updateById(voteStats));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	public R submit(@RequestBody VoteStats voteStats) {
		return R.status(voteStrategyService.saveOrUpdate(voteStats));
	}

	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	public R remove(@RequestParam String ids) {
		return R.status(voteStrategyService.removeByIds(Func.toLongList(ids)));
	}

	// ------------------------------- 赞、踩（多种策略实现）-------------------------------
	/**
	 * 提交/取消 赞踩
	 */
	@RequestMapping("/checkVote")
	public R alreadyVote(@RequestParam Long answerId,
						 @RequestParam int voteType,
						 @RequestParam String userId) {
		return R.data(voteStrategyService.checkVote(answerId, voteType, userId));
	}

	/**
	 * 提交/取消 赞踩
	 */
	@RequestMapping("/getCount")
	public R getCount(@RequestParam Long answerId,
					  @RequestParam int voteType) {
		return R.data(voteStrategyService.getCount(answerId, voteType));
	}

	/**
	 * 提交/取消 赞踩
	 */
	@PostMapping("/vote")
	public R vote(@RequestBody VoteVO vote) {
		return R.status(voteStrategyService.vote(vote));
	}

	/**
	 * 提交 赞或踩
	 */
	@PostMapping("/submitVote")
	public R submitVote(@RequestBody VoteVO vote) {
		// 更新得票计数器
		return R.status(voteStrategyService.submitVote(vote));
	}

	/**
	 * 提交 赞或踩
	 */
	@PostMapping("/cancelVote")
	public R cancelVote(@RequestBody VoteVO vote) {
		// 更新得票计数器
		return R.status(voteStrategyService.cancelVote(vote));
	}
}
