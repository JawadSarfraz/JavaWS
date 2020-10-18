package com.test.repository;


import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.test.entities.Post;

public interface PostRepository extends CrudRepository<Post,Integer> {
	
	@Query(value = "SELECT * FROM post t WHERE t.user_id = user_id",nativeQuery = true)
	Iterable<Post> findAllByUserId(@Param("user_id") int user_id);

	@Query(value = "SELECT * FROM post t WHERE t.user_flag = 1",nativeQuery = true)
	Iterable<Post> findAllUserActive();

	@Query(value = "SELECT * FROM post t WHERE t.post_flag = 1",nativeQuery = true)
	Iterable<Post> findAllPostActive();
	
	@Query(value = "SELECT * FROM post t WHERE t.post_flag = 1 AND t.user_flag = 1",nativeQuery = true)
	Iterable<Post> findAllActivePostAndUser();

}
