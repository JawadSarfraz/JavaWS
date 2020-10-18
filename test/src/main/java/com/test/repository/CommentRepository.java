package com.test.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.test.entities.Comment;
import com.test.entities.Post;

public interface CommentRepository extends CrudRepository<Comment,Integer> {
	
	//Iterable<Comment> findAllActiveUserAndPost();

	@Query(value = "SELECT * FROM comment c WHERE c.post_id = post_id",nativeQuery = true)
	Iterable<Comment> findAllCommentByPostId(@Param("post_id") int post_id);

}
