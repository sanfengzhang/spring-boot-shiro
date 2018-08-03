package com.han.springboot.shiro;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = { "com.han.springboot.shiro" })
public class Application
{

	public static void main(String[] args)
	{
		SpringApplication springApplication = new SpringApplication(Application.class);
		springApplication.run(args);

	}

}
