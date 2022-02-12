package com.wm;

import com.wm.model.Stub;
import com.wm.model.Teams;
import org.apache.ibatis.type.MappedTypes;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MappedTypes({Stub.class, Teams.class})
@MapperScan("com.wm.mappers")
public class DatabaseStubbingApplication {

	public static void main(String[] args) {
		System.out.println("starting the server");
		SpringApplication.run(DatabaseStubbingApplication.class, args);
	}

}
