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
package com.jiuzhang.zhihu.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import java.time.LocalDateTime;
import com.baomidou.mybatisplus.annotation.TableId;
import java.io.Serializable;

import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.Version;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类
 *
 * @author 作者
 * @since 2020-11-12
 */
@Data
public class Answer implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	 * id
	 */
	@TableId(value="id", type = IdType.ASSIGN_ID)
	private Long id;
	/**
	* 问题答案
	*/
	private String content;
	/**
	* 发布用户ID
	*/
	private String userId;
	/**
	 * 问题ID
	 */
	private Long questionId;
	/**
	 * 评分
	 */
	private Double score;
	/**
	 * 乐观锁版本号
	 */
	@Version
	private Integer version;
	/**
	* 逻辑删除 0-未删除 1-已删除
	*/
	@TableLogic
	private Boolean isDeleted;

	private String createPerson;

	private LocalDateTime createTime;

	private String updatePerson;

	private LocalDateTime updateTime;

}
