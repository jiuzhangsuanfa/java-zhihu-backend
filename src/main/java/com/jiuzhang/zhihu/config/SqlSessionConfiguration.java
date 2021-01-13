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
package com.jiuzhang.zhihu.config;

import javax.sql.DataSource;

import com.baomidou.mybatisplus.extension.spring.MybatisSqlSessionFactoryBean;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * mybatis数据源配置
 *
 * @author 九章算法
 */
@Configuration
public class SqlSessionConfiguration {

	@Primary
	@Bean(name = "sqlSessionFactory")
	public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
		MybatisSqlSessionFactoryBean sessionFactoryBean = new MybatisSqlSessionFactoryBean();
		sessionFactoryBean.setDataSource(dataSource);
//		Resource resource = new PathMatchingResourcePatternResolver().getResource("classpath:mybatis/mybatis-config.xml");
//		sessionFactoryBean.setConfigLocation(resource);
		return sessionFactoryBean.getObject();
	}

	@Bean(name = "sqlSessionTemplate")
	public SqlSessionTemplate sqlSessionTemplate(@Qualifier("sqlSessionFactory") SqlSessionFactory sqlSessionFactory) {
		return new SqlSessionTemplate(sqlSessionFactory);
	}

}
