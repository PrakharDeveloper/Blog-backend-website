package com.whtsonyourmind.blog;

import java.util.List;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.Bean;

import com.whtsonyourmind.blog.entities.Role;
import com.whtsonyourmind.blog.repositories.RoleRepo;

@SpringBootApplication
public class BlogWebappApiApplication implements CommandLineRunner{

	@Autowired
	private RoleRepo roleRepo;
	
	public static void main(String[] args) {
		SpringApplication.run(BlogWebappApiApplication.class, args);
	}
	
	
	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}
	
	@Override
	
	public void run(String...args) throws Exception{
		try {
			Role role = new Role();
			role.setId(501);
			role.setName("ADMIN_USER");
			
			Role role1 = new Role();
			role1.setId(502);
			role1.setName("NORMAL_USER");
			
			List<Role> roles = List.of(role,role1);
			List<Role> result = this.roleRepo.saveAll(roles);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
