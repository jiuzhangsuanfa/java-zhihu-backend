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
package com.jiuzhang.zhihu.service.vote.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiuzhang.zhihu.entity.VoteStats;
import com.jiuzhang.zhihu.entity.vo.VoteVO;
import com.jiuzhang.zhihu.service.IVoteStatsService;
import com.jiuzhang.zhihu.service.vote.IVoteStrategyService;
import com.jiuzhang.zhihu.util.SetUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Set;

/**
 * 基于缓存的投票服务策略类
 *
 * @author 九章算法
 * @since 2020-11-25
 */
//@Slf4j
//@Primary
//@Service("simpleVoteStrategyService")
public class SimpleVoteStrategyServiceImpl implements IVoteStrategyService {

	@Autowired
	private IVoteStatsService voteStatsService;

	@Override
	public boolean checkVote(Long answerId, int voteType, String userId) {
		VoteStats stats = queryVoteStats(answerId, voteType);
		Set<String> users = SetUtil.deserialize(stats.getVoteUsers());
		return users.contains(userId);
	}

	@Override
	public int getCount(Long answerId, int voteType) {
		VoteStats stats = queryVoteStats(answerId, voteType);
		return Optional.ofNullable(stats).map(VoteStats::getVoteCount).orElse(0);
	}

	@Override
	public synchronized boolean submitVote(VoteVO vote) {

		String userId = vote.getUserId();
		Long answerId = vote.getAnswerId();
		Integer type = vote.getType();

		VoteStats stats = queryVoteStats(answerId, type);
		// 如果投票记录不存在，创建新记录
		if (stats == null) {
			stats = new VoteStats(answerId, type);
		}

		Set<String> users = SetUtil.deserialize(stats.getVoteUsers());

		// 判断是否 投过票
		if (users.contains(userId) ) {
			return true;
		}

		// 保存入库
		users.add(userId);
		stats.setVoteUsers(SetUtil.serialize(users));

		if (stats.getVoteCount()==null ) {
			stats.setVoteCount(0);
		}
		stats.setVoteCount(stats.getVoteCount() + 1);

		return voteStatsService.saveOrUpdate(stats);
	}

	@Override
	public synchronized boolean cancelVote(VoteVO vote) {

		String userId = vote.getUserId();
		Long answerId = vote.getAnswerId();
		Integer type = vote.getType();

		VoteStats stats = queryVoteStats(answerId, type);
		Set<String> users = SetUtil.deserialize(stats.getVoteUsers());

		// 判断是否 投过票
		if (!users.contains(userId) ) {
			return true;
		}

		// 保存入库
		stats.setVoteCount(stats.getVoteCount() - 1);
		users.remove(userId);
		stats.setVoteUsers(SetUtil.serialize(users));

		return voteStatsService.updateById(stats);
	}

	/**
	 * 查询 answer的投票统计
	 * @param answerId
	 * @param type
	 * @return
	 */
	private VoteStats queryVoteStats(Long answerId, Integer type) {
		VoteStats voteStats = new VoteStats();
		voteStats.setAnswerId(answerId);
		voteStats.setType(type);

		QueryWrapper<VoteStats> query = new QueryWrapper<>(voteStats);
		return voteStatsService.getOne(query);
	}

}
