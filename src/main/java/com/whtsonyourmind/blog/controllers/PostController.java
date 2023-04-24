package com.whtsonyourmind.blog.controllers;

import java.awt.PageAttributes.MediaType;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import javax.print.attribute.standard.Media;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.util.StreamUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.whtsonyourmind.blog.entities.Post;
import com.whtsonyourmind.blog.entities.User;
import com.whtsonyourmind.blog.payloads.ApiResponse;
import com.whtsonyourmind.blog.payloads.PostDto;
import com.whtsonyourmind.blog.payloads.PostResponse;
import com.whtsonyourmind.blog.services.FileService;
import com.whtsonyourmind.blog.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/api/posts")
public class PostController {
	
	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;
    
	// create post
	@PostMapping("/user/{userId}/category/{categoryId}/posts")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto,
			@PathVariable Integer userId,@PathVariable Integer categoryId){
		 PostDto createPost = this.postService.createPost(postDto, userId, categoryId);
		 return new ResponseEntity<PostDto>(createPost,HttpStatus.CREATED);
	}
	
	//get post by user
	@GetMapping("/user/{userId}")
	public ResponseEntity<List<PostDto>> getPostsByUser(@PathVariable Integer userId){
		List<PostDto> posts =  this.postService.getPostsByUser(userId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//get post by category
	@GetMapping("/category/{categoryId}")
	public ResponseEntity<List<PostDto>> getPostsByCategory(@PathVariable Integer categoryId){
		List<PostDto> posts =  this.postService.getPostsByCategory(categoryId);
		return new ResponseEntity<List<PostDto>>(posts,HttpStatus.OK);
	}
	
	//get All post
	@GetMapping("/")
	public ResponseEntity<PostResponse> getAllPosts(
			@RequestParam(value = "pageNumber",defaultValue = "1",required = false)Integer pageNumber,
			@RequestParam(value = "pageSize",defaultValue = "8",required = false)Integer pageSize,
			@RequestParam(value = "sortBy",defaultValue = "postId",required = false)String sortBy,
			@RequestParam(value = "sortDir",defaultValue = "asc",required = false)String sortDir
			){
		PostResponse postResponse = this.postService.getAllPost(pageNumber, pageSize,sortBy,sortDir);
		return new ResponseEntity<PostResponse>(postResponse,HttpStatus.OK);
	}
	
	//get Post By postId
	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId){
		PostDto postDto = this.postService.getPostById(postId);
		return new ResponseEntity<PostDto>(postDto,HttpStatus.OK);
	}
	
	//delete Post
	@DeleteMapping("/post/{postId}")
	public ApiResponse deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ApiResponse("Post is Successfully deleted!!", true);
	}
	
	
	//updated Post
	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto,@PathVariable Integer postId) {
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	//searching
	@GetMapping("/post/search/{keywords}")
	public ResponseEntity<List<PostDto>> searchPostByTitle(@PathVariable String keywords){
		 List<PostDto> postDtos = this.postService.searchPosts(keywords);
		 return new ResponseEntity<List<PostDto>>(postDtos,HttpStatus.OK);
	}
	
	
	
	//post image upload
	
	@PostMapping("/post/image/upload/{postId}")
	public ResponseEntity<PostDto> uploadPostImage(
				@RequestParam("image") MultipartFile image,
				@PathVariable Integer postId
				) throws IOException{
		
		PostDto postDto = this.postService.getPostById(postId);
		String fileName = this.fileService.uploadImage(path, image);
		
		postDto.setImageName(fileName);
		PostDto updatePost = this.postService.updatePost(postDto, postId);
		return new ResponseEntity<PostDto>(updatePost,HttpStatus.OK);
	}
	
	@GetMapping(value = "/post/image/{imageName}",produces = org.springframework.http.MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable("imageName") String imageName,
			HttpServletResponse response) throws IOException {
		
		InputStream resource = this.fileService.getResources(path, imageName);
		response.setContentType(org.springframework.http.MediaType.IMAGE_JPEG_VALUE);
		org.springframework.util.StreamUtils.copy(resource,response.getOutputStream());
	}
}
