package com.api.insight.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.insight.entities.Category;
import com.api.insight.entities.Post;
import com.api.insight.entities.User;

public interface PostRepo extends JpaRepository<Post, Integer> {
	
	List<Post> findByUser(User user);
	List<Post> findByCategory(Category category);
	
	List<Post> findByPostTitleContaining(String title);
}
