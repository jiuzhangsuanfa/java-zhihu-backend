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
package com.jiuzhang.zhihu.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.jiuzhang.zhihu.entity.VoteStats;
import com.jiuzhang.zhihu.entity.vo.VoteStatsVO;
import com.jiuzhang.zhihu.mapper.VoteStatsMapper;
import com.jiuzhang.zhihu.service.IVoteStatsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author 作者
 * @since 2020-11-12
 */
@Service
@Qualifier("voteStatsService")
public class VoteStatsServiceImpl extends ServiceImpl<VoteStatsMapper, VoteStats> implements IVoteStatsService {

	@Override
	public IPage<VoteStatsVO> selectVoteStatsPage(IPage<VoteStatsVO> page, VoteStatsVO vote) {
		return page.setRecords(baseMapper.selectVoteStatsPage(page, vote));
	}

	@Override
	public VoteStats queryByCondition(Long answerId, int type) {
		VoteStats query = new VoteStats(answerId, type);
		return getOne(new QueryWrapper<>(query));
	}
}
