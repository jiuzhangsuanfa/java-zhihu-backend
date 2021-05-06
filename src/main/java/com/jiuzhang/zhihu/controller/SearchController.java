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
package com.jiuzhang.zhihu.controller;

import com.jiuzhang.zhihu.common.api.R;
import com.jiuzhang.zhihu.service.search.IndexService;

import org.elasticsearch.search.SearchHits;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;

/**
 *  控制器
 *
 * @author 九章算法
 * @since 2020-11-09
 */
@AllArgsConstructor
@RestController
@RequestMapping("search")
public class SearchController {

	private final IndexService indexService;

	/** 搜索Question */
	@RequestMapping("")
	public R<SearchHits> search(String keyword) {
		return R.data(indexService.search(keyword));
	}
}

//	/**
//	 * 索引全部Question
//	 */
//	@RequestMapping("indexAll")
//	public R<String> indexAll() {
//		indexService.indexAll();
//		return null;
//	}
//
//	/**
//	 * 索引全部Question
//	 */
//	@RequestMapping("index")
//	public R<String> index(Long questionId) {
//		indexService.index(questionId);
//		return R.data(null);
//	}
//
//	/**
//	 * 索引全部Question
//	 */
//	@RequestMapping("delete")
//	public R<String> delete(Long questionId) {
//		indexService.delete(questionId);
//		return R.data(null);
//	}
//
//}
