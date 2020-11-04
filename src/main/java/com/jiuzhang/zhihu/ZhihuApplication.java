package com.jiuzhang.zhihu;

import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@EnableCaching
@RestController
@SpringBootApplication
@MapperScan("com.jiuzhang.zhihu.mapper")
public class ZhihuApplication {

	public static void main(String[] args) {
		SpringApplication.run(ZhihuApplication.class, args);
	}

	@RequestMapping("/")
	public String home(HttpServletRequest request) {
		Principal userPrincipal = request.getUserPrincipal();
		return "/";
	}
}
