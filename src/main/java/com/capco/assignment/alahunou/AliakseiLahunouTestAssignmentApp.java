package com.capco.assignment.alahunou;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
@ComponentScan(basePackages = "com.capco.assignment.alahunou")
public class AliakseiLahunouTestAssignmentApp {

	public static void main(String[] args) {
		SpringApplication.run(AliakseiLahunouTestAssignmentApp.class, args);
	}

}
