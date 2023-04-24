package com.whtsonyourmind.blog.services.Impl;

import java.util.List;
import java.util.stream.Collectors;

import javax.management.relation.Role;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.whtsonyourmind.blog.exceptions.ResourceNotFoundException;
import com.whtsonyourmind.blog.controllers.UserController;
import com.whtsonyourmind.blog.entities.User;
import com.whtsonyourmind.blog.payloads.Userdto;
import com.whtsonyourmind.blog.repositories.RoleRepo;
import com.whtsonyourmind.blog.repositories.UserRepo;
import com.whtsonyourmind.blog.services.UserService;

@Service
public class UserServiceImpl implements UserService {

	private static final Integer NORMAL_USER = 502;
	
	@Autowired
	private UserRepo userRepo;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private PasswordEncoder passwordEncoder;
	
	@Autowired
	private RoleRepo roleRepo;
	
	@Override
	public Userdto createUser(Userdto userdto) {
		User user = this.dtoToUser(userdto);
		User savedUser = this.userRepo.save(user); 
		return this.userToDto(savedUser);
	}

	@Override
	public Userdto updateUser(Userdto userdto, Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		user.setName(userdto.getName());
		user.setEmail(userdto.getEmail());
		user.setPassword(userdto.getPassword());
		user.setAbout(userdto.getAbout());
		
		User updateUser = this.userRepo.save(user);
		
		return this.userToDto(updateUser);		
		
	}

	@Override
	public Userdto getUserById(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		return this.userToDto(user);
	}

	@Override
	public List<Userdto> getAllUsers() {
		List<User> users = this.userRepo.findAll();
		
		List<Userdto> userdtos = users.stream().map(user->this.userToDto(user)).collect(Collectors.toList());
		return userdtos;
	}

	@Override
	public void deleteUser(Integer userId) {
		User user = this.userRepo.findById(userId).orElseThrow(()-> new ResourceNotFoundException("User","Id",userId));
		
		this.userRepo.delete(user);

	}
	
	private User dtoToUser(Userdto userdto) {
		
		User user = this.modelMapper.map(userdto, User.class);
//		user.setId(userdto.getId());
//		user.setName(userdto.getName());
//		user.setEmail(userdto.getEmail());
//		user.setPassword(userdto.getPassword());
//		user.setAbout(userdto.getAbout());
		
		return user;
		
	}
	
	public Userdto userToDto(User user) {
		Userdto userdto = this.modelMapper.map(user, Userdto.class);
//		Userdto userdto = new Userdto();
//		userdto.setId(user.getId());
//		userdto.setName(user.getName());
//		userdto.setEmail(user.getEmail());
//		userdto.setPassword(user.getPassword());
//		userdto.setAbout(user.getAbout());
		return userdto;
	}

	@Override
	public Userdto registerNewUserdtoUser(Userdto userDto) {
		User user = this.modelMapper.map(userDto, User.class);
		
		//encoder
		user.setPassword(this.passwordEncoder.encode(user.getPassword()));
		
		//find role
		com.whtsonyourmind.blog.entities.Role role = roleRepo.findById(NORMAL_USER).get();
		user.getRoles().add(role);
		User newUser = this.userRepo.save(user);
		return this.modelMapper.map(newUser, Userdto.class);
	}

}
