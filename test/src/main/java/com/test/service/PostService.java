package com.test.service;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Service;

import com.test.entities.Post;
import com.test.repository.PostRepository;

@Service
public class PostService {

	@Autowired
	private PostRepository postRepository;

	private static final Logger logger = LoggerFactory.getLogger(PostService.class);

	// save Post
	public Post savePost(Post post) {
		return (Post) this.postRepository.save(post);
	}
	// find All Posts
	public List<Post> findAll() {
		return (List<Post>) this.postRepository.findAll();
	}
	//find All Posts having user not deleted/Active User
	public Iterable<Post> findAllUserActive() {
		return (Iterable<Post>) this.postRepository.findAllUserActive();
	}
	public Iterable<Post> findAllById(Iterable<Integer> id){
		return this.postRepository.findAllById(id);
	}
	// get user by id
	public Optional<Post> getOne(int id) {
		return this.postRepository.findById(id);
	}
	// delete user/ or maybe Boolean type
	public void delete(Post post) {
		this.postRepository.delete(post);
	}
	public Iterable<Post> findAllByUserId(int id){
		return this.postRepository.findAllByUserId(id);
	}
	
	//USER STATUS + POST STATUS = 1
	public Iterable<Post> findAllActivePostAndUser(){
		return this.postRepository.findAllActivePostAndUser();
	}
	//USER STATUS = 1
	public Iterable<Post> findAllActiveUser(){
		return this.findAllUserActive();
	}
	//POST STATUS = 1
	public Iterable<Post> findAllActivePost(){
		return this.findAllActivePost();
	}
}
