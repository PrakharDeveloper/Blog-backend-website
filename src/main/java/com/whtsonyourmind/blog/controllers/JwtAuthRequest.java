package com.whtsonyourmind.blog.controllers;

import lombok.Data;

@Data
public class JwtAuthRequest {
	private String username;
	private String password;
}
