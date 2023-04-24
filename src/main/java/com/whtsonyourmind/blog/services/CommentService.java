package com.whtsonyourmind.blog.services;

import com.whtsonyourmind.blog.entities.Post;
import com.whtsonyourmind.blog.payloads.CommentDto;

public interface CommentService {

	CommentDto createComment(CommentDto comment,Integer postId);
	void deleteComment(Integer commentId);
}
