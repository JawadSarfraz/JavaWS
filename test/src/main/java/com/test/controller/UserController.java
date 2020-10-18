package com.test.controller;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.test.dto.UserDto;
import com.test.entities.Post;
import com.test.entities.User;
import com.test.service.PostService;
import com.test.service.UserService;

@RestController
@RequestMapping("/user")

public class UserController {

	@Autowired
	private UserService userService;
	@Autowired
    private ModelMapper modelMapper;

	@Autowired
	private PostService postService;

	@RequestMapping("/")
	public String index() {
		Optional<User> temp = userService.getOne(15);
		if (temp.isPresent()) {
			System.out.println(temp.get());
		} else {
			System.out.println("No User found");
		}
		System.out.print(temp.getClass().getName());
		return "Meowwwww....";
	}
	//HERE BOOLEAN CAN BE RETURNED-->uSER CREATED CONFIRMATION
	@PostMapping("/create")
	public ResponseEntity<UserDto> createUser(@Valid @RequestBody User user) {
		// return Response.build();
		User obj = this.userService.getUserByEmail(user.getEmail());
		if(this.userService.getUserByEmail(user.getEmail())!= null)
			return ResponseEntity.notFound().build();
		
		user = this.userService.saveUser(user);
		UserDto userDto = modelMapper.map(user, UserDto.class);
		return ResponseEntity.ok().body(userDto);
	}

	@GetMapping("/findAllUser")
	public ResponseEntity<List<UserDto>> getAllUser() {
		List<User> users = this.userService.findAll();
		if (users == null)
			return ResponseEntity.notFound().build();
		Type listType = new TypeToken<List<UserDto>>(){}.getType();
		List<UserDto> userDtoList = this.modelMapper.map(users,listType);
		return ResponseEntity.ok().body(userDtoList);
	}

	@GetMapping("/findUser/{id}")
	public ResponseEntity<UserDto> getUse(@PathVariable(value = "id") int id) {
		Optional<User> user = this.userService.getOne(id);
		if (user == null)
			return ResponseEntity.notFound().build();
		UserDto userDto = this.modelMapper.map(user, UserDto.class);
		return ResponseEntity.ok().body(userDto);
	}
	//TAKE POST AND DELETE ALLES.. AND THEN DELETE FROM USER, SOFT DELTETION
	@PostMapping("/delete/{id}")
//	public ResponseEntity<UserDto> delete(@PathVariable(value = "id") int id, @Valid @RequestBody User user) {
	public Boolean delete(@PathVariable(value = "id") int id) {
	
		Optional<User> getUser = this.userService.getOne(id);
		if (getUser == null)							//user doesnot Exist...
			return false;
		if(getUser.get().getUserFlag().equals("0")) 	//user ALREADY DELETED
			return false;
		Iterable<Post> posts = postService.findAllByUserId(id); 
		for(Post post : posts){									// set all posts status 0, soft deletion
			post.setUserFlag("0");
			postService.savePost(post);
		}
		getUser.get().setUserFlag("0");							//user deletion is on
		this.userService.saveUser(getUser.get());
		return true;
	}
	//USER UPDATION HAPPENED VIA U_ID, NORMALLY UPDATION OCCUR VIA EMAIL, 
	//SO GET U_ID BY EMAIL THEN DO OPERAITON, ALSO EMAIL WOULD BE UNIQUE...-->not implemented yet, discussion requred
	@PostMapping("update/{id}")
	public ResponseEntity<UserDto> update(@PathVariable(value = "id") int id) {	
		Optional<User> getUser = this.userService.getOne(id);
		if (getUser == null)
			return ResponseEntity.notFound().build();
		User updateUser = this.userService.saveUser(getUser.get());
		UserDto userDto = this.modelMapper.map(updateUser, UserDto.class);
		return ResponseEntity.ok().body(userDto);
	}
	//LOGIN IMPLEMENTATION VIA EMAIL N PASSWORD
	@PostMapping("/login")
	public ResponseEntity<UserDto> loginUser(@PathVariable(value = "email") String email,@PathVariable(value = "password") String password) {
		User getUser = this.userService.getUserByEmailAndPassword(email, password);
		if(getUser == null)
			return ResponseEntity.notFound().build();
		UserDto userDto = modelMapper.map(getUser, UserDto.class);
		return ResponseEntity.ok().body(userDto);
	}
}
