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
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 实体类
 *
 * @author 九章算法
 * @since 2020-11-06
 */
@Data
public class Vote implements Serializable {

	private static final long serialVersionUID = 1L;

	/**
	* 类别：1-赞 0-踩
	*/
	private Boolean type;
	/**
	* 答案ID
	*/
	private Boolean answerId;
	/**
	* 投票用户ID
	*/
	private Long userId;
	private String createPerson;
	private LocalDateTime createTime;
	private String updatePerson;
	private LocalDateTime updateTime;


}
