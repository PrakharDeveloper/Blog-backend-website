package com.whtsonyourmind.blog.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whtsonyourmind.blog.entities.Category;
import com.whtsonyourmind.blog.entities.Post;
import com.whtsonyourmind.blog.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer>{
			
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByTitleContaining(String title);

}
