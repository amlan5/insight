package com.api.insight.repositories;

import org.springframework.data.jpa.repository.JpaRepository;

import com.api.insight.entities.Comment;

public interface CommentRepo extends JpaRepository<Comment, Integer>{

}
