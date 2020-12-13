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

import com.jiuzhang.zhihu.entity.Question;
import com.jiuzhang.zhihu.entity.vo.QuestionVO;
import com.jiuzhang.zhihu.mapper.AnswerMapper;
import com.jiuzhang.zhihu.mapper.QuestionMapper;
import com.jiuzhang.zhihu.service.IQuestionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author 作者
 * @since 2020-11-12
 */
@Service
public class QuestionServiceImpl extends ServiceImpl<QuestionMapper, Question> implements IQuestionService {

	@Override
	public IPage<QuestionVO> selectQuestionPage(IPage<QuestionVO> page, QuestionVO question) {
		return page.setRecords(baseMapper.selectQuestionPage(page, question));
	}

	@Override
	public void incrAnswerCount(Long questionId) {
		Question question = getById(questionId);
		question.setAnswerCount(question.getAnswerCount()+1);
		updateById(question);
	}

	@Override
	public void decrAnswerCount(Long questionId) {
		Question question = getById(questionId);
		question.setAnswerCount(question.getAnswerCount()-1);
		updateById(question);
	}
}
