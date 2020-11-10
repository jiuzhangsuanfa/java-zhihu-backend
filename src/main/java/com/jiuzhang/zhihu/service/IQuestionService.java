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
package com.jiuzhang.zhihu.service;

import com.jiuzhang.zhihu.entity.Question;
import com.jiuzhang.zhihu.vo.QuestionVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务类
 *
 * @author 九章算法
 * @since 2020-11-06
 */
public interface IQuestionService extends IService<Question> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param question
	 * @return
	 */
	IPage<QuestionVO> selectQuestionPage(IPage<QuestionVO> page, QuestionVO question);

}
