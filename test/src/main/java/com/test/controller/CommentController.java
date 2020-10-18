package com.test.controller;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.test.dto.CommentDto;
import com.test.dto.PostDto;
import com.test.entities.Comment;
import com.test.entities.Post;
import com.test.entities.User;
import com.test.service.CommentService;
import com.test.service.PostService;
import com.test.service.UserService;

@RestController
@RequestMapping("/comment")
public class CommentController {

	@Autowired
	private CommentService commentService;
	@Autowired
	private UserService userService;
	@Autowired
	private PostService postService;

	@Autowired
	private ModelMapper modelMapper;

/*	@RequestMapping("/")
	public String index() {
	}
*/
	@PostMapping("/signUp")
	public ResponseEntity<CommentDto> createComment(@Valid @RequestBody Comment comment) {
		Optional<User> userObj = this.userService.getOne(comment.getUser().getId());
		Optional<Post> postObj = this.postService.getOne(comment.getPost().getId());

		if (userObj.isPresent() && postObj.isPresent()) {
			comment = this.commentService.saveComment(comment);
			CommentDto commentDto = modelMapper.map(comment, CommentDto.class);
			return ResponseEntity.ok().body(commentDto);
		}
		return ResponseEntity.notFound().build();
	}

	@GetMapping("/findAllComment")
	public ResponseEntity<List<CommentDto>> getAllComment() {
		List<Comment> comment= this.commentService.findAll();
		if (comment == null)
			return ResponseEntity.notFound().build();
		Type listType = new TypeToken<List<CommentDto>>() {
		}.getType();
		List<CommentDto> commentDtoList = this.modelMapper.map(comment, listType);
		return ResponseEntity.ok().body(commentDtoList);
	}
	//LOAD ALL COMMENTS BY POST_ID, so like when every post loads it has comments
	@GetMapping("/findAllCommentByPostId/{post_id}")
	public ResponseEntity<Iterable<CommentDto>> getAllCommentByPostId(@PathVariable(value = "post_id") int postId) {
		Iterable<Comment> comment= this.commentService.findAllCommentByPostId(postId);
		if (comment == null)
			return ResponseEntity.notFound().build();
		Type listType = new TypeToken<Iterable<CommentDto>>() {
		}.getType();
		Iterable<CommentDto> commentDtoList = this.modelMapper.map(comment, listType);
		return ResponseEntity.ok().body(commentDtoList);
	}
/*
	@GetMapping("/findAllCommentUserActiveAndPost")
	public ResponseEntity<List<CommentDto>> getAllCommentActiveUserAndPost() {
		Iterable<Comment> comments = this.commentService.findAllActiveUserAndPost();
		if (posts == null)
			return ResponseEntity.notFound().build();

		Type listType = new TypeToken<List<PostDto>>() {
		}.getType();
		List<PostDto> postDtoList = this.modelMapper.map(posts, listType);
		return ResponseEntity.ok().body(postDtoList);
	}
*/
	@GetMapping("/findPost/{id}")
	public ResponseEntity<PostDto> getUse(@PathVariable(value = "id") int id) {
		Optional<Post> post = this.postService.getOne(id);
		if (post.isPresent() == false)
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		// return ResponseEntity.notFound().build();
		PostDto postDto = modelMapper.map(post.get(), PostDto.class);
		return ResponseEntity.ok().body(postDto);
	}

	// DELETED USER SET POST'S FLAG UNSET... SET ALL COMMENT FROM USER AS SOFT DELETION...
	@PostMapping("/deleteCommentWhenPostDeleted/{post_id}")
	public Boolean deletePostWhenUserDeleted(
			@PathVariable(value = "post_id") int postId) {

		Iterable<Comment> comments = this.commentService.findAllCommentByPostId(postId);
		if (comments.spliterator().getExactSizeIfKnown() == 0)
			return false;
		for (Comment comment : comments) {
			comment.setUserFlag("0");
			this.commentService.saveComment(comment);
		}
		return true;
	}	
	//SOFT DELETION OF COMMENT OCCUR, SETS COMMENTfLAG TO 0 ONLY..
	@PostMapping("/deleteComment/{comment_id}")
	public Boolean deleteComment(@PathVariable(value = "comment_id") int commentId) {
		
		Optional<Comment> getPost = this.commentService.getOne(commentId);
		if(getPost.isPresent()){
			getPost.get().setCommentFlag("0");
			this.commentService.saveComment(getPost.get());
			return true;
		}
		return false;
	}
	
	// DELETE POST BY USER...
	@PostMapping("/deletePostByUser/{post_id}")
	public Boolean deletePostByUser(@PathVariable(value = "post_id") int postId) {
		Optional<Post> getPost = this.postService.getOne(postId);
		if (getPost.isPresent() == false)
			return false;
		this.postService.delete(getPost.get());
		return true;
	}
	
	@PostMapping("/update/{id}")
	public ResponseEntity<CommentDto> update(@PathVariable(value = "id") int id,
			@Valid @RequestBody Comment comment) {
		Optional<Comment> getComment = this.commentService.getOne(id);
		if (getComment.isPresent() == false)
			return ResponseEntity.notFound().build();
		Comment updateComment = this.commentService.saveComment(getComment.get());
		CommentDto commentDto = modelMapper.map(updateComment, CommentDto.class);
		return ResponseEntity.ok().body(commentDto);
	}
}
