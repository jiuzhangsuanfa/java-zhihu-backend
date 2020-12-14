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
package com.jiuzhang.zhihu.common.support;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jiuzhang.zhihu.util.BeanUtil;
import com.jiuzhang.zhihu.common.Func;
import com.jiuzhang.zhihu.common.constant.TokenConstant;

import java.util.Map;

/**
 * 分页工具
 *
 * @author Chill
 */
public class Condition {

	/**
	 * 转化成mybatis plus中的Page
	 *
	 * @param query 查询条件
	 * @return IPage
	 */
	public static <T> IPage<T> getPage(Query query) {
		Page<T> page = new Page<>(Func.toInt(query.getCurrent(), 1), Func.toInt(query.getSize(), 10));
		page.setAsc(Func.toStrArray(SqlKeyword.filter(query.getAscs())));
		page.setDesc(Func.toStrArray(SqlKeyword.filter(query.getDescs())));
		return page;
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param entity 实体
	 * @param <T>    类型
	 * @return QueryWrapper
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(T entity) {
		return new QueryWrapper<>(entity);
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param query 查询条件
	 * @param clazz 实体类
	 * @param <T>   类型
	 * @return QueryWrapper
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Class<T> clazz) {
		Kv exclude = Kv.create().set(TokenConstant.HEADER, TokenConstant.HEADER)
			.set("current", "current").set("size", "size").set("ascs", "ascs").set("descs", "descs");
		return getQueryWrapper(query, exclude, clazz);
	}

	/**
	 * 获取mybatis plus中的QueryWrapper
	 *
	 * @param query   查询条件
	 * @param exclude 排除的查询条件
	 * @param clazz   实体类
	 * @param <T>     类型
	 * @return QueryWrapper
	 */
	public static <T> QueryWrapper<T> getQueryWrapper(Map<String, Object> query, Map<String, Object> exclude, Class<T> clazz) {
		exclude.forEach((k, v) -> query.remove(k));
		QueryWrapper<T> qw = new QueryWrapper<>();
		qw.setEntity(BeanUtil.newInstance(clazz));
		SqlKeyword.buildCondition(query, qw);
		return qw;
	}

}
