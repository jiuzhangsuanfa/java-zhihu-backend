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
import com.jiuzhang.zhihu.common.enums.VoteTypeEnum;
import com.jiuzhang.zhihu.entity.Answer;
import com.jiuzhang.zhihu.entity.Vote;
import com.jiuzhang.zhihu.entity.vo.VoteVO;
import com.jiuzhang.zhihu.service.IAnswerService;
import com.jiuzhang.zhihu.service.IVoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 基于缓存的投票服务策略类
 *
 * @author 九章算法
 * @since 2020-11-25
 */
//@Service
public class SimpleVoteStrategyServiceImpl extends AbstractVoteStrategyServiceImpl {

	@Autowired
	private IAnswerService answerService;

	@Override
	protected void logVote(VoteVO vote) {
		if (VoteActionEnum.SUBMIT.getCategory() == vote.getAction()) {
			this.save(vote);
		}
		if (VoteActionEnum.CANCEL.getCategory() == vote.getAction()) {
			this.removeById(vote);
		}
	}

	/**
	 * 改变answer的投票数（支持乐观锁）
	 *
	 * @param answerId
	 * @param voteType
	 * @param voteAction
	 * @param count
	 */
	protected void changeVoteCount(Long answerId, int voteType, int voteAction, int count) {
		Answer answer = answerService.getById(answerId);
		if (answer==null) {
			return;
		}

		boolean rv = false;
		if (voteType == VoteTypeEnum.UPVOTE.getCategory()) {
			answer.setUpvoteCount(answer.getUpvoteCount()+count);
			rv = answerService.updateById(answer);
		} else if (voteType == 0) {
			int downCount = Math.max(answer.getDownvoteCount()+count, 0);
			answer.setDownvoteCount(downCount);
			rv = answerService.updateById(answer);
		} else {
			throw new IllegalStateException("Unexpected value: " + voteType);
		}
	}

}
