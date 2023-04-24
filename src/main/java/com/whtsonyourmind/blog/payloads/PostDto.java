package com.whtsonyourmind.blog.payloads;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import com.whtsonyourmind.blog.entities.Category;
import com.whtsonyourmind.blog.entities.Comment;
import com.whtsonyourmind.blog.entities.User;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class PostDto {
	private Integer postId;
	private String title;
	private String content;
	private String imageName;
	private Date addeDate;
	private CategoryDto category;
	private Userdto user;
	private Set<CommentDto> comments = new HashSet<>();
}
