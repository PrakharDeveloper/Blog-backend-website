package com.whtsonyourmind.blog;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.whtsonyourmind.blog.repositories.UserRepo;
import com.whtsonyourmind.blog.services.UserService;

@SpringBootTest
class BlogWebappApiApplicationTests {

	@Autowired
	private UserService userService;
	
	
	@Test
	void contextLoads() {
	}
	
	@Test
	public void serviceTest() {
		String className = this.userService.getClass().getName();
		String packageName = this.userService.getClass().getPackageName();
		System.out.println(className);
		System.out.println(packageName);
	}
}
