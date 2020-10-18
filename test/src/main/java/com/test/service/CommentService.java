package com.test.service;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.test.entities.Comment;
import com.test.repository.CommentRepository;

@Service
public class CommentService {
	
	@Autowired
	private CommentRepository commentRepository;

	private static final Logger logger = LoggerFactory.getLogger(CommentService.class);

	// save User
	public Comment saveComment(Comment comment) {
		return (Comment) this.commentRepository.save(comment);
	}

	// find All Users
	public List<Comment> findAll() {
		return (List<Comment>) this.commentRepository.findAll();
	}

	// get user by id
	public Optional<Comment> getOne(int id) {
		return this.commentRepository.findById(id);
	}

	// delete user/ or maybe Boolean type
	public void delete(Comment comment) {
		this.commentRepository.delete(comment);
	}
	
	public Iterable<Comment> findAllCommentByPostId(int postId){
		return this.commentRepository.findAllCommentByPostId(postId);
	}

/*	public Iterable<Comment> findAllActiveUserAndPost() {
		// TODO Auto-generated method stub
		return this.commentRepository.findAllActiveUserAndPost();
	}
*/}
