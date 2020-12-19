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

import lombok.AllArgsConstructor;

import com.jiuzhang.zhihu.common.support.Condition;
import com.jiuzhang.zhihu.common.support.Query;
import com.jiuzhang.zhihu.common.R;
import com.jiuzhang.zhihu.common.Func;
import org.springframework.web.bind.annotation.*;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.jiuzhang.zhihu.entity.UserPushRel;
import com.jiuzhang.zhihu.entity.vo.UserPushRelVO;
import com.jiuzhang.zhihu.service.IUserPushRelService;

/**
 *  控制器
 *
 * @author 作者
 * @since 2020-12-15
 */
@RestController
@AllArgsConstructor
@RequestMapping("/")
public class UserPushRelController {

	private final IUserPushRelService userPushRelService;

	/**
	 * 详情
	 */
	@GetMapping("/detail")
	public R<UserPushRel> detail(UserPushRel userPushRel) {
		UserPushRel detail = userPushRelService.getOne(Condition.getQueryWrapper(userPushRel));
		return R.data(detail);
	}

	/**
	 * 分页 
	 */
	@GetMapping("/list")
	public R<IPage<UserPushRel>> list(UserPushRel userPushRel, Query query) {
		IPage<UserPushRel> pages = userPushRelService.page(Condition.getPage(query), Condition.getQueryWrapper(userPushRel));
		return R.data(pages);
	}

	/**
	 * 自定义分页 
	 */
	@GetMapping("/page")
	public R<IPage<UserPushRelVO>> page(UserPushRelVO userPushRel, Query query) {
		IPage<UserPushRelVO> pages = userPushRelService.selectUserPushRelPage(Condition.getPage(query), userPushRel);
		return R.data(pages);
	}

	/**
	 * 新增 
	 */
	@PostMapping("/save")
	public R save(@RequestBody UserPushRel userPushRel) {
		return R.status(userPushRelService.save(userPushRel));
	}

	/**
	 * 修改 
	 */
	@PostMapping("/update")
	public R update(@RequestBody UserPushRel userPushRel) {
		return R.status(userPushRelService.updateById(userPushRel));
	}

	/**
	 * 新增或修改 
	 */
	@PostMapping("/submit")
	public R submit(@RequestBody UserPushRel userPushRel) {
		return R.status(userPushRelService.saveOrUpdate(userPushRel));
	}


	/**
	 * 删除 
	 */
	@PostMapping("/remove")
	public R remove(@RequestParam String ids) {
		return R.status(userPushRelService.removeByIds(Func.toLongList(ids)));
	}


}
