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

import com.jiuzhang.zhihu.common.enums.VoteActionEnum;
import com.jiuzhang.zhihu.entity.vo.VoteVO;
import com.jiuzhang.zhihu.service.impl.VoteServiceImpl;
import com.jiuzhang.zhihu.service.vote.IVoteStrategyService;

/**
 * 基于缓存的投票服务策略类
 *
 * @author 九章算法
 * @since 2020-11-25
 */
public abstract class AbstractVoteStrategyServiceImpl extends VoteServiceImpl implements IVoteStrategyService {

	/**
	 * 写入投票记录
	 * @param answerId
	 * @param userId
	 * @param voteType
	 */
	@Override
	public int getCount(Long answerId, String userId, int voteType) {
		return calcVoteCount(answerId, userId, voteType);
	}

	@Override
	public void vote(VoteVO vote) {
		int action = vote.getAction();
		if (VoteActionEnum.SUBMIT.getCategory() == vote.getAction()) {
			submitVote(vote);
		}
		if (VoteActionEnum.CANCEL.getCategory() == vote.getAction()) {
			cancelVote(vote);
		}

//		int count = determineCount(action);
		logVote(vote);
//		changeVoteCount(vote.getAnswerId(), vote.getType(), vote.getAction(), count);
	}

	/**
	 * 写入投票记录
	 * @param answerId
	 * @param userId
	 * @param voteType
	 */
	protected abstract int calcVoteCount(Long answerId, String userId, int voteType);

	protected abstract void submitVote(VoteVO vote);

	protected abstract void cancelVote(VoteVO vote);


	/**
	 * 写入投票记录
	 * @param vote
	 */
	protected abstract void logVote(VoteVO vote);

	/**
	 * 改变answer的投票计数器
	 * @param answerId
	 * @param voteType
	 * @param count
	 */
	protected abstract void changeVoteCount(Long answerId, int voteType, int voteAction, int count);




	private int determineCount(int action) {
		int count;
		if (action == VoteActionEnum.SUBMIT.getCategory()) {
			count = 1;
		} else if (action == VoteActionEnum.CANCEL.getCategory()) {
			count = -1;
		} else {
			throw new IllegalStateException("Unexpected voteAction value: " + action);
		}
		return count;
	}

}
