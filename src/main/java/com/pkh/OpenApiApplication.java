package com.pkh;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@SpringBootApplication
@ComponentScan("com.pkh.*")
@MapperScan("com.pkh.dao.mapper")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class OpenApiApplication {

	public static void main(String[] args) {
		SpringApplication.run(OpenApiApplication.class, args);
	}

}
