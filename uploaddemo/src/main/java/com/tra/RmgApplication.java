package com.tra;

import com.tra.swagger.EnableApiDoc;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication
@MapperScan("com.tra.repository")
@EnableApiDoc
public class RmgApplication {

	public static void main(String[] args) {
		SpringApplication.run(RmgApplication.class, args);
	}

}
