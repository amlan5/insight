package com.api.insight.services;

import java.util.List;
import com.api.insight.payloads.PostDto;
import com.api.insight.payloads.PostResponse;

public interface PostService {

	PostDto createPost(PostDto postDto, Integer categoryId, Integer userId);

	PostDto updatePost(PostDto postDto, Integer postId);

	PostDto getPost(Integer postId);

	PostResponse getAllPost(Integer pageNumber, Integer pageSize, String sortBy, String sortOrder);

	void deletePost(Integer postId);

	List<PostDto> getAllPostByCategory(Integer categoryId);

	List<PostDto> getAllPostByUser(Integer userId);

	List<PostDto> searchPostByKey(String key);

}
