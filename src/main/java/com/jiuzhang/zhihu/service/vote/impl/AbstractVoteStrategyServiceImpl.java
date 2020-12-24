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
import com.jiuzhang.zhihu.service.impl.VoteStatsServiceImpl;
import com.jiuzhang.zhihu.service.vote.IVoteStrategyService;

/**
 * 基于缓存的投票服务策略类
 *
 * @author 九章算法
 * @since 2020-11-25
 */
public abstract class AbstractVoteStrategyServiceImpl implements IVoteStrategyService {

	/**
	 * 判断是否已投票
	 * @param answerId
	 * @param voteType
	 */
	public abstract boolean checkVote(Long answerId, int voteType, String userId);

	/**
	 * 写入投票记录
	 * @param answerId
	 * @param voteType
	 */
	public abstract int getCount(Long answerId, int voteType);

	/**
	 * 点赞/踩
	 * @param vote
	 */
	public abstract boolean submitVote(VoteVO vote);

	/**
	 * 取消赞/踩
	 * @param vote
	 */
	public abstract boolean cancelVote(VoteVO vote);

}
