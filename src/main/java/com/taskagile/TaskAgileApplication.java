package com.taskagile;

import com.taskagile.config.ApplicationProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class TaskAgileApplication {

	public static void main(String[] args) {
		SpringApplication.run(TaskAgileApplication.class, args);
	}
}
