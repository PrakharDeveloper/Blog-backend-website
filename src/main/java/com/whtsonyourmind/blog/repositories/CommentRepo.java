package com.whtsonyourmind.blog.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.whtsonyourmind.blog.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
