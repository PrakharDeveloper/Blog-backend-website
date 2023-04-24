package com.whtsonyourmind.blog.services;

import java.util.List;

import com.whtsonyourmind.blog.payloads.Userdto;

public interface UserService {
	
	Userdto registerNewUserdtoUser(Userdto user);

	Userdto createUser(Userdto user);

	Userdto updateUser(Userdto user, Integer userId);

	Userdto getUserById(Integer userId);

	List<Userdto> getAllUsers();

	void deleteUser(Integer userId);
}
