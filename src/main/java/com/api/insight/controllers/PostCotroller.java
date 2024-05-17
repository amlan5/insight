package com.api.insight.controllers;

import java.io.IOException;
import java.io.InputStream;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StreamUtils;
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

import com.api.insight.config.AppConstants;
import com.api.insight.payloads.ApiResponse;
import com.api.insight.payloads.PostDto;
import com.api.insight.payloads.PostResponse;
import com.api.insight.services.FileService;
import com.api.insight.services.PostService;

import jakarta.servlet.http.HttpServletResponse;

@RestController
@RequestMapping("/insight")
public class PostCotroller {

	@Autowired
	private PostService postService;
	
	@Autowired
	private FileService fileService;
	
	@Value("${project.image}")
	private String path;

	@PostMapping("/user/{userId}/category/{categoryId}/post")
	public ResponseEntity<PostDto> createPost(@RequestBody PostDto postDto, @PathVariable Integer categoryId,
			@PathVariable Integer userId) {
		PostDto createdPostDto = this.postService.createPost(postDto, categoryId, userId);
		return new ResponseEntity<PostDto>(createdPostDto, HttpStatus.CREATED);
	}

	@GetMapping("/user/{userId}/post")
	public ResponseEntity<List<PostDto>> getPostByUser(@PathVariable Integer userId) {
		return ResponseEntity.ok(this.postService.getAllPostByUser(userId));
	}

	@GetMapping("/category/{categoryId}/post")
	public ResponseEntity<List<PostDto>> getPostByCategory(@PathVariable Integer categoryId) {
		return ResponseEntity.ok(this.postService.getAllPostByCategory(categoryId));
	}

	@GetMapping("/post/{postId}")
	public ResponseEntity<PostDto> getPostById(@PathVariable Integer postId) {
		return ResponseEntity.ok(this.postService.getPost(postId));
	}

	@GetMapping("/post")
	public ResponseEntity<PostResponse> getAllPost(
			@RequestParam(value = "pageNumber", defaultValue = AppConstants.PAGE_NUMBER, required = false) Integer pageNumber,
			@RequestParam(value = "pageSize", defaultValue = AppConstants.PAGE_SIZE, required = false) Integer pageSize,
			@RequestParam(value = "sortBy", defaultValue = AppConstants.SORT_BY, required = false) String sortBy,
			@RequestParam(value = "sortOrder", defaultValue = AppConstants.SORT_ORDER, required = false) String sortOrder) {
		return ResponseEntity.ok(this.postService.getAllPost(pageNumber, pageSize, sortBy, sortOrder));
	}

	@PutMapping("/post/{postId}")
	public ResponseEntity<PostDto> updatePost(@RequestBody PostDto postDto, @PathVariable Integer postId) {
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatedPostDto);
	}

	@DeleteMapping("/post/{postId}")
	public ResponseEntity<ApiResponse> deletePost(@PathVariable Integer postId) {
		this.postService.deletePost(postId);
		return new ResponseEntity<ApiResponse>(new ApiResponse("Post deleted successfully", true), HttpStatus.OK);
	}
	
	@GetMapping("/post/search/{keyword}")
	public ResponseEntity<List<PostDto>> searchPost(@PathVariable("keyword") String keyword) {
		return ResponseEntity.ok(this.postService.searchPostByKey(keyword));
	}
	
	@PostMapping("/post/upload/{postId}")
	public ResponseEntity<PostDto> uploadImage(@RequestParam("image") MultipartFile file, @PathVariable Integer postId) throws IOException{
		
		PostDto postDto = this.postService.getPost(postId);
		String fileName = this.fileService.uploadImage(path, file);
		postDto.setPostImage(fileName);
		PostDto updatedPostDto = this.postService.updatePost(postDto, postId);
		return ResponseEntity.ok(updatedPostDto);
	}
	
	@GetMapping(value = "/post/download/{imageName}", produces = MediaType.IMAGE_JPEG_VALUE)
	public void downloadImage(@PathVariable String imageName, HttpServletResponse response) throws IOException {
		InputStream resource = this.fileService.getSource(path, imageName);
		response.setContentType(MediaType.IMAGE_JPEG_VALUE);
		StreamUtils.copy(resource, response.getOutputStream());
	}

}
