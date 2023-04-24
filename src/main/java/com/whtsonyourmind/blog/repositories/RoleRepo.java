package com.whtsonyourmind.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whtsonyourmind.blog.entities.Role;

public interface RoleRepo extends JpaRepository<Role, Integer>{

	
}
