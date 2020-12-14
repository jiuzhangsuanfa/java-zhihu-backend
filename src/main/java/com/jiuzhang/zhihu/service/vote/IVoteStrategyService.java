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
import com.jiuzhang.zhihu.service.IVoteService;

/**
 *  服务类
 *
 * @author 作者
 * @since 2020-11-12
 */
public interface IVoteStrategyService extends IVoteService {

    /**
     * 取得投票总数
     *
     * @param answerId
     * @param userId
     * @param voteType
     * @return
     */
    int getCount(Long answerId, String userId, int voteType);

    /**
     * 投票
     *
     * 提交或取消赞/踩
     *
     * @param voteVo
     * @return
     */
    void vote(VoteVO voteVo);

//    void addVote();

//    void cancelVote();
}
