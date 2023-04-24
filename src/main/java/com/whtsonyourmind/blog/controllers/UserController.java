package com.whtsonyourmind.blog.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.whtsonyourmind.blog.payloads.ApiResponse;
import com.whtsonyourmind.blog.payloads.Userdto;
import com.whtsonyourmind.blog.services.UserService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/users")
public class UserController {

	@Autowired
	private UserService userService;
	
	//POST - create user
	@PostMapping("/")
	public ResponseEntity<Userdto> createUser(@Valid @RequestBody Userdto userdto){
		Userdto createUserdto = userService.createUser(userdto);
		return new ResponseEntity<Userdto>(createUserdto, HttpStatus.CREATED);
	}
	
	//PUT - update user
	@PutMapping("/{userId}")
	public ResponseEntity<Userdto> updateUser(@Valid @RequestBody Userdto userdto,@PathVariable("userId") Integer uid){
		Userdto updatedUserdto = this.userService.updateUser(userdto, uid);
		return new ResponseEntity<Userdto>(updatedUserdto,HttpStatus.OK);
	}
	
	//Only Admin can access
	//DELETE - delete user
	@PreAuthorize("hasRole('ADMIN')")
	@DeleteMapping("/{userId}")
	public ResponseEntity<ApiResponse> deleteUser(@PathVariable("userId") Integer uid){
		 this.userService.deleteUser(uid);
		 return new ResponseEntity<ApiResponse>(new ApiResponse("User Deleted successfully", true), HttpStatus.OK);
	}
	
	
	//GET - get user
	@GetMapping("/")
	public ResponseEntity<List<Userdto>> getAllUsers(){
		List<Userdto> l = this.userService.getAllUsers();
		return new ResponseEntity<List<Userdto>>(l,HttpStatus.OK); 
	}
	
	@GetMapping("/{userId}")
	public ResponseEntity<Userdto> getSingleUser(@PathVariable Integer userId){
		Userdto user = this.userService.getUserById(userId);
		return new ResponseEntity<Userdto>(user,HttpStatus.OK);
	}
}
