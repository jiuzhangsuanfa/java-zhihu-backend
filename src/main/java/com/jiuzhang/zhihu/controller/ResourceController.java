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
import com.jiuzhang.zhihu.util.MinioUtil;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import lombok.AllArgsConstructor;

/**
 *  资源控制器
 *
 * @author 九章算法
 * @since 2020-11-09
 */
@RestController
@AllArgsConstructor
@RequestMapping("resources")
public class ResourceController {

	private MinioUtil minioUtil;

	/**
	 * 索引全部Question
	 */
	@PostMapping
	public void get() {
//		minioUtil.
//		return null;
	}

	/**
	 * 搜索Question
	 */
	@GetMapping
	public R<String> upload(MultipartFile file) {
		minioUtil.putObject("default", file, "name");
		return R.data("path");
	}

}
