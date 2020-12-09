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

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.jiuzhang.zhihu.entity.Answer;
import com.jiuzhang.zhihu.entity.vo.AnswerVO;
import com.jiuzhang.zhihu.mapper.AnswerMapper;
import com.jiuzhang.zhihu.service.IAnswerService;
import org.springframework.stereotype.Service;

/**
 *  服务实现类
 *
 * @author 作者
 * @since 2020-11-12
 */
@Service
public class AnswerServiceImpl extends ServiceImpl<AnswerMapper, Answer> implements IAnswerService {

	@Override
	public IPage<AnswerVO> selectAnswerPage(IPage<AnswerVO> page, AnswerVO answer) {
		return page.setRecords(baseMapper.selectAnswerPage(page, answer));
	}

}
