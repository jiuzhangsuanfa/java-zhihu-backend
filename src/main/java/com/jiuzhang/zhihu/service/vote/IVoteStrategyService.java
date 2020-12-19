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
package com.jiuzhang.zhihu.service.vote;


import com.jiuzhang.zhihu.entity.vo.VoteVO;
import com.jiuzhang.zhihu.service.IVoteStatsService;

/**
 *  服务类
 *
 * @author 作者
 * @since 2020-11-12
 */
public interface IVoteStrategyService extends IVoteStatsService {

    /**
     * 用户对答案是否已投票
     *
     * @param answerId
     * @param voteType
     * @return
     */
    boolean checkVote(Long answerId, int voteType, String userId);

    /**
     * 取得投票总数
     *
     * @param answerId
     * @param voteType
     * @return
     */
    int getCount(Long answerId, int voteType);

    /**
     * 投票（提交或取消赞/踩）
     *
     * @param voteVo
     * @return
     */
    boolean vote(VoteVO voteVo);

    /**
     * 提交投票 赞/踩
     *
     * @param vote
     * @return
     */
    boolean submitVote(VoteVO vote);

    /**
     * 取消投票 赞/踩
     *
     * @param vote
     * @return
     */
    boolean cancelVote(VoteVO vote);

}
