package com.api.insight.services.impl;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Sort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.api.insight.entities.Category;
import com.api.insight.entities.Post;
import com.api.insight.entities.User;
import com.api.insight.exceptions.ResourceNotFoundException;
import com.api.insight.payloads.PostDto;
import com.api.insight.payloads.PostResponse;
import com.api.insight.repositories.CategoryRepo;
import com.api.insight.repositories.PostRepo;
import com.api.insight.repositories.UserRepo;
import com.api.insight.services.PostService;

@Service
public class PostServiceImpl implements PostService {

	@Autowired
	private PostRepo postRepo;
	@Autowired
	private UserRepo userRepo;
	@Autowired
	private CategoryRepo categoryRepo;
	@Autowired
	private ModelMapper modelmapper;

	@Override
	public PostDto createPost(PostDto postDto, Integer categoryId, Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		Post post = this.postDtoToPost(postDto);
		post.setPostImage("default.png");
		post.setCreatedOn(new Date());
		post.setUser(user);
		post.setCategory(category);
		Post savedPost = this.postRepo.save(post);
		return this.postToPostDto(savedPost);
	}

	@Override
	public PostDto updatePost(PostDto postDto, Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		post.setPostTitle(postDto.getPostTitle());
		post.setPostContent(postDto.getPostContent());
		post.setPostImage(postDto.getPostImage());
		Post updatedPost = this.postRepo.save(post);
		return this.postToPostDto(updatedPost);
	}

	@Override
	public PostDto getPost(Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		return this.postToPostDto(post);
	}

	@Override
	public PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder) {
		// TODO Auto-generated method stub
		Sort sort = sortOrder.equalsIgnoreCase("asc") ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
		Pageable page = PageRequest.of(pageNumber, pageSize, sort);
		Page<Post> pagePosts = this.postRepo.findAll(page);
		List<Post> posts = pagePosts.getContent();
		List<PostDto> postDtos = posts.stream().map(post -> this.postToPostDto(post)).collect(Collectors.toList());
		PostResponse postResponse = new PostResponse();
		postResponse.setContent(postDtos);
		postResponse.setPageNumber(pagePosts.getNumber());
		postResponse.setPageSize(pagePosts.getSize());
		postResponse.setTotalElements(pagePosts.getTotalElements());
		postResponse.setTotalPages(pagePosts.getTotalPages());
		postResponse.setLastPage(pagePosts.isLast());
		return postResponse;
	}

	@Override
	public void deletePost(Integer postId) {
		// TODO Auto-generated method stub
		Post post = this.postRepo.findById(postId)
				.orElseThrow(() -> new ResourceNotFoundException("Post", "postId", postId));
		this.postRepo.delete(post);
	}

	@Override
	public List<PostDto> getAllPostByCategory(Integer categoryId) {
		// TODO Auto-generated method stub
		Category category = this.categoryRepo.findById(categoryId)
				.orElseThrow(() -> new ResourceNotFoundException("Category", "categoryId", categoryId));
		List<Post> posts = this.postRepo.findByCategory(category);
		List<PostDto> postDtos = posts.stream().map(post -> this.postToPostDto(post)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> getAllPostByUser(Integer userId) {
		// TODO Auto-generated method stub
		User user = this.userRepo.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));
		List<Post> posts = this.postRepo.findByUser(user);
		List<PostDto> postDtos = posts.stream().map(post -> this.postToPostDto(post)).collect(Collectors.toList());
		return postDtos;
	}

	@Override
	public List<PostDto> searchPostByKey(String key) {
		// TODO Auto-generated method stub
		List<Post> posts = this.postRepo.findByPostTitleContaining(key);
		List<PostDto> postDtos = posts.stream().map(post -> this.postToPostDto(post)).collect(Collectors.toList());
		return postDtos;
	}

	private PostDto postToPostDto(Post post) {
		return this.modelmapper.map(post, PostDto.class);
	}

	private Post postDtoToPost(PostDto postDto) {
		return this.modelmapper.map(postDto, Post.class);
	}

}
