package com.test.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import com.test.entities.Resource;

public interface ResourceRepository extends CrudRepository<Resource,Integer> {
	//TO GET RES WHERE POST ID MATCHED
	@Query(value = "SELECT * FROM resource r WHERE r.post_id = postId",nativeQuery = true)
	Iterable<Resource> findAllByPostId(@Param("postId") int postId);

}
